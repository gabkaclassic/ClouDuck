package org.gab.ClouDuck;

import org.gab.ClouDuck.handlers.Handler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
		@PropertySource("classpath:application.properties")
})
@ComponentScans(
		@ComponentScan("org.gab.ClouDuck")
)
public class ClouDuckApplication {

	public static void main(String[] args) {
		
		Handler.start();
		SpringApplication.run(ClouDuckApplication.class, args);
	}
}
