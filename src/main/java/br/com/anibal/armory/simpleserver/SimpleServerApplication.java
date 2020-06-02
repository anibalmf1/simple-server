package br.com.anibal.armory.simpleserver;

import br.com.anibal.armory.simpleserver.services.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SimpleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleServerApplication.class, args);
    }

}
