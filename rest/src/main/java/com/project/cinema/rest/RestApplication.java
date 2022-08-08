package com.project.cinema.rest;

import com.project.cinema.data.entity.Genre;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.entity.Ticket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableFeignClients("com.project")
@ComponentScan({"com.project.cinema.api" , "com.project.cinema.rest", "com.project.cinema.core", "com.project.cinema.data" ,})
@EnableJpaRepositories("com.project.cinema.data")
@EntityScan(
        basePackages = "com.project.cinema.core",
        basePackageClasses = {ProjectionEntity.class, Genre.class, Ticket.class}
)
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
