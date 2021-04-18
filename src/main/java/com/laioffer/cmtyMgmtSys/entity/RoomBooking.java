package com.laioffer.cmtyMgmtSys.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "roombooking")
@EntityListeners(AuditingEntityListener.class)
public class RoomBooking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endTime;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date timeCreated;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private CommonRoom cRoom;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident booker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCrated) {
        this.timeCreated = timeCreated;
    }

    public CommonRoom getCommonRoom() {
        return cRoom;
    }

    public void setCommonRoom(CommonRoom cRoom) {
        this.cRoom = cRoom;
    }

    public Resident getBooker() {
        return booker;
    }

    public void setBooker(Resident booker) {
        this.booker = booker;
    }
}
