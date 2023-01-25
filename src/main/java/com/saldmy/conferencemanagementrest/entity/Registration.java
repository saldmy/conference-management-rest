package com.saldmy.conferencemanagementrest.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Registration {

    @EmbeddedId
    private RegistrationId id;

    @CreationTimestamp
    private LocalDateTime created;

    public Registration() {}

    public Registration(RegistrationId id) {
        this.id = id;
    }

    public RegistrationId getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setId(RegistrationId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Registration that = (Registration) o;

        if (!id.equals(that.id)) return false;
        return created != null ? created.equals(that.created) : that.created == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", created=" + created +
                '}';
    }

}
