package com.proyecto.proyecto_renta;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.proyecto.proyecto_renta.domain.entities.Role;
import com.proyecto.proyecto_renta.infrastructure.repositories.Role.RoleRepository;

@SpringBootApplication
public class ProyectoRentaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoRentaApplication.class, args);
    }

    // @Bean
    // @Order(1)
    // CommandLineRunner init(RoleRepository rolRepository) {
    //     return args -> {
    //         if (rolRepository.count() == 0) {
    //             Role admin = new Role();
    //             admin.setName("ADMIN");
    //             rolRepository.save(admin);

    //             Role proveedor = new Role();
    //             proveedor.setName("PROVIDER");
    //             rolRepository.save(proveedor);

    //             Role cliente = new Role();
    //             cliente.setName("CLIENT");
    //             rolRepository.save(cliente);
    //         }
    //     };
    // }

    @Bean
    @Order(1)
    CommandLineRunner initDatabaseData(DataSource dataSource) {
        return args -> {
            System.out.println("Ejecutando script SQL...");
            executeSqlScript(dataSource);
            System.out.println("Se inserto todo correctamente...");
        };
    }

    private void executeSqlScript(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));
        populator.setContinueOnError(true);
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

}
