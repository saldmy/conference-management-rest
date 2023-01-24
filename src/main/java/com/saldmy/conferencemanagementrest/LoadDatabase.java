package com.saldmy.conferencemanagementrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ConferenceRepository conferenceRepository) {
        return args -> {
            Conference finishedConference = new Conference(
                    "Environmental Problems in 21st Century",
                    "12/2 M. Kobylianskoyi Street, Lviv, Ukraine",
                    200,
                    LocalDateTime.of(2021, Month.APRIL, 11, 18, 0),
                    Duration.ofHours(2)
            );

            finishedConference.setStatus(ConferenceStatus.FINISHED);
            conferenceRepository.save(finishedConference);

            conferenceRepository.save(new Conference(
                    "Principles of Effective Developers",
                    "45 Franka Street, Kropyvnytskii, Ukraine",
                    45,
                    LocalDateTime.of(2023, Month.JANUARY, 26, 20, 30),
                    Duration.ofMinutes(50)
            ));

            Conference cancelled = new Conference(
                    "Principles of Effective Developers",
                    "45 Franka Street, Kropyvnytskii, Ukraine",
                    45,
                    LocalDateTime.of(2023, Month.JANUARY, 26, 20, 30),
                    Duration.ofMinutes(50)
            );

            cancelled.setStatus(ConferenceStatus.CANCELLED);
            conferenceRepository.save(cancelled);

            conferenceRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
        };
    }

}
