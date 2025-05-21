package com.proyecto.proyecto_renta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyecto.proyecto_renta.domain.entities.Role;
import com.proyecto.proyecto_renta.infrastructure.repositories.Role.RoleRepository;

@SpringBootApplication
public class ProyectoRentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoRentaApplication.class, args);
	}
	@Bean
    CommandLineRunner init(RoleRepository rolRepository) {
        return args -> {
            if (rolRepository.count() == 0) {
                Role admin = new Role();
                admin.setName("ADMIN");
                rolRepository.save(admin);

                Role proveedor = new Role();
                proveedor.setName("PROVIDER");
                rolRepository.save(proveedor);

                Role cliente = new Role();
                cliente.setName("CLIENT");
                rolRepository.save(cliente);
            }
        };
    }


}
