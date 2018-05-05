package com.omerio.service.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.omerio.model.EmailMessage;
import com.omerio.service.EmailServiceImpl;
import com.omerio.test.TestUtils;
import com.omerio.test.category.UnitTest;

/**
 * 
 * @author omerio
 *
 */
@Category(UnitTest.class)
@RunWith(PowerMockRunner.class) // <-- Must run with PowerMockRunner
public class EmailServiceMockTest {

    @InjectMocks EmailServiceImpl emailService;

    @Test
    @PrepareForTest({ Transport.class })
    public void testSendEmail() throws Exception {

        /**********
         * Arrange
         **********/
        final String message = "The sun is shining and the grass is green";
        final String subject = "Hello World";
        final String fromEmail = "test@domain.com";
        final String toEmail = "example@example.com";
        final EmailMessage email = new EmailMessage(message, subject, fromEmail, toEmail);

        // We can't do this with Mockito, we have to use PowerMock with Mockito
        // Mockito.mock(Transport.class);

        // To mock a static class using PowerMock
        PowerMockito.mockStatic(Transport.class); 
        PowerMockito.doNothing().when(Transport.class, "send", any(Message.class)); 

        /**********
         * Act
         **********/

        emailService.sendEmail(email);

        /**********
         * Assert
         **********/
        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);
        // Different from Mockito, always use PowerMockito.verifyStatic(Class) first
        // to start verifying behavior
        PowerMockito.verifyStatic(Transport.class, Mockito.times(1));
        // IMPORTANT:  Call the static method you want to verify
        Transport.send(argument.capture());

        Message msg = argument.getValue();
        assertNotNull(msg);

        assertEquals(msg.getSubject(), subject);
        assertEquals(msg.getContent(), message);
        assertEquals(msg.getFrom()[0], (new InternetAddress(fromEmail)));
        assertEquals(msg.getAllRecipients()[0], (new InternetAddress(toEmail)));

        Message mimeMsg = TestUtils.createMimeMessage();
        // verify call to real method
        // nothing happens, method is fully mocked
        Transport.send(mimeMsg, new Address[] {new InternetAddress("test@example.com")});
    }

}
