package likelion.eight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
public class ViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewApplication.class, args);
	}
}
