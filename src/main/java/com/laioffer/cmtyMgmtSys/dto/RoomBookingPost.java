package com.laioffer.cmtyMgmtSys.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laioffer.cmtyMgmtSys.vo.CommonRoomView;
import com.laioffer.cmtyMgmtSys.vo.UserView;
import lombok.Data;

import java.util.Date;

@Data
public class RoomBookingPost {
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
}
