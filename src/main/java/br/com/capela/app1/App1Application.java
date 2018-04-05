package br.com.capela.app1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.capela.app1.service.GameInfoService;

@SpringBootApplication
public class App1Application {
	
	public static void main(String[] args) {
		SpringApplication.run(App1Application.class, args);
	}
	
	@Configuration
	@Profile("SSL_PROFILE")
	public class WithSSL{
		public WithSSL() throws IOException {
			Properties systemProps = System.getProperties();
			Path trustStore = Files.createTempFile("clientkeystore", "");
			InputStream is = GameInfoService.class.getClassLoader().getResourceAsStream("clientkeystore");
			Files.copy(is, trustStore, StandardCopyOption.REPLACE_EXISTING); 
			systemProps.put( "javax.net.ssl.trustStore", trustStore.toString());
//			systemProps.put( "javax.net.ssl.trustStorePassword", "123456");
			System.setProperties(systemProps);
		}
	}
}
