package com.laioffer.cmtyMgmtSys.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.laioffer.cmtyMgmtSys.dao.CommonRoomRepository;
import com.laioffer.cmtyMgmtSys.dao.RoomBookingRepository;
import com.laioffer.cmtyMgmtSys.entity.CommonRoom;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import lombok.Data;
import lombok.NonNull;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
public class RoomBookingPostDto {
    @NonNull
    private Date startTime;
    @NonNull
    private Date endTime;
    @JsonProperty("croom")
    private Long cRoomId;
}
