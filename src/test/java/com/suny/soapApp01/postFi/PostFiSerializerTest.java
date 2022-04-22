package com.suny.soapApp01.postFi;

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

import static org.junit.jupiter.api.Assertions.*;


public class PostFiSerializerTest {

    private final String TEST_REG_FILE = "subsc_BillerId_ddMMyyyyHHmmss.xml";

    private final String TEST_RRO_FILE = "ProcessProtocol_Example.xml";

    private PostFiSerializer serializer;

    @Test
    void testRegistrationUnmarshal() {
        serializer = new PostFiSerializer();
        assertDoesNotThrow(() -> {
            InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_REG_FILE);
            assertNotNull(is);
            assertTrue(is.available() > 0);
            ObjectFactory factory = new ObjectFactory();
            DownloadFile file = factory.createDownloadFile();
            file.setData(factory.createDownloadFileData(is.readAllBytes()));
            file.setFilename(factory.createDownloadFileFilename("testRegistrations.xml"));
            System.out.println(String.format("regs: %s", new String(file.getData().getValue(), StandardCharsets.UTF_8)));
            CustomerRegistrationMessage regMsgs = (CustomerRegistrationMessage) serializer.getRegistrationMarshaller()
                    .unmarshal(new StreamSource(new ByteArrayInputStream(file.getData().getValue())));

            assertEquals(6, regMsgs.getCustomerRegistration().size());

        });
    }
    @Test
    void testProcessProtocolUnmarshal() {
        serializer = new PostFiSerializer();
        assertDoesNotThrow(() -> {
            InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_RRO_FILE);
            assert is != null;
            assertTrue(is.available() > 0);

            Envelope env = (Envelope) serializer.getProcessProtocolMarshaller()
                    .unmarshal(new StreamSource(is));

            assertEquals(1, env.getBody().getDeliveryDate().size());
            assertEquals("20120522", env.getBody().getDeliveryDate().get(0).getDate());
            assertEquals("41101000000111111", env.getBody().getBillerID());
            assertEquals(1, env.getBody().getRejectedBills().getBill().size());

        });
    }
}
