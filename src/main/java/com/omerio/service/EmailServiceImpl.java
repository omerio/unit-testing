package com.omerio.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

/**
 * A simple service that uses Transport.send, in a real application you would use
 * some of the spring email services. Additionally, you would probably save emails
 * to the database and have an async task to send/retry
 *  
 * @author omerio
 *
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(com.omerio.model.EmailMessage emailMsg) throws MessagingException {

        // create email session, etc.
        Session session = Session.getInstance(new Properties()); 

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(emailMsg.getFromEmail()));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailMsg.getToEmail()));
        message.setSubject(emailMsg.getSubject());
        message.setText(emailMsg.getContent());

        Transport.send(message);


    }

}
