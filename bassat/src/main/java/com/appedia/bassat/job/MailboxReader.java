package com.appedia.bassat.job;

import javax.mail.MessagingException;

/**
 *
 * @author Muz Omar
 */
public class MailboxReader {

    private String host;
    private int port;
    private String username;
    private String password;

    public MailboxReader() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public void processInbox(MailboxMessageHandler handler) {
        handler.onMessage(message);
    }

}
