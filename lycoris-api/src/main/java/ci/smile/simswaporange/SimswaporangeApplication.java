package ci.smile.simswaporange;

import com.google.gson.Gson;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.Map;

@EnableAsync
@EnableCaching
@EnableBatchProcessing
@SpringBootApplication
public class SimswaporangeApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SimswaporangeApplication.class, args);

	}

}
