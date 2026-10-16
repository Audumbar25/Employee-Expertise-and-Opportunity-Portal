package com.workplace.support.entity;

import com.workplace.support.dto.AuthObject;
import com.workplace.support.dto.ProfileImageStatus;
import com.workplace.support.dto.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    private String empName;

    @Column(unique = true,nullable = false)
    private String empEmail;

    @Column(nullable = false)
    private String empPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 10)
    private String empContactNumber;

    @Column(length = 50)
    private String designation;

    @Column(length = 500)
    private String skills;

    @Column(length = 500)
    private String expertiseModules;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private ProfileImageStatus profileImageStatus;
}
