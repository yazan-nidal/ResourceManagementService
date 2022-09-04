package exp.exalt.ps;

import exp.exalt.ps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class ServerPoolApplication{

	public static void main(String[] args) {
		SpringApplication.run(ServerPoolApplication.class, args);
	}

}
