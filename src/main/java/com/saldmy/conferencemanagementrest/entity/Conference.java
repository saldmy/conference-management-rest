package com.saldmy.conferencemanagementrest.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class Conference {

    private @Id @GeneratedValue Long id;
    private String title;
    private String place;
    private Integer maxParticipants;
    private LocalDateTime start;
    private Duration duration;
    private ConferenceStatus status = ConferenceStatus.REGISTERED;
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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (maxParticipants != null ? !maxParticipants.equals(that.maxParticipants) : that.maxParticipants != null)
            return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (status != that.status) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        return updated != null ? updated.equals(that.updated) : that.updated == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (maxParticipants != null ? maxParticipants.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
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
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

}
