package com.laioffer.cmtyMgmtSys.service;

import com.laioffer.cmtyMgmtSys.dao.ServiceRequestRepository;
import com.laioffer.cmtyMgmtSys.entity.ServiceRequest;
import com.laioffer.cmtyMgmtSys.entity.ServiceRequestStatus;
import com.laioffer.cmtyMgmtSys.entity.ServiceRequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ServiceRequestService {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public ServiceRequest addServiceRequest(ServiceRequest serviceRequest) {
        return this.serviceRequestRepository.save(serviceRequest);
    }

    public ServiceRequest findServiceRequestById(Long id) {
        return serviceRequestRepository.findServiceRequestById(id);
    }

    public ResponseEntity<ServiceRequest> updateServiceRequest(Long id, ServiceRequest serviceDetail) {
        //get request object
        ServiceRequest service = serviceRequestRepository.findServiceRequestById(id);
        //update info
        service.setDescription(serviceDetail.getDescription());
        changeServiceRequestStatus(id);
        service.setDisabled(serviceDetail.isDisabled());
        ServiceRequest updated = serviceRequestRepository.save(service);
        return ResponseEntity.ok(updated);
    }

    //Get All Services
    public List<ServiceRequest> getAllServiceRequests() {
        return serviceRequestRepository.findAll();
    }

    public List<ServiceRequest> findServiceRequestByAptId(Long apt) {
        List<ServiceRequest> allService = serviceRequestRepository.findAll();
        ;
        List<ServiceRequest> aptService = null;
        for (ServiceRequest service : allService) {
            if (service.getApt().equals(apt)) {
                aptService.add(service);
            }
        }
        return aptService;
    }

    public List<ServiceRequest> findServiceRequestByResidentId(Long resident) {
        List<ServiceRequest> allService = serviceRequestRepository.findAll();
        List<ServiceRequest> residentService = null;
        for (ServiceRequest service : allService) {
            if (service.getResident().equals(resident)) {
                residentService.add(service);
            }
        }
        return residentService;
    }


    //this service should check for services by its completed time. if longer than 30 days. it should delete it;
    //public void deleteServiceRequest(Long id) {};

    //Status Group:
    public List<ServiceRequest> findServiceByStatus(String status) {
        List<ServiceRequest> allService = serviceRequestRepository.findAll();
        List<ServiceRequest> statusService = null;
        for (ServiceRequest service : allService) {
            if (service.getStatus().name().equals(status)) {
                statusService.add(service);
            }
        }
        return statusService;
    }

    public ServiceRequestStatus getServiceRequestStatus(Long id) {
        return serviceRequestRepository.findServiceRequestById(id).getStatus();
    }

    //change from done from todo vice versa
    public ServiceRequestStatus changeServiceRequestStatus(Long id) {
        ServiceRequest service = findServiceRequestById(id);
        int cur = service.getStatus().ordinal();
        service.setStatus(cur == 0 ? ServiceRequestStatus.TODO : ServiceRequestStatus.DONE);
        Date currentDate = new Date(System.currentTimeMillis());
        if (service.getStatus().name().equals(ServiceRequestStatus.DONE)) {
            service.setTimeCompleted(currentDate);
        } else {
            service.setTimeCompleted(null);
            setServiceRequestTime(id, currentDate);
        }
        return service.getStatus();
    }

    //Type Group:
    public List<ServiceRequest> findServiceByType(String type) {
        List<ServiceRequest> allService = serviceRequestRepository.findAll();
        List<ServiceRequest> typeService = null;
        for (ServiceRequest service : allService) {
            if (service.getType().name().equals(type)) {
                typeService.add(service);
            }
        }
        return typeService;
    }


    public ServiceRequestType getServiceRequestType(Long id) {
        return serviceRequestRepository.findServiceRequestById(id).getType();
    }

    public ServiceRequestType changeServiceRequestType(Long id, ServiceRequestType type) {
        ServiceRequest service = serviceRequestRepository.findServiceRequestById(id);
        service.setType(type);
        return service.getType();
    }


    //Disabled Group:
    public boolean getServiceRequestDisable(Long id) {
        return serviceRequestRepository.findServiceRequestById(id).isDisabled();
    }

    public boolean changeServiceRequestDisabled(Long id) {
        ServiceRequest service = serviceRequestRepository.findServiceRequestById(id);
        boolean cur = service.isDisabled();
        service.setDisabled(cur == true ? false : true);
        return service.isDisabled();
    }


    //description group
    public String getServiceRequestDescription(Long id) {
        return serviceRequestRepository.findServiceRequestById(id).getDescription();
    }

    public String setServiceRequestDescription(Long id, String description) {
        ServiceRequest cur = serviceRequestRepository.findServiceRequestById(id);
        cur.setDescription(description);
        return cur.getDescription();
    }

    //Time Group
    public String setServiceRequestTime(Long id, Date date) {
        if (serviceRequestRepository.existsById(id)) {
            ServiceRequest cur = serviceRequestRepository.findServiceRequestById(id);
            cur.setTimeRequested(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return dateFormat.format(cur.getTimeRequested());
        } else {
            return "No record";
        }
    }

    public String getServiceRequestTime(Long id) {
        if (serviceRequestRepository.existsById(id)) {
            ServiceRequest cur = serviceRequestRepository.findServiceRequestById(id);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return dateFormat.format(cur.getTimeRequested());
        } else {
            return "No record";
        }

    }

    //TODO: DELETE THIS
    public String setServiceRequestCompletedTime(Long id, Date date) {
        if (serviceRequestRepository.existsById(id)) {
            ServiceRequest cur = serviceRequestRepository.findServiceRequestById(id);
            cur.setTimeCompleted(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return dateFormat.format(cur.getTimeCompleted());
        } else {
            return "No record";
        }
    }

    public String getServiceRequestCompletedTime(Long id) {
        if (serviceRequestRepository.existsById(id)) {
            ServiceRequest cur = serviceRequestRepository.findServiceRequestById(id);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return dateFormat.format(cur.getTimeCompleted());
        } else {
            return "No record";
        }
    }


}
