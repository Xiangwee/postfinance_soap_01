package com.suny.soapApp01.postFi;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PostFiSerializer {
    
    private final Jaxb2Marshaller webServiceMarshaller = new Jaxb2Marshaller();

    private final Jaxb2Marshaller registrationMarshaller = new Jaxb2Marshaller();

    private static final String WS_PACKAGE = "b2bservice.ebill.ebs.swisspost_ch";

    private static final String REG_PACKAGE = "com.postfinance.CustomerRegistration";

    public PostFiSerializer() {
        webServiceMarshaller.setPackagesToScan(WS_PACKAGE);
        registrationMarshaller.setPackagesToScan(REG_PACKAGE);
    }

}
