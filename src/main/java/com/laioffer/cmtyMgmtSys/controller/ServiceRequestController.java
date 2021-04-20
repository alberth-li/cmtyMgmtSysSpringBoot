package com.laioffer.cmtyMgmtSys.controller;

import com.laioffer.cmtyMgmtSys.entity.ServiceRequest;
import com.laioffer.cmtyMgmtSys.entity.ServiceRequestStatus;
import com.laioffer.cmtyMgmtSys.entity.ServiceRequestType;
import com.laioffer.cmtyMgmtSys.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ServiceRequestController {
    @Autowired
    private ServiceRequestService serviceRequestService;

    //TODO: KEEP THIS.
    //add new service Request
    @PostMapping("/serviceRequest/add")
    public ServiceRequest addServiceRequest(@RequestBody ServiceRequest serviceRequest) {
        return serviceRequestService.addServiceRequest(serviceRequest);
    }

    //TODO:
    //Mark certain request as completed.
    @PutMapping("/updateServiceRequest/{id}")
    public ResponseEntity<ServiceRequest> updateServiceRequest(@PathVariable Long id, @RequestBody ServiceRequest serviceDetail) {
        return serviceRequestService.updateServiceRequest(id, serviceDetail);
    }

    //TODO: GET ALL SERVICEREQUESTS FOR CURRENT USER
    //get all serviceRequests
    @GetMapping("/allServiceRequests")
    public List<ServiceRequest> getAllServiceRequest() {
        return serviceRequestService.getAllServiceRequests();
    }

    //TODO: WE DO NOT NEED THIS FOR NOW.
    //get all serviceRequests by Apartment Id
    @GetMapping("/serviceRequests/{aptId}")
    public List<ServiceRequest> getServiceRequestByAptId(@PathVariable String aptId) {
        return serviceRequestService.findServiceRequestByAptId(Long.parseLong(aptId));
    }

    //TODO: GET ALL SERVICEREQUESTS FOR CURRENT USER
    //get all serviceRequests by TODO: USER ID NOT RESIDENT ID!
    @GetMapping("/serviceRequests/{residentId}")
    public List<ServiceRequest> getServiceRequestByResidentId(@PathVariable String residentId) {
        return serviceRequestService.findServiceRequestByResidentId(Long.parseLong(residentId));
    }


    public List<ServiceRequest> getServiceRequestByIdAndType(@PathVariable String id, @PathVariable String type){
        //TODO: GET ALL SERVICER REQUEST FOR CURRENT USER AND SPECIFIC STATUS(TODO, DONE)
        return null;
    }

    //TODO: GOOD KEEP THIS.
    //get serviceRequest by its own id
    @GetMapping("/serviceRequest/{id}")
    public ServiceRequest findServiceRequestById(@PathVariable String id) {
        return serviceRequestService.findServiceRequestById(Long.parseLong(id));
    }

    //TODO: DO NOT NEED THIS FOR NOW.
    //Status Group:
    //get all serviceRequest by Status
    @GetMapping("/serviceRequest/{status}")
    public List<ServiceRequest> getServiceRequestByStatus(@PathVariable String status) {
        return serviceRequestService.findServiceByStatus(status);
    }

    //TODO: WHY DO WE NEED THIS?
    //get certain service's status
    @GetMapping("/serviceRequest/getStatus/{id}")
    public ServiceRequestStatus getServiceRequestStatus(@PathVariable String id) {
        return serviceRequestService.getServiceRequestStatus(Long.parseLong(id));
    }

    //TODO: DELETE THIS
    //change service request status
    @PostMapping("/serviceRequest/changeStatus/{id}")
    public ServiceRequestStatus changeServiceRequestStatus(@PathVariable String id) {
        return serviceRequestService.changeServiceRequestStatus(Long.parseLong(id));
    }


    //TODO: DON'T NEED THIS FOR NOW
    //Type Group:
    //get all service request by type ??
    @GetMapping("/serviceRequest/{type}")
    public List<ServiceRequest> getServiceRequestByType(@PathVariable String type) {
        return serviceRequestService.findServiceByType(type);
    }

    //TODO: DON'T NEED THIS FOR NOW
    //get certain service's type
    @GetMapping("serviceRequest/getType/{id}")
    public ServiceRequestType getServiceRequestType(@PathVariable String id) {
        return serviceRequestService.getServiceRequestType(Long.valueOf(id));
    }

    //TODO: WHY DO WE NEED THIS?
    //TODO: DELETE EVERYHING BELOW.
    //change certain service's type
    @PostMapping("serviceRequest/postType/{id}")
    public ServiceRequestType changeServiceRequestType(@RequestParam("type") ServiceRequestType type, @PathVariable Long id) {
        return serviceRequestService.changeServiceRequestType(id, type);
    }


    //Disabled Group
    @GetMapping("/serviceRequest/getDisabled/{id}")
    public boolean getServiceRequestDisabled(@PathVariable String id) {
        return serviceRequestService.getServiceRequestDisable(Long.parseLong(id));
    }

    @PostMapping("/serviceRequest/postDisabled/{id}")
    public boolean changeServiceRequestDisable(@PathVariable Long id) {
        return serviceRequestService.changeServiceRequestDisabled(id);
    }

    //Description Group
    @GetMapping("/serviceRequset/{getDescription/{id}")
    public String getServiceRequestDescription(@PathVariable String id) {
        return serviceRequestService.getServiceRequestDescription(Long.parseLong(id));
    }

    @PostMapping("/serviceRequest/setDescription/{id}")
    public String setServiceRequestDescription(@RequestParam String description, @PathVariable String id) {
        return serviceRequestService.setServiceRequestDescription(Long.parseLong(id), description);
    }


    @GetMapping("/serviceRequest/getServiceRequestTime/{id}")
    public String getServiceRequestTime(@PathVariable String id) {
        return serviceRequestService.getServiceRequestTime(Long.parseLong(id));
    }

    @GetMapping("/serviceRequest/getServiceRequestCompletedTime/{id}")
    public String getServiceRequestedTime(@PathVariable String id) {
        return serviceRequestService.getServiceRequestCompletedTime(Long.parseLong(id));
    }

    @PostMapping("/serviceRequest/setServiceRequestTime/{id}")
    public String setServiceRequestTime(@PathVariable("id") String id, @RequestParam("time") Date time) {
        return serviceRequestService.setServiceRequestTime(Long.parseLong(id), time);
    }

    @PostMapping("/serviceRequest/setServiceRequestCompletedTime/{id}")
    public String setServiceRequestCompletedTime(@PathVariable("id") Long id, @RequestParam("time") Date time) {
        return serviceRequestService.setServiceRequestCompletedTime(id, time);
    }


}
