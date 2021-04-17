package com.laioffer.cmtyMgmtSys.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "servicerequest")
@EntityListeners(AuditingEntityListener.class)
public class ServiceRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private ServiceRequestStatus status;

    @Enumerated(EnumType.STRING)
    private ServiceRequestType type;

    private String message;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date timeCreated;

    @Column(nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCompleted;

    @ManyToOne
    @JoinColumn(name = "apt_ID")
    private Apartment apt;

    @ManyToOne
    @JoinColumn(name = "resident_ID")
    private Resident requester;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public ServiceRequestType getType() {
        return type;
    }

    public void setType(ServiceRequestType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(Date timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public Apartment getApt() {
        return apt;
    }

    public void setApt(Apartment apt) {
        this.apt = apt;
    }

    public Resident getRequester() {
        return requester;
    }

    public void setRequester(Resident requester) {
        this.requester = requester;
    }
}
