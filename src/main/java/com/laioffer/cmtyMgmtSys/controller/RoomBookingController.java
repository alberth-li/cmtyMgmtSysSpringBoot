package com.laioffer.cmtyMgmtSys.controller;

import com.laioffer.cmtyMgmtSys.dto.UpdateResponse;
import com.laioffer.cmtyMgmtSys.dto.RoomBookingPost;
import com.laioffer.cmtyMgmtSys.service.RoomBookingService;
import com.laioffer.cmtyMgmtSys.vo.RoomBookingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class RoomBookingController {
    @Autowired
    RoomBookingService roomBookingService;

    @GetMapping("/events")
    public List<RoomBookingView> getEvents() {
        return this.roomBookingService.getAllEvents();
    }

    // create event rest api
    @PostMapping("/events")
    public RoomBookingPost createRoomBooking(@RequestBody RoomBookingPost event) {
        return this.roomBookingService.createRoomBooking(event);
    }

    // update event rest api
    @PutMapping("/events/{id}")
    public ResponseEntity<String> updateRoomBooking(@PathVariable Long id, @RequestBody RoomBookingPost newRoomBooking) {
        //return this.roomBookingService.updateRoomBookingById(id, eventDetails);
        /*
        Resident resident = eventDetails.getBooker();

        resident = residentRepository.findById(resident.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Resident not exist with id: " + id));
        eventDetails.setBooker(resident);
         */
        Map<String, UpdateResponse> res = this.roomBookingService.updateRoomBookingById(id, newRoomBooking);
        if (!Boolean.TRUE.equals(res.get("updated").getSuccess())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res.get("updated").getReason());
        }
        return ResponseEntity.ok().build();
    }

    // delete event rest api
    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> deleteRoomBooking(@PathVariable Long id) {
        Map<String, Boolean> res = this.roomBookingService.deleteRoomBooking(id);
        if (!Boolean.TRUE.equals(res.get("deleted"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own bookings.");
        }
        return ResponseEntity.ok().build();
    }
}
