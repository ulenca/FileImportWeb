package general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import general.rest_api.PersonController;


@SpringBootApplication
public class FileImportWebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FileImportWebApplication.class, args);

	}

}
