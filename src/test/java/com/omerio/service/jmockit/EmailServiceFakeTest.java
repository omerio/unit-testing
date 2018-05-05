package com.omerio.service.jmockit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.omerio.model.EmailMessage;
import com.omerio.service.EmailServiceImpl;
import com.omerio.test.TestUtils;
import com.omerio.test.category.UnitTest;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;

/**
 * 
 * @author omerio
 *
 */
//either include JMockit dependency before JUnit or use @RunWith
//@RunWith(JMockit.class)
@Category(UnitTest.class)
public class EmailServiceFakeTest {

    // the class being tested
    @Tested EmailServiceImpl emailService;

    @BeforeClass
    public static void beforeClass() {
        // to apply the FakeTransport initialize it here
        // new FakeTransport();
    }

    @Test
    public void testSendEmail() throws MessagingException {

        /**********
         * Arrange
         **********/
        final String message = "The sun is shining and the grass is green";
        final String subject = "Hello World";
        final String fromEmail = "test@domain.com";
        final String toEmail = "example@example.com";
        final EmailMessage email = new EmailMessage(message, subject, fromEmail, toEmail);

        // Fake is created as an anonymous inner class
        // http://jmockit.github.io/tutorial/Faking.html
        new MockUp<Transport>() {

            @Mock
            public void send(Invocation invocation, Message msg) throws MessagingException, IOException {
                assertNotNull(msg);

                assertEquals(msg.getSubject(), subject);
                assertEquals(msg.getContent(), message);
                assertEquals(msg.getFrom()[0], (new InternetAddress(fromEmail)));
                assertEquals(msg.getAllRecipients()[0], (new InternetAddress(toEmail)));

                // access the invocation context to verify the method is called once
                // http://jmockit.github.io/tutorial/Faking.html#invocation
                assertEquals(invocation.getInvocationCount(), 1);
            }
        };

        /**********
         * Act
         **********/

        emailService.sendEmail(email);

        /**********
         * Assert
         **********/

        // all asserts are done inside the fake method implementation above ^

        // verify call to real method
        Message mimeMsg = TestUtils.createMimeMessage();
        try {
            Transport.send(mimeMsg, new Address[] {new InternetAddress("test@example.com")});
            fail("Expected MessagingException not thrown");

        } catch(MessagingException e) {	
            assertEquals(e.getMessage(), "Could not connect to SMTP host: localhost, port: 25");
        }
    }


    /**
     * This is another example of using a Fake class, this creates a reusable fake
     * as compared to method local anonymous Fake classes 
     * @author omerio
     *
     */
    public final class FakeTransport extends MockUp<Transport> {

        /**
         * passing invocation context as first parameter is optional
         * @param invocation
         * @param msg
         * @throws MessagingException
         */
        @Mock
        public void send(Invocation invocation, Message msg) throws MessagingException {
            assertNotNull(msg);

            // create reusable outcome conditions
            if("Error".equals(msg.getSubject())) {
                throw new MessagingException("Failed to send email message");
            }

            // optionally access the invocation context 

            // access the invocation context to verify the method is called once
            // assertEquals(invocation.getInvocationCount(), 1);

            // executes the real code of the faked method
            // invocation.proceed();
        }


    }
}
