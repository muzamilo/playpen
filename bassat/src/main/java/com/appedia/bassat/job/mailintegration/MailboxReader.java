package com.appedia.bassat.job.mailintegration;

import javax.mail.*;
import java.util.Properties;

/**
 *
 * @author Muz Omar
 */
public class MailboxReader {

    private final String FOLDER_INBOX = "inbox";

    private String host;
    private String protocol;
    private String username;
    private String password;
    private Properties javaMailProperties;

    public MailboxReader() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getJavaMailProperties() {
        return javaMailProperties;
    }

    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
    }

    /**
     *
     * @param handler
     */
    public void processInbox(MailboxMessageHandler handler) {

        System.out.println("Connecting to mailbox @ " + getHost() + " with " + getUsername() + "/" + getPassword());
        try {
            Session session = Session.getDefaultInstance(getJavaMailProperties(), null);
            // create session instance
            Store store = session.getStore(getProtocol());
            try {
                // connect to mailbox
                store.connect(getHost(), getUsername(), getPassword());
                // set your user_name and password
                Folder inbox = store.getFolder(FOLDER_INBOX);
                try {
                    // set folder from where to read mails and access type
                    inbox.open(Folder.READ_WRITE);
                    // handle messages
                    for (Message message : inbox.getMessages()) {
                        try {
                            handler.onMessage(message);
                            deleteMessage(message);
                        } catch (InvalidMessageException e) {
                            System.err.println(e.toString());
                            //deleteMessage(message);
                        }
                    }
                } finally {
                    inbox.close(true);
                }
            } finally {
                store.close();
                System.out.println("Disconnected from mailbox");
            }
        } catch (Exception e) {
            //System.err.println(e.toString());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param message
     * @throws MessagingException
     */
    private void deleteMessage(Message message) throws MessagingException {
        message.setFlag(Flags.Flag.DELETED, true);
        System.out.println("Deleted message from mailbox");
    }

}
