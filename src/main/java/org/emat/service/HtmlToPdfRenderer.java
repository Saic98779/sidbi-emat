package org.emat.service;

public interface HtmlToPdfRenderer {

    byte[] render(String htmlContent);
}

