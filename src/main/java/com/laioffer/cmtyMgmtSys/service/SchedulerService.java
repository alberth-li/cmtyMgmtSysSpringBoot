package com.laioffer.cmtyMgmtSys.service;

import com.laioffer.cmtyMgmtSys.dao.SchedulerRepository;
import com.laioffer.cmtyMgmtSys.entity.CommonRoom;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import com.laioffer.cmtyMgmtSys.entity.User;
import org.apache.commons.collections.functors.FalsePredicate;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.bouncycastle.cert.crmf.ProofOfPossessionSigningKeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
@Service
public class SchedulerService {

    @Autowired
    private SchedulerRepository schedulerRepo;


    public List<RoomBooking> getAllBookings() {
        return schedulerRepo.findAll();
    }


    public Optional<RoomBooking> getBookingById(Long id) {
        System.out.println(schedulerRepo.findById(id) + " " + schedulerRepo.findById(id).getClass());
        return schedulerRepo.findById(id);
    }


    public List<RoomBooking> getBookingByWeekByUser(Long user_id){
        List<RoomBooking> all = schedulerRepo.findAll();
        List<RoomBooking> ret = new ArrayList<>();;
        for (RoomBooking booking : all) {
            if(booking.getCreatedBy().equals(user_id)){
                ret.add(booking);
            }
        }
        return ret;
    }




    public List<RoomBooking> getBookingByWeek(){
        Calendar c=Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        Date past_sunday = (c.getTime());
        c.add(Calendar.DATE,7);
        Date next_sunday = (c.getTime());

        List<RoomBooking> all = schedulerRepo.findAll();
        List<RoomBooking> ret = new ArrayList<>();;
        for (RoomBooking booking : all) {
            if((past_sunday.compareTo(booking.getStartTime()) <= 0)
                    && (next_sunday.compareTo(booking.getEndTime()) > 0) ){
                ret.add(booking);
            }

        }
        return ret;
    }


    public List<RoomBooking> getBookingByWeekByRoom(Long id) {

        Calendar c=Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);

        Date past_sunday = (c.getTime());
        c.add(Calendar.DATE,7);
        Date next_sunday = (c.getTime());

        List<RoomBooking> all = schedulerRepo.findAll();
        List<RoomBooking> ret = new ArrayList<>();

        for (RoomBooking booking : all) {
            if((past_sunday.compareTo(booking.getStartTime()) <= 0)
                    && (next_sunday.compareTo(booking.getEndTime()) > 0) ){
                if(booking.getCRoom() != null && booking.getCRoom().getId().equals(id)){
                    ret.add(booking);
                }
            }
        }
        return ret;

    }



    public RoomBooking addNewRoomBooking(RoomBooking newRoomBooking) {

        Long room_id = newRoomBooking.getCRoom().getId();
        Timestamp new_start = new Timestamp(newRoomBooking.getStartTime().getTime());
        Timestamp new_end = new Timestamp(newRoomBooking.getStartTime().getTime());

        List<RoomBooking> allweek_booking = getBookingByWeekByRoom(room_id);
        for (RoomBooking booking : allweek_booking) {
            Timestamp cur_start = new Timestamp(booking.getStartTime().getTime());
            Timestamp cur_end = new Timestamp(booking.getEndTime().getTime());
            //check time overlap with other bookings
            if ( (new_end.compareTo(cur_start) < 0) ){
                System.out.println("time conflict for other booking");
                return null;

                // throw new Exception("time conflict for other booking");
            }else if(new_start.compareTo(cur_end) < 0){
                System.out.println("time conflict for other booking");
                return null;

                // throw new Exception("time conflict for other booking");
            }
        }

        return schedulerRepo.save(newRoomBooking);
    }


    public List<RoomBooking> deleteAll() {
        List<RoomBooking> bookings = schedulerRepo.findAll();
        schedulerRepo.deleteAll();
        return bookings;
    }

    public String deleteById(Long id) {

        Long username = 12345678910L;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            username = ((User) principal).getId();
        }

        RoomBooking target = schedulerRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Event not exist with id: " + id));
        if (target.getCreatedBy().equals(username)) {

            schedulerRepo.deleteById(id);
            return ("deleted successfully");


        }
        return ("not allowed to delete");



    }

//    public String putByTime(Long id, Date start, Date end) {
//        if (schedulerRepo.existsById(id)) {
//            RoomBooking booking = schedulerRepo.findById(id).get();
//            booking.setStartTime(start);
//            booking.setEndTime(end);
//            schedulerRepo.save(booking);
//
//            return "Booking time of id: " + id + " reset successfully";
//        } else {
//            return "No record found";
//        }
//    }


    public RoomBooking updateRoomBookingById(Long id,RoomBooking newRoomBooking) {

        Long username = 12345678910L;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            username = ((User) principal).getId();
        }

        RoomBooking target = schedulerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not exist with id: " + id));

        if (target.getCreatedBy().equals(username)){
            Long room_id = newRoomBooking.getCRoom().getId();
            Timestamp new_start = new Timestamp(newRoomBooking.getStartTime().getTime());
            Timestamp new_end = new Timestamp(newRoomBooking.getStartTime().getTime());

            List<RoomBooking> allweek_booking = getBookingByWeekByRoom(room_id);
            for (RoomBooking booking : allweek_booking) {
                Timestamp cur_start = new Timestamp(booking.getStartTime().getTime());
                Timestamp cur_end = new Timestamp(booking.getEndTime().getTime());
                //check time overlap with other bookings
                if ( (new_end.compareTo(cur_start) < 0) ){
                    return target;
                    // throw new Exception("time conflict for other booking");
                }else if(new_start.compareTo(cur_end) < 0){
                    return target;
                    // throw new Exception("time conflict for other booking");
                }
            }

            RoomBooking updatedRoomBooking = newRoomBooking;
            updatedRoomBooking.setId(id);
            System.out.println("changed!");

            return schedulerRepo.save(updatedRoomBooking);

        }

       // throw new Exception("user_id not allowed for update");
        return target;
    }
}

