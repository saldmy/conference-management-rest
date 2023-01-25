package com.saldmy.conferencemanagementrest.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Conference {

    private @Id @GeneratedValue Long id;
    private String title;
    private String place;
    private Integer maxParticipants;
    private LocalDateTime start;
    private Duration duration;
    private ConferenceStatus status = ConferenceStatus.REGISTERED;
    private @ManyToMany Set<User> participants = new HashSet<>();
    private @CreationTimestamp LocalDateTime created;
    private @UpdateTimestamp LocalDateTime updated;

    public Conference() {}

    public Conference(String title, String place, Integer maxParticipants, LocalDateTime start, Duration duration) {
        this.title = title;
        this.place = place;
        this.maxParticipants = maxParticipants;
        this.start = start;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ConferenceStatus getStatus() {
        return status;
    }

    public void setStatus(ConferenceStatus status) {
        this.status = status;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conference that = (Conference) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", place='" + place + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", start=" + start +
                ", duration=" + duration +
                ", status=" + status +
                ", participants=" + participants +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

}
