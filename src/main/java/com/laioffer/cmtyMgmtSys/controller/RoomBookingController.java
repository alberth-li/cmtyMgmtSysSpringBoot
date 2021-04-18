package com.laioffer.cmtyMgmtSys.controller;

import com.laioffer.cmtyMgmtSys.dao.RoomBookingRepository;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class RoomBookingController {

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @GetMapping("/events")
    public List<RoomBooking> getEvents() {
        return this.roomBookingRepository.findAll();
    }

    // create resident rest api
    @PostMapping("/events")
    public RoomBooking createRoomBooking(@RequestBody RoomBooking event) {
        return this.roomBookingRepository.save(event);
    }

    // update resident rest api
    @PutMapping("/events/{id}")
    public ResponseEntity<RoomBooking> updateRoomBooking(@PathVariable Long id, @RequestBody RoomBooking eventDetails) {
        RoomBooking event = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not exist with id: " + id));
        event.setStartTime(eventDetails.getStartTime());
        event.setEndTime(eventDetails.getEndTime());
        event.setTimeCreated(eventDetails.getTimeCreated());
        event.setCommonRoom(eventDetails.getCommonRoom());
        event.setBooker(eventDetails.getBooker());
        RoomBooking updatedEvent = roomBookingRepository.save(event);
        return ResponseEntity.ok(updatedEvent);
    }
}
