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
import org.apache.pdfbox.util.PDFText2HTML;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.*;
import java.util.Map;


/**
 *
 * @author Muz Omar
 */
public class PDFExtractor {

    /*
     * debug flag
     */
    private boolean debug = false;
    private int maxSupportedFileSize = 65535;

    /**
     * Starts the text extraction.
     *
     * @param password
     * @param pdfFile
     * @throws Exception
     */
    public File extract(File pdfFile, String password) throws IOException, PDFExtractionException
    {
        boolean toConsole = false;
        boolean toHTML = false;
        boolean force = false;
        boolean sort = false;
        boolean separateBeads = true;
        boolean useNonSeqParser = false;
        String encoding = null;
        // Defaults to text files
        String ext = ".txt";
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;

        if (pdfFile == null) {
            throw new IllegalArgumentException("No pdfFile specified");

        } else if (pdfFile.length() > maxSupportedFileSize) {
            throw new PDFExtractionException("PDF file size exceeds " + maxSupportedFileSize);

        } else {
            Writer output = null;
            PDDocument document = null;
            try
            {
                long startTime = startProcessing("Loading PDF "+ pdfFile.getName());

                File outputFile = new File(pdfFile.getAbsolutePath() + ext);

                if (useNonSeqParser)
                {
                    document = PDDocument.loadNonSeq(pdfFile, null, password);
                }
                else
                {
                    document = PDDocument.load(pdfFile);
                    if( document.isEncrypted() )
                    {
                        StandardDecryptionMaterial sdm = new StandardDecryptionMaterial( password );
                        document.openProtection( sdm );
                    }
                }

//                AccessPermission ap = document.getCurrentAccessPermission();
//                if(!ap.canExtractContent()) {
//                    throw new IOException( "You do not have permission to extract text" );
//                }

                stopProcessing("Time for loading: ", startTime);


                if ((encoding == null) && (toHTML))
                {
                    encoding = "UTF-8";
                }

                if( toConsole )
                {
                    output = new OutputStreamWriter( System.out );
                }
                else
                {
                    if( encoding != null )
                    {
                        output = new OutputStreamWriter(
                                new FileOutputStream( outputFile ), encoding );
                    }
                    else
                    {
                        //use default encoding
                        output = new OutputStreamWriter(
                                new FileOutputStream( outputFile ) );
                    }
                }

                PDFTextStripper stripper = null;
                if(toHTML)
                {
                    stripper = new PDFText2HTML(encoding);
                }
                else
                {
                    stripper = new PDFTextStripper(encoding);
                }
                stripper.setForceParsing( force );
                stripper.setSortByPosition( sort );
                stripper.setShouldSeparateByBeads( separateBeads );
                stripper.setStartPage( startPage );
                stripper.setEndPage( endPage );

                startTime = startProcessing("Starting text extraction");
                if (debug)
                {
                    System.err.println("Writing to "+outputFile);
                }

                // Extract text for main document:
                stripper.writeText( document, output );

                // ... also for any embedded PDFs:
                PDDocumentCatalog catalog = document.getDocumentCatalog();
                PDDocumentNameDictionary names = catalog.getNames();
                if (names != null)
                {
                    PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
                    if (embeddedFiles != null)
                    {
                        Map<String,COSObjectable> embeddedFileNames = embeddedFiles.getNames();
                        if (embeddedFileNames != null) {
                            for (Map.Entry<String,COSObjectable> ent : embeddedFileNames.entrySet())
                            {
                                if (debug)
                                {
                                    System.err.println("Processing embedded file " + ent.getKey() + ":");
                                }
                                PDComplexFileSpecification spec = (PDComplexFileSpecification) ent.getValue();
                                PDEmbeddedFile file = spec.getEmbeddedFile();
                                if (file.getSubtype().equals("application/pdf"))
                                {
                                    if (debug)
                                    {
                                        System.err.println("  is PDF (size=" + file.getSize() + ")");
                                    }
                                    InputStream fis = file.createInputStream();
                                    PDDocument subDoc = null;
                                    try
                                    {
                                        subDoc = PDDocument.load(fis);
                                    }
                                    finally
                                    {
                                        fis.close();
                                    }
                                    try
                                    {
                                        stripper.writeText( subDoc, output );
                                    }
                                    finally
                                    {
                                        subDoc.close();
                                    }
                                }
                            }
                        }
                    }
                }

                stopProcessing("Time for extraction: ", startTime);

                return outputFile;

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

    private long startProcessing(String message)
    {
        if (debug)
        {
            System.err.println(message);
        }
        return System.currentTimeMillis();
    }

    private void stopProcessing(String message, long startTime)
    {
        if (debug)
        {
            long stopTime = System.currentTimeMillis();
            float elapsedTime = ((float)(stopTime - startTime))/1000;
            System.err.println(message + elapsedTime + " seconds");
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getMaxSupportedFileSize() {
        return maxSupportedFileSize;
    }

    public void setMaxSupportedFileSize(int maxSupportedFileSize) {
        this.maxSupportedFileSize = maxSupportedFileSize;
    }
}
