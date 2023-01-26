package com.saldmy.conferencemanagementrest.configuration;

import com.saldmy.conferencemanagementrest.entity.Conference;
import com.saldmy.conferencemanagementrest.entity.ConferenceStatus;
import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.entity.RegistrationId;
import com.saldmy.conferencemanagementrest.entity.User;
import com.saldmy.conferencemanagementrest.entity.UserRole;
import com.saldmy.conferencemanagementrest.repository.ConferenceRepository;
import com.saldmy.conferencemanagementrest.repository.RegistrationRepository;
import com.saldmy.conferencemanagementrest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Configuration
class PreloadDatabase {

    @Bean
    @Order(1)
    CommandLineRunner preloadConferences(ConferenceRepository conferenceRepository) {
        return args -> {
            Conference finished = new Conference(
                    "Environmental Problems in 21st Century",
                    "12/2 M. Kobylianskoyi Str, Lviv, Ukraine",
                    200,
                    LocalDateTime.of(2021, Month.APRIL, 11, 18, 0),
                    Duration.ofHours(2)
            );

            finished.setStatus(ConferenceStatus.FINISHED);
            conferenceRepository.save(finished);

            Conference registered = new Conference(
                    "Principles of Effective Developers",
                    "45 Franka Street, Kropyvnytskii, Ukraine",
                    45,
                    LocalDateTime.of(2023, Month.JANUARY, 26, 20, 30),
                    Duration.ofMinutes(50)
            );

            conferenceRepository.save(registered);

            Conference pastCancelled = new Conference(
                    "Effective medicine during war",
                    "226 Druzhby Narodiv Str, Kharkiv, Ukraine",
                    236,
                    LocalDateTime.of(2022, Month.MAY, 15, 17, 25),
                    Duration.ofHours(3)
            );

            pastCancelled.setStatus(ConferenceStatus.CANCELLED);
            conferenceRepository.save(pastCancelled);

            Conference futureCancelled = new Conference(
                    "NodeJs performance problems",
                    "45 Shevchenka Str, Uzhhorod, Ukraine",
                    89,
                    LocalDateTime.of(2023, Month.JULY, 4, 12, 0),
                    Duration.ofHours(1)
            );

            futureCancelled.setStatus(ConferenceStatus.CANCELLED);
            conferenceRepository.save(futureCancelled);
        };
    }


    @Bean
    @Order(2)
    CommandLineRunner preloadUsers(UserRepository userRepository) {
        return args -> {
            User admin = new User(
                    "admin@mail.com",
                    "$2a$08$vDKB0RvoSUAOMoyrxKWZXeDiGfAaxCPRlby1raUeSFr6pYfl32BRO",
                    "Dmytro",
                    "Salo",
                    LocalDate.of(1997, Month.JUNE, 7)
            );
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);

            userRepository.save(new User(
                "user1@mail.com",
                "$2a$08$iSHcpbGzD.zxygVqkkXaVeH5/054aeNpPfBfYkky6Xse9TIAzEIZq",
                "Arnold",
                "Schwarzenegger",
                LocalDate.of(1971, Month.APRIL, 1)
            ));
        };
    }

    @Bean
    @Order(3)
    CommandLineRunner preloadRegistrations(
            RegistrationRepository registrationRepository,
            UserRepository userRepository,
            ConferenceRepository conferenceRepository
    ) {
        return args -> {
            User user = userRepository.findAll().stream()
                    .filter(usr -> usr.getFirstName().equals("Arnold"))
                    .findFirst()
                    .orElseThrow();

            Conference conference = conferenceRepository.findAll().stream()
                    .filter(conf -> conf.getDuration().equals(Duration.ofMinutes(50)))
                    .findFirst()
                    .orElseThrow();

            registrationRepository.save(new Registration(new RegistrationId(user.getId(), conference.getId())));
        };
    }

}
