package cavalcante.deVirtual_store.virtual_store1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VirtualStore1Application {

	public static void main(String[] args) {
		SpringApplication.run(VirtualStore1Application.class, args);
	}

}
