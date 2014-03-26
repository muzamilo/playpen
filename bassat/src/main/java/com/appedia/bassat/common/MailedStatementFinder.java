package com.appedia.bassat.common;


import javax.mail.*;
import java.io.*;
import java.util.Properties;


/**
 */
public class MailedStatementFinder {

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getDefaultInstance(properties, null);
            //create session instance
            Store store = session.getStore("imaps");//create store instance
            store.connect("pop.gmail.com", "send2basat@gmail.com", "Muzamil0");
            //set your user_name and password
            System.out.println(store);
            Folder inbox = store.getFolder("inbox");
            //set folder from where u wants to read mails
            inbox.open(Folder.READ_ONLY);//set access type of Inbox
            Message messages[] = inbox.getMessages();// gets inbox messages
            for (int i = 0; i < messages.length; i++) {
                System.out.println("------------ Message " + (i + 1) + " ------------");
                System.out.println("SentDate : " + messages[i].getSentDate()); //print sent date
                System.out.println("From : " + messages[i].getFrom()[0]); //print email id of sender
                System.out.println("Sub : " + messages[i].getSubject()); //print subject of email
                try {
                    Multipart mulpart = (Multipart) messages[i].getContent();
                    int count = mulpart.getCount();
                    System.out.println("Count:" + count);
                    for (int j = 0; j < count; j++) {
                        String attachment = mulpart.getBodyPart(j).getFileName();
                        if (attachment != null && attachment.trim().toLowerCase().endsWith(".pdf")) {
                            File pdfFile = downloadPDFStatement(mulpart.getBodyPart(j));
                            importPDFStatement(pdfFile);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Exception arise at get Content");
                    ex.printStackTrace();
                }
            }
            store.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void importPDFStatement(File pdfFile) throws Exception {
        String[] args = new String[] { "-password", "7706035097083", pdfFile.getPath(), pdfFile.getName() + ".txt"};
        new PDFExtractor().startExtraction(args);

    }

    public static File downloadPDFStatement(BodyPart part) throws Exception {
        InputStream input = part.getInputStream();
        try {
            if (!(input instanceof BufferedInputStream)) {
                input = new BufferedInputStream(input);
            }
            File tempfile = File.createTempFile(part.getFileName() + ".", "");
            OutputStream output = new FileOutputStream(tempfile);
            try {
                byte[] res = new byte[1048];
                int got;
                while ((got = input.read(res)) != -1){
                    output.write(res, 0, got);
                }
                return tempfile;
            } finally {
                output.close();
            }
        } finally {
            input.close();
        }
    }

}
