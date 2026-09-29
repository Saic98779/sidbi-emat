package org.emat.service;

public interface DocxToPdfRenderer {
    byte[] render(byte[] docx);
}
