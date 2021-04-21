package com.laioffer.cmtyMgmtSys.service;

import com.laioffer.cmtyMgmtSys.config.dto.UpdateResponse;
import com.laioffer.cmtyMgmtSys.dao.CommonRoomRepository;
import com.laioffer.cmtyMgmtSys.dao.RoomBookingRepository;
import com.laioffer.cmtyMgmtSys.dto.RoomBookingPost;
import com.laioffer.cmtyMgmtSys.entity.CommonRoom;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import com.laioffer.cmtyMgmtSys.entity.User;
import com.laioffer.cmtyMgmtSys.vo.RoomBookingView;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomBookingService {

    @Autowired
    RoomBookingRepository roomBookingRepository;
    @Autowired
    CommonRoomRepository commonRoomRepository;
    private Long curUserId = 1L;
    private void findCurUser(){
        Object curPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (curPrincipal instanceof User) {
            curUserId = ((User) curPrincipal).getId();
        }
    }

    public List<RoomBookingView> getAllEvents() {
        List<RoomBookingView> result = new ArrayList<>();
        for (RoomBooking rb:roomBookingRepository.findAll()
             ) {
            result.add(new RoomBookingView(rb));
        }
        return result;
    }

    public RoomBookingPost createRoomBooking(RoomBookingPost roomBookingRequest) {
        roomBookingRepository.save(toRoomBooking(roomBookingRequest));
        return roomBookingRequest;
    }

    private RoomBooking toRoomBooking(RoomBookingPost post){

        //Optional<RoomBooking> target = roomBookingRepository.findById(post.getId());
        RoomBooking result;
        /*
        if(target.isPresent()){
            result = target.get();
            result.setStartTime(post.getStartTime());
            result.setEndTime(post.getEndTime());
            result.setCommonRoom(commonRoomRepository.findById(post.getRoom().getId()).get());
        }else{

        }
         */
        result = new RoomBooking();
        result.setStartTime(post.getStartTime());
        result.setEndTime(post.getEndTime());
        result.setCommonRoom(commonRoomRepository.findById(post.getRoom().getId()).get());
        return result;
    }

    /*
    private RoomBookingView toRoomBookingView(RoomBooking booking){
        RoomBookingView result = new RoomBookingView();
        result.setId(booking.getId());
        result.getUser().setId(booking.getCreatedBy());
        result.setCreatedDate(booking.getCreatedDate());
        result.setStartTime(booking.getStartTime());
        result.setEndTime(booking.getEndTime());
        result.getRoom().setId(booking.getCRoom().getId());
        return result;
    }
     */

    public Map<String, UpdateResponse> updateRoomBookingById(Long id, RoomBookingPost updateRequest) {
        //get the RoomBooking Entity that it want to modify
        findCurUser();
        RoomBooking target = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not exist with id: " + id));

        Map<String, UpdateResponse> response = new HashMap<>();
        if (! hasAccess(target.getCreatedBy(),curUserId)) {
            response.put("updated", new UpdateResponse(Boolean.FALSE, "You can only update your own bookings."));
        } else if (hasOverlap(target, updateRequest)) {
            response.put("updated", new UpdateResponse(Boolean.FALSE, "Room is not available for the given time period."));
        } else {
            target.setCommonRoom(commonRoomRepository.findById(updateRequest.getRoom().getId()).get());
            target.setStartTime(updateRequest.getStartTime());
            target.setEndTime(updateRequest.getEndTime());
            roomBookingRepository.save(target);
            response.put("updated", new UpdateResponse(Boolean.TRUE, null));
        }
        return response;
    }

    public Map<String, Boolean> deleteRoomBooking(@PathVariable Long id) {
        findCurUser();
        RoomBooking event = roomBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not exist with id: " + id));
        Map<String, Boolean> response = new HashMap<>();
        if (! hasAccess(event.getCreatedBy(), curUserId)) {
            response.put("deleted", Boolean.FALSE);
        } else {
            roomBookingRepository.delete(event);
            response.put("deleted", Boolean.TRUE);
        }
        return response;
    }

    private boolean hasAccess(Long userId, Long currentUserId) {
        return Objects.nonNull(userId) && Objects.nonNull(currentUserId) && userId.equals(currentUserId);
    }

    private boolean hasOverlap(RoomBooking target, RoomBookingPost updateRequest) {
        Date startDate = updateRequest.getStartTime();
        Date endDate = updateRequest.getEndTime();
        Date originalStart = target.getStartTime();
        Date originalEnd = target.getEndTime();
        CommonRoom room = commonRoomRepository.findById(updateRequest.getRoom().getId()).get();
        List<RoomBooking> bookings = roomBookingRepository.findAllByCommonRoomAndStartTimeLessThanAndEndTimeGreaterThan(room, startDate, startDate);
        if (! bookings.isEmpty()) {
            return true;
        }
        bookings = roomBookingRepository.findAllByCommonRoomAndStartTimeLessThanAndEndTimeGreaterThan(room, endDate, endDate);
        if (! bookings.isEmpty()) {
            return true;
        }
        bookings = roomBookingRepository.findAllByCommonRoomAndStartTimeGreaterThanAndEndTimeLessThan(room, startDate, endDate);
        if (! bookings.isEmpty()) {
            return true;
        }
        bookings = roomBookingRepository.findAllByCommonRoomAndStartTimeEqualsOrCommonRoomAndEndTimeEquals(room, startDate, room, endDate);
        if(startDate.equals(originalStart) || endDate.equals(originalEnd)){
            return false;
        }else{
            return !bookings.isEmpty();
        }
    }
}
