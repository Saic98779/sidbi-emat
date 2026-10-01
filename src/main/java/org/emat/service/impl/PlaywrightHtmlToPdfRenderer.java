//package org.emat.service.impl;
//
//import com.microsoft.playwright.Browser;
//import com.microsoft.playwright.BrowserContext;
//import com.microsoft.playwright.BrowserType;
//import com.microsoft.playwright.Page;
//import com.microsoft.playwright.Playwright;
//import com.microsoft.playwright.PlaywrightException;
//import com.microsoft.playwright.options.WaitUntilState;
//import java.util.List;
//import org.emat.service.HtmlToPdfRenderer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PlaywrightHtmlToPdfRenderer implements HtmlToPdfRenderer {
//    private final String channel;
//
//    public PlaywrightHtmlToPdfRenderer(@Value("${dia.pdf.browser-channel:}") String channel) {
//        this.channel = channel;
//    }
//
//    @Override
//    public synchronized byte[] render(String htmlContent) {
//        // Playwright is not thread-safe; bound concurrent browser creation per application instance.
//        try (Playwright playwright = Playwright.create();
//                Browser browser = launch(playwright);
//                BrowserContext context = browser.newContext(
//                        new Browser.NewContextOptions().setJavaScriptEnabled(false))) {
//            context.route("**/*", route -> route.abort());
//            Page page = context.newPage();
//            page.setDefaultTimeout(30_000);
//            page.setContent(htmlContent, new Page.SetContentOptions()
//                    .setWaitUntil(WaitUntilState.LOAD).setTimeout(30_000));
//            page.evaluate("() => document.fonts.ready");
//            return page.pdf(new Page.PdfOptions().setFormat("A4").setPreferCSSPageSize(true)
//                    .setPrintBackground(true).setDisplayHeaderFooter(true)
//                    .setHeaderTemplate("<span></span>")
//                    .setFooterTemplate("<div style='font-size:8px;width:100%;text-align:center'>DIA | Page <span class='pageNumber'></span> of <span class='totalPages'></span></div>"));
//        }
//    }
//
//    private Browser launch(Playwright playwright) {
//        PlaywrightException failure = null;
//        List<String> channels = channel.isBlank() ? List.of("", "chrome", "msedge") : List.of(channel);
//        for (String candidate : channels) {
//            try {
//                var options = new BrowserType.LaunchOptions().setHeadless(true).setTimeout(30_000);
//                if (!candidate.isBlank()) options.setChannel(candidate);
//                return playwright.chromium().launch(options);
//            } catch (PlaywrightException e) {
//                failure = e;
//            }
//        }
//        throw new IllegalStateException("Install Playwright Chromium or configure dia.pdf.browser-channel=chrome or msedge", failure);
//    }
//}
//
