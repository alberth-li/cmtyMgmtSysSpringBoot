package com.laioffer.cmtyMgmtSys.controller;

import com.laioffer.cmtyMgmtSys.dao.RoomBookingRepository;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import com.laioffer.cmtyMgmtSys.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // create event rest api
    @PostMapping("/events")
    public RoomBooking createRoomBooking(@RequestBody RoomBooking event) {
        return this.roomBookingService.createRoomBooking(event);
    }

    // update event rest api
    @PutMapping("/events/{id}")
    public RoomBooking updateRoomBooking(@PathVariable Long id, @RequestBody RoomBooking eventDetails) {
        return this.roomBookingService.updateRoomBookingById(id, eventDetails);
    }

    // delete event rest api
    @DeleteMapping("/events/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRoomBooking(@PathVariable Long id) {
        return this.roomBookingService.deleteRoomBooking(id);
    }
}
