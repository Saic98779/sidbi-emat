package org.emat.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.emat.service.DocxToPdfRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LibreOfficeDocxToPdfRenderer implements DocxToPdfRenderer {
    private final String executable;

    public LibreOfficeDocxToPdfRenderer(@Value("${dia.pdf.office-executable:}") String executable) {
        this.executable = executable.isBlank() ? detectExecutable() : executable;
    }

    @Override
    public synchronized byte[] render(byte[] docx) {
        Path work = null;
        Process process = null;
        try {
            work = Files.createTempDirectory("emat-dia-office-");
            Path input = work.resolve("dia.docx");
            Files.write(input, docx);
            process = new ProcessBuilder(executable,
                    "-env:UserInstallation=" + work.resolve("profile").toUri(),
                    "--headless", "--nologo", "--nodefault", "--norestore", "--nofirststartwizard",
                    "--convert-to", "pdf:writer_pdf_Export", "--outdir", work.toString(), input.toString())
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(ProcessBuilder.Redirect.DISCARD).start();
            if (!process.waitFor(120, TimeUnit.SECONDS)) {
                throw new IllegalStateException("DIA Word-to-PDF conversion timed out");
            }
            Path output = work.resolve("dia.pdf");
            if (process.exitValue() != 0 || !Files.isRegularFile(output)) {
                throw new IllegalStateException("LibreOffice did not produce the DIA PDF");
            }
            byte[] pdf = Files.readAllBytes(output);
            if (pdf.length < 5 || !new String(pdf, 0, 5, StandardCharsets.US_ASCII).equals("%PDF-")) {
                throw new IllegalStateException("Invalid PDF returned by LibreOffice");
            }
            return pdf;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("DIA PDF conversion interrupted", e);
        } catch (IOException e) {
            throw new IllegalStateException("Install LibreOffice Writer or set dia.pdf.office-executable to its executable", e);
        } finally {
            if (process != null && process.isAlive()) {
                process.descendants().forEach(ProcessHandle::destroyForcibly);
                process.destroyForcibly();
                try { process.waitFor(5, TimeUnit.SECONDS); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            cleanup(work);
        }
    }

    private static String detectExecutable() {
        for (String candidate : List.of("C:\\Program Files\\LibreOffice\\program\\soffice.com",
                "C:\\Program Files (x86)\\LibreOffice\\program\\soffice.com",
                "/usr/bin/soffice", "/Applications/LibreOffice.app/Contents/MacOS/soffice")) {
            if (Files.isRegularFile(Path.of(candidate))) return candidate;
        }
        return "soffice";
    }

    private static void cleanup(Path directory) {
        if (directory == null) return;
        try (var paths = Files.walk(directory)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.deleteIfExists(path);
        } catch (IOException e) {
            // Do not expose document data or temporary paths in client errors.
            System.getLogger(LibreOfficeDocxToPdfRenderer.class.getName())
                    .log(System.Logger.Level.WARNING, "DIA conversion temporary files could not be fully removed");
        }
    }
}
