package com.laioffer.cmtyMgmtSys.service;

import com.laioffer.cmtyMgmtSys.dao.SchedulerRepository;
import com.laioffer.cmtyMgmtSys.entity.CommonRoom;
import com.laioffer.cmtyMgmtSys.entity.RoomBooking;
import org.apache.commons.collections.functors.FalsePredicate;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.bouncycastle.cert.crmf.ProofOfPossessionSigningKeyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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



    public RoomBooking addNewRoomBooking(RoomBooking booking) {
        return schedulerRepo.save(booking);
    }


    public List<RoomBooking> deleteAll() {
        List<RoomBooking> bookings = schedulerRepo.findAll();
        schedulerRepo.deleteAll();
        return bookings;
    }

    public String deleteById(Long id) {
        if (!schedulerRepo.existsById(id)) {
            return "No Entry Found";
        } else {
            schedulerRepo.deleteById(id);
            return "Delete Successfully";
        }
    }

    public String putByTime(Long id, Date start, Date end) {
        if (schedulerRepo.existsById(id)) {
            RoomBooking booking = schedulerRepo.findById(id).get();
            booking.setStartTime(start);
            booking.setEndTime(end);
            schedulerRepo.save(booking);

            return "Booking time of id: " + id + " reset successfully";
        } else {
            return "No record found";
        }
    }
}
