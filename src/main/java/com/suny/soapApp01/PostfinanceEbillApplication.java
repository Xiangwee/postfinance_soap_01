package com.suny.soapApp01;

import java.net.MalformedURLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PostfinanceEbillApplication {

        static ConfigurableApplicationContext applicationContext;
        public static void main(String[] args) throws MalformedURLException {
                applicationContext = SpringApplication.run(PostfinanceEbillApplication.class, args);
        }

}
