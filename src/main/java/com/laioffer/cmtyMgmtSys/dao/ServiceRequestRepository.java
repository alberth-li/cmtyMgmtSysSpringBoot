package com.laioffer.cmtyMgmtSys.dao;


import com.laioffer.cmtyMgmtSys.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    ServiceRequest findServiceRequestById(Long id);

    ServiceRequest findServiceRequestByCreatedByAndType(Long id, String type);

}

