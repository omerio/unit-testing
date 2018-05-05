package com.omerio.service;

import javax.mail.MessagingException;

import com.omerio.model.EmailMessage;

/**
 * @author omerio
 *
 */
public interface EmailService {

    /**
     * Send an email
     * 
     * @param emailMsg - email message to send
     * @throws MessagingException - if sending fails
     */
    void sendEmail(EmailMessage emailMsg) throws MessagingException;

}
