package com.laioffer.cmtyMgmtSys.controller;

import com.laioffer.cmtyMgmtSys.dao.RoomBookingRepository;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import com.laioffer.cmtyMgmtSys.service.RoomBookingService;
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
    RoomBookingService roomBookingService;

    @GetMapping("/events")
    public List<RoomBooking> getEvents() {
        return this.roomBookingService.getAllEvents();
    }

    // create resident rest api
    @PostMapping("/events")
    public RoomBooking createRoomBooking(@RequestBody RoomBooking event) {
        return this.roomBookingService.createRoomBooking(event);
    }

    // update resident rest api
    @PutMapping("/events/{id}")
    public ResponseEntity<RoomBooking> updateRoomBooking(@PathVariable Long id, @RequestBody RoomBooking eventDetails) {
        /*
        RoomBooking event = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not exist with id: " + id));
        event.setStartTime(eventDetails.getStartTime());
        event.setEndTime(eventDetails.getEndTime());
        event.setTimeCreated(eventDetails.getTimeCreated());
        event.setCommonRoom(eventDetails.getCommonRoom());
        event.setBooker(eventDetails.getBooker());
         */
        return ResponseEntity.ok(roomBookingService.updateRoomBookingById(id, eventDetails));
    }
}
