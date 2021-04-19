package com.laioffer.cmtyMgmtSys.service;

import com.laioffer.cmtyMgmtSys.dao.RoomBookingRepository;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomBookingService {
    @Autowired
    RoomBookingRepository roomBookingRepository;

    public List<RoomBooking> getAllEvents(){
        return roomBookingRepository.findAll();
    }

    public RoomBooking createRoomBooking(RoomBooking roomBooking){
        return roomBookingRepository.save(roomBooking);
    }

    public RoomBooking updateRoomBookingById(Long id, RoomBooking newRoomBooking){
        RoomBooking target = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not exist with id: " + id));
        RoomBooking updatedRoomBooking = newRoomBooking;
        updatedRoomBooking.setId(id);
        return roomBookingRepository.save(updatedRoomBooking);
    }
}
