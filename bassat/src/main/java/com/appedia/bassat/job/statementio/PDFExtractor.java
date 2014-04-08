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

    /**
     * Starts the textual extraction.
     *
     * @param password
     * @param istream
     * @throws Exception
     */
    public byte[] extractToText(InputStream istream, String password) throws IOException, PDFExtractionException
    {
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;

        Writer output = null;
        PDDocument document = null;
        try {
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();

            document = PDDocument.load(istream);
            if( document.isEncrypted() )
            {
                StandardDecryptionMaterial sdm = new StandardDecryptionMaterial( password );
                document.openProtection( sdm );
            }

            //use default encoding
            output = new OutputStreamWriter(ostream);

            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setForceParsing(false);
            stripper.setSortByPosition(false);
            stripper.setShouldSeparateByBeads(true);
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);

            // Extract text for main document:
            stripper.writeText( document, output );

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
                            if (file.getSubtype().equals("application/pdf")) {
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

            return ostream.toByteArray();

        } catch (CryptographyException e) {
            throw new PDFExtractionException(e);

        } catch (BadSecurityHandlerException e) {
            throw new PDFExtractionException(e);

        } finally {
            if( output != null ) {
                output.close();
            }
            if( document != null ) {
                document.close();
            }
        }
    }

}
