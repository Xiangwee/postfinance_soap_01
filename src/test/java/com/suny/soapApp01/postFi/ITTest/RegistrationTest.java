package com.suny.soapApp01.postFi.ITTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import com.suny.soapApp01.postFi.PostFiSerializer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import b2bservice.ebill.ebs.swisspost_ch.ArrayOfDownloadFile;
import b2bservice.ebill.ebs.swisspost_ch.DownloadFile;
import b2bservice.ebill.ebs.swisspost_ch.ObjectFactory;
import b2bservice.ebill.swisspost.ch.B2BService;

public class RegistrationTest {
    
    @Mock
    private B2BService port;

    private PostFiSerializer serializer;

    private final String billerID = "41010186990657958";

    private final String TEST_REG_FILE = "subsc_BillerId_ddMMyyyyHHmmss.xml";

    private ArrayOfDownloadFile testRegs;

    @BeforeAll
    void prepareTests() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_REG_FILE);
        ObjectFactory factory = new ObjectFactory();
        DownloadFile file = factory.createDownloadFile();
        file.setData(factory.createDownloadFileData(is.readAllBytes()));
        file.setFilename(factory.createDownloadFileFilename("testRegistrations.xml"));
        testRegs = factory.createArrayOfDownloadFile();
        testRegs.getDownloadFile().add(file);
    }

    @Test
    void testRegistration() {
        assertNotNull(testRegs);
        when(port.getRegistrationProtocol(billerID, null, false)).thenReturn(testRegs);
        
    }
}
