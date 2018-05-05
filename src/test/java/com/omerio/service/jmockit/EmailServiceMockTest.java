package com.omerio.service.jmockit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.omerio.model.EmailMessage;
import com.omerio.service.EmailServiceImpl;
import com.omerio.test.TestUtils;
import com.omerio.test.category.UnitTest;

import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;

/**
 * 
 * @author omerio
 *
 */
//either include JMockit dependency before JUnit or use @RunWith
//@RunWith(JMockit.class)
@Category(UnitTest.class)
public class EmailServiceMockTest {

    // the class being tested
    @Tested EmailServiceImpl emailService;

    // Transport is mocked throughout this test class
    @Mocked Transport transport;

    @Test
    public void testSendEmail(
            // @Mocked Transport unused <-- optionally Transport mocking at method level only
            ) throws MessagingException, IOException {

        /**********
         * Arrange
         **********/
        final String message = "The sun is shining and the grass is green";
        final String subject = "Hello World";
        final String fromEmail = "test@domain.com";
        final String toEmail = "example@example.com";
        final EmailMessage email = new EmailMessage(message, subject, fromEmail, toEmail);

        /**********
         * Act
         **********/

        emailService.sendEmail(email);


        /**********
         * Assert
         **********/

        new Verifications() {{
            Message msg;
            // capture the message being passed to the method so we can verify it
            Transport.send(msg = withCapture()); times = 1;

            assertEquals(msg.getContent(), message);

            Address [] address = msg.getRecipients(RecipientType.TO);
            assertEquals(address.length, 1);
            assertEquals(address[0].toString(), toEmail);

            assertEquals(msg.getSubject(), subject);
            assertEquals(msg.getFrom()[0], (new InternetAddress(fromEmail)));
        }};

        Message mimeMsg = TestUtils.createMimeMessage();
        // verify call to real method
        // nothing happens, method is fully mocked
        Transport.send(mimeMsg, new Address[] {new InternetAddress("test@example.com")});

    }

}
