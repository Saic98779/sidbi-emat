package org.emat.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.emat.service.IndustryAssociationAppraisalService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;

class DiaDocumentControllerTest {
    @Test
    void bothRoutesUseExplicitIdsAndNonCacheablePdfResponses() {
        var service = mock(IndustryAssociationAppraisalService.class);
        var controller = new IndustryAssociationAppraisalController(service);
        byte[] pdf = new byte[] {1, 2, 3};
        when(service.generateDiaDocumentPdf(221L)).thenReturn(pdf);
        when(service.generateDiaDocumentPdfByRegistrationId(42L)).thenReturn(pdf);
        var byAppraisal = controller.downloadDiaDocument(221L);
        var byRegistration = controller.downloadDiaDocumentByRegistrationId(42L);
        for (var response : java.util.List.of(byAppraisal, byRegistration)) {
            assertArrayEquals(pdf, response.getBody());
            assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
            assertEquals("no-store", response.getHeaders().getCacheControl());
        }
        assertTrue(byAppraisal.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("appraisal-221.pdf"));
        assertTrue(byRegistration.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION).contains("registration-42.pdf"));
        verify(service).generateDiaDocumentPdf(221L);
        verify(service).generateDiaDocumentPdfByRegistrationId(42L);
    }

    @Test
    void bothRoutesUseTheDedicatedDownloadPolicy() throws Exception {
        for (String method : java.util.List.of("downloadDiaDocument", "downloadDiaDocumentByRegistrationId")) {
            assertEquals("hasAnyRole(@endpointRolePolicyService.resolveRoles('industryAssociationAppraisalDiaDocumentDownload'))",
                    IndustryAssociationAppraisalController.class.getMethod(method, Long.class)
                            .getAnnotation(PreAuthorize.class).value());
        }
    }
}
