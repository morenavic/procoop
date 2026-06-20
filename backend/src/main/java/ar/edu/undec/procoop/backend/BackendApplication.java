package ar.edu.undec.procoop.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		System.out.println("Clientes123!: " + encoder.encode("Clientes123!"));
//		System.out.println("Admin123!: " + encoder.encode("Admin123!"));
	}
}