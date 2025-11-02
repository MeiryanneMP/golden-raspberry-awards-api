package outsera.golden_raspberry_awards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "outsera.golden_raspberry_awards.model")
public class GoldenRaspberryAwardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryAwardsApplication.class, args);
	}

}
