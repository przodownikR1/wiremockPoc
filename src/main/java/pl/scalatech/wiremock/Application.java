package pl.scalatech.wiremock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);

		ApplicationContext context = springApplication.run(args);
		Application app = context.getBean(Application.class);

		app.printApplicationInfo(context.getEnvironment());
	}

	private void printApplicationInfo(Environment environment) {
		log.info("\n-------------------------------------------------------\n"
						+ "\tApplication '{}' is up and running! Access URLs:\n"
						+ "\tLocal: \t\thttp://127.0.0.1:{}\n"
						+ "\tActive Profiles: {}\n"
						+ "\tDefault Profiles: {}\n"
						+ "----------------------------------------------------------",
				environment.getProperty("spring.application.name"),
				environment.getProperty("server.port"),
				environment.getActiveProfiles(),
				environment.getDefaultProfiles());
	}

}