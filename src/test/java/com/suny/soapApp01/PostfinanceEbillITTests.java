package com.suny.soapApp01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import com.suny.soapApp01.postFi.PostFiSerializer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import b2bservice.ebill.swisspost.ch.B2BService;
import b2bservice.ebill.swisspost.ch.B2BService_Service;

@SpringBootTest
class PostfinanceEbillITTests {

	@Value("#{systemEnvironment['postFiUsername']}")
    private String postFiUsername;

    @Value("#{systemEnvironment['postFiPassword']}")
    private String postFiPassword;

	private final String billerID = "41010186990657958";

	private final String postFinanceWsdl = "https://ebill-ki.postfinance.ch/B2BService/B2BService.svc?singleWsdl";
	
	@Autowired
	PostFiSerializer serializer;

	@Test
	void contextLoads() {
	}

	@Test
	void publicvoidtestExecutePing() throws MalformedURLException {
		
		URL wsdlURL = new URL(postFinanceWsdl);
		B2BService_Service service = new B2BService_Service(wsdlURL, B2BService_Service.SERVICE);
		B2BService port = service.getUserNamePassword();

		Map<String, Object> requestCtx = ((BindingProvider)port).getRequestContext();
		requestCtx.put("ws-security.username", postFiUsername);
		requestCtx.put("ws-security.password", postFiPassword);

/* 		Client client = ClientProxy.getClient(port);
		LoggingOutInterceptor outLog = new LoggingOutInterceptor();
		outLog.setPrettyLogging(true);
		client.getOutInterceptors().add(outLog); */

		String result = port.executePing(billerID, null, null, null);

		assertNotNull(result);
		assertEquals(billerID, result);
	}

}
