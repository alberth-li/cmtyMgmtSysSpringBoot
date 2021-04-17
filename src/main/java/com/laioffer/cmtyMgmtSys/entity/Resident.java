package com.laioffer.cmtyMgmtSys.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "resident")
public class Resident implements Serializable{
    private static final long serialVersionUID = 2652327633296064143L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String preferredName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)//unique foreign key make sure it is one-to-one
    private User user;

    @ManyToOne()
    private Apartment apartment;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceRequest> ServiceRequests;
}
