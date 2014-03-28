package com.appedia.bassat.job;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 *
 * @author Muz Omar
 */
public interface MailboxMessageHandler {

    /**
     *
     * @param message
     * @throws MessagingException
     */
    void onMessage(Message message) throws MessagingException;

}
