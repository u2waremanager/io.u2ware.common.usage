package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.core.env.ConfigurableEnvironment;
// import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


		// ConfigurableEnvironment environment = new StandardEnvironment();
		// environment.setActiveProfiles("stage");
		
		// SpringApplication sa = new SpringApplication(Application.class);
		// sa.setEnvironment(environment);
		// sa.run(args);

	}

}
