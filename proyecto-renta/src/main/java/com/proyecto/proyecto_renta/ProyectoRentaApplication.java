package com.proyecto.proyecto_renta;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.proyecto.proyecto_renta.application.services.UserService;
import com.proyecto.proyecto_renta.domain.entities.Role;
import com.proyecto.proyecto_renta.domain.entities.User;
import com.proyecto.proyecto_renta.infrastructure.repositories.Role.RoleRepository;

@SpringBootApplication
public class ProyectoRentaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoRentaApplication.class, args);
    }

    @Bean
    CommandLineRunner initAdminUser(UserService userService, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userService.findOneByUsername("admin").isEmpty()) {
                Optional<Role> adminRoleOpt = roleRepository.findByName("ADMIN");
                if (adminRoleOpt.isPresent()) {
                    Role adminRole = adminRoleOpt.get();

                    User adminUser = new User();
                    adminUser.setName("admin");
                    adminUser.setEmail("karen@gmail.com");
                    adminUser.setPassword(passwordEncoder.encode("1234567890"));
                    adminUser.setAddress("Administrative Address");
                    adminUser.setPhone("3232483683");
                    adminUser.setActive(true);
                    adminUser.setRole(adminRole);

                    userService.save(adminUser);

                    System.out.println("Default user created successfully.");
                }
            } else {
                System.out.println("User already exists.");
            }
        };
    }

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
