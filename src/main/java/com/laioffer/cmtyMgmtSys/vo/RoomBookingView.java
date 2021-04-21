package com.laioffer.cmtyMgmtSys.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import lombok.Data;
import java.util.Date;

@Data
public class RoomBookingView {
    @JsonProperty("id")
    Long id;
    @JsonProperty("startTime")
    Date startTime;

    @JsonProperty("endTime")
    private Date endTime;

    @JsonProperty("timeCreated")
    Date createdDate;

    @JsonProperty("commonRoom")
    CommonRoomView room;

    @JsonProperty("booker")
    UserView user;

    public RoomBookingView(RoomBooking booking) {
            id = booking.getId();
            user = new UserView(booking.getCreatedBy());
            createdDate = booking.getCreatedDate();
            startTime = booking.getStartTime();
            endTime = booking.getEndTime();
            room = new CommonRoomView(booking.getCommonRoom().getId());
    }
}
