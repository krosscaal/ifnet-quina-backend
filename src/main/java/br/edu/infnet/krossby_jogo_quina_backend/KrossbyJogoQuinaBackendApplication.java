package br.edu.infnet.krossby_jogo_quina_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableFeignClients
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO) //Habilitada para serializacão objetos de paginação
public class KrossbyJogoQuinaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KrossbyJogoQuinaBackendApplication.class, args);
	}

}
