package com.suny.soapApp01.postFi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.stream.StreamSource;

import com.postfinance.customer.registration.CustomerRegistrationMessage;

import com.postfinance.process.Envelope;
import org.junit.jupiter.api.Test;

import b2bservice.ebill.ebs.swisspost_ch.DownloadFile;
import b2bservice.ebill.ebs.swisspost_ch.ObjectFactory;


public class PostFiSerializerTest {

    private final String TEST_REG_FILE = "subsc_BillerId_ddMMyyyyHHmmss.xml";

    private final String TEST_RRO_FILE = "ProcessProtocol_Example.xml";

    private PostFiSerializer serializer;

    @Test
    void testDownloadFile() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_REG_FILE);
        assertTrue(is.available() > 0);
        ObjectFactory factory = new ObjectFactory();
        assertDoesNotThrow(() -> {
            DownloadFile file = factory.createDownloadFile();
            file.setData(factory.createDownloadFileData(is.readAllBytes()));
            file.setFilename(factory.createDownloadFileFilename("testRegistrations.xml"));
        });

    }

    @Test
    void testRegistrationUnmarshal() {
        serializer = new PostFiSerializer();
        assertDoesNotThrow(() -> {
            InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_REG_FILE);
            ObjectFactory factory = new ObjectFactory();
            DownloadFile file = factory.createDownloadFile();
            file.setData(factory.createDownloadFileData(is.readAllBytes()));
            file.setFilename(factory.createDownloadFileFilename("testRegistrations.xml"));
            System.out.println(String.format("regs: %s", new String(file.getData().getValue(), StandardCharsets.UTF_8)));
            CustomerRegistrationMessage regMsgs = (CustomerRegistrationMessage) serializer.getRegistrationMarshaller()
                    .unmarshal(new StreamSource(new ByteArrayInputStream(file.getData().getValue())));

            assertTrue(regMsgs.getCustomerRegistration().size() == 6);

        });
    }
    @Test
    void testProcessProtocolUnmarshal() {
        serializer = new PostFiSerializer();
        assertDoesNotThrow(() -> {
            InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_RRO_FILE);
            assertTrue(is.available() > 0);

            Envelope env = (Envelope) serializer.getProcessProtocolMarshaller()
                    .unmarshal(new StreamSource(is));

            assertTrue(env.getBody().getDeliveryDate().equals("20120522"));
            assertTrue(env.getBody().getBillerID().equals("41101000000111111"));
            assertTrue(env.getBody().getRejectedBills().getBill().size() == 1);

        });
    }
}
