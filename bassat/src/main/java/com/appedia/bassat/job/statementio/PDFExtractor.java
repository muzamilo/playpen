package com.appedia.bassat.job.statementio;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.encryption.BadSecurityHandlerException;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.*;
import java.util.Map;


/**
 *
 * @author Muz Omar
 */
public class PDFExtractor {

    private static final String MIME_TYPE_PDF = "application/pdf";

    /**
     * Extract the PDF into text
     *
     * @param pdfFileData
     * @param password
     * @throws PDFExtractionException
     */
    public byte[] extractToText(byte[] pdfFileData, String password) throws PDFExtractionException
    {
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;

        try {
            PDDocument document = PDDocument.load(new BufferedInputStream(new ByteArrayInputStream(pdfFileData)));
            try {
                // decrypt the document if necessary
                if (document.isEncrypted()) {
                    try {
                        document.openProtection(new StandardDecryptionMaterial(password));
                    } catch (BadSecurityHandlerException e) {
                        throw new PDFExtractionException(e);
                    } catch (CryptographyException e) {
                        throw new PDFExtractionException(e);
                    }
                }

                // use default encoding
                Writer output = new StringWriter();
                try {
                    PDFTextStripper stripper = new PDFTextStripper();
                    stripper.setForceParsing(false);
                    stripper.setSortByPosition(false);
                    stripper.setShouldSeparateByBeads(true);
                    stripper.setStartPage(startPage);
                    stripper.setEndPage(endPage);

                    // Extract text for main document:
                    stripper.writeText(document, output);

                    // ... also for any embedded PDFs:
                    PDDocumentCatalog catalog = document.getDocumentCatalog();
                    PDDocumentNameDictionary names = catalog.getNames();

                    if (names != null) {
                        PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();

                        if (embeddedFiles != null) {
                            Map<String,COSObjectable> embeddedFileNames = embeddedFiles.getNames();

                            if (embeddedFileNames != null) {
                                for (Map.Entry<String,COSObjectable> ent : embeddedFileNames.entrySet()) {
                                    PDComplexFileSpecification spec = (PDComplexFileSpecification) ent.getValue();
                                    PDEmbeddedFile file = spec.getEmbeddedFile();
                                    if (file.getSubtype().equals(MIME_TYPE_PDF)) {
                                        InputStream fis = file.createInputStream();
                                        PDDocument subDoc = null;
                                        try {
                                            subDoc = PDDocument.load(fis);
                                        } finally {
                                            fis.close();
                                        }
                                        try {
                                            stripper.writeText( subDoc, output );
                                        } finally {
                                            subDoc.close();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    return output.toString().getBytes();

                } finally {
                    output.close();
                }
            } finally {
                document.close();
            }
        } catch (IOException e) {
            throw new PDFExtractionException(e);
        }
    }


    /**
     *
     * @param pdfFileData
     * @param password
     * @return
     * @throws IOException
     */
    public boolean checkValidPassword(byte[] pdfFileData, String password) throws IOException {
        PDDocument document = PDDocument.load(new BufferedInputStream(new ByteArrayInputStream(pdfFileData)));
        try {
            // decrypt the document if necessary
            if (document.isEncrypted()) {
                try {
                    document.openProtection(new StandardDecryptionMaterial(password));
                    return true;
                } catch (BadSecurityHandlerException e) {
                    // ignore
                } catch (CryptographyException e) {
                    // ignore
                }
            }
        } finally {
            document.close();
        }
        return false;
    }

}
