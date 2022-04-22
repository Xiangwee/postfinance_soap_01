package com.suny.soapApp01.postFi;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class PostFiSerializer {
    
    @Getter
    private final Jaxb2Marshaller webServiceMarshaller = new Jaxb2Marshaller();

    @Getter
    private final Jaxb2Marshaller registrationMarshaller = new Jaxb2Marshaller();

    @Getter
    private final Jaxb2Marshaller processProtocolMarshaller = new Jaxb2Marshaller();

    private static final String WS_PACKAGE = "b2bservice.ebill.ebs.swisspost_ch";

    private static final String REG_PACKAGE = "com.postfinance.CustomerRegistration";

    private static final String PRO_PACKAGE = "com.postfinance.process";

    public PostFiSerializer() {
        webServiceMarshaller.setPackagesToScan(WS_PACKAGE);
        registrationMarshaller.setPackagesToScan(REG_PACKAGE);
        processProtocolMarshaller.setPackagesToScan(PRO_PACKAGE);
    }
}
