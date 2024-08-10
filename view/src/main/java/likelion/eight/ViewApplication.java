package likelion.eight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewApplication.class, args);

/*		ApplicationContext applicationContext = SpringApplication.run(ViewApplication.class, args);

		// Get all bean names
		String[] beanNames = applicationContext.getBeanDefinitionNames();

		// Sort bean names for easier reading
		java.util.Arrays.sort(beanNames);

		// Print out each bean's name and the corresponding object
		for (String beanName : beanNames) {
			Object bean = applicationContext.getBean(beanName);
			System.out.println("Bean Name: " + beanName + " | Bean Object: " + bean.getClass().getName());
		}*/
	}
}
