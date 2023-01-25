package com.saldmy.conferencemanagementrest.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RegistrationId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "conference_id")
    private Long conferenceId;

    public RegistrationId() {}

    public RegistrationId(Long userId, Long conferenceId) {
        this.userId = userId;
        this.conferenceId = conferenceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationId that = (RegistrationId) o;

        if (!userId.equals(that.userId)) return false;
        return conferenceId.equals(that.conferenceId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + conferenceId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CourseRegistrationKey{" +
                "userId=" + userId +
                ", conferenceId=" + conferenceId +
                '}';
    }

}
