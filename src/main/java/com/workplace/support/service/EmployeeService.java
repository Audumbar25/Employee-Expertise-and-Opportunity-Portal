package com.workplace.support.service;

import com.workplace.support.dto.*;
import com.workplace.support.entity.Employee;
import com.workplace.support.entity.Opportunity;
import com.workplace.support.exceptions.EmployeeNotFoundException;
import com.workplace.support.exceptions.OpportunityDoesNotExist;
import com.workplace.support.repository.EmployeeRepository;
import com.workplace.support.repository.OpportunityRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final OpportunityRepository opportunityRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           OpportunityRepository opportunityRepository){
        this.employeeRepository = employeeRepository;
        this.opportunityRepository = opportunityRepository;
    }

    public ProfileResponseDto getProfile(Long empId){
        Employee emp = employeeRepository.findById(empId).orElseThrow(()->
                new EmployeeNotFoundException("Employee Doesn't Exist In The System."));
        return convertToProfileResponseDto(emp);
    }

    public ProfileResponseDto getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            log.info("Authentication Failed while getting my profile.");
            throw new RuntimeException("Authentication Failed");
        }
        AuthObject authObject = (AuthObject) authentication.getPrincipal();
        if(authObject == null){
            log.info("Authentication Failed while getting my profile.");
            throw new RuntimeException("Authentication Failed.");
        }
        Employee emp = employeeRepository.findById(authObject.getEmpId()).orElseThrow(()->
                new EmployeeNotFoundException("Employee Doesn't Exist In The System."));
        return convertToProfileResponseDto(emp);
    }

    public OpportunityResponseDto getOpportunityById(Long opId) {

        Opportunity op = opportunityRepository.findById(opId)
                .orElseThrow(() ->
                        new OpportunityDoesNotExist("Opportunity does not exist"));

        OpportunityResponseDto responseDto =
                new OpportunityResponseDto();

        responseDto.setOpId(op.getOpId());
        responseDto.setTitle(op.getTitle());
        responseDto.setDescription(op.getDescription());
        responseDto.setSkills(op.getSkills());
        responseDto.setApplyBefore(op.getApplyBefore());

        return responseDto;
    }

    public ProfileResponseDto saveProfile(@Valid ProfileRequestDto req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            log.info("Authentication Failed");
            throw new RuntimeException("Authentication Failed");
        }
        AuthObject authObject = (AuthObject) authentication.getPrincipal();
        if(authObject == null){
            log.info("Authentication Failed");
            throw new RuntimeException("Authentication Failed.");
        }

        Employee emp = employeeRepository.findById(authObject.getEmpId()).orElse(null);
        if(emp == null){
            log.info("employee not found with employee id : {}",authObject.getEmpId());
            throw new RuntimeException("employee not found with employee id : {}" + authObject.getEmpId());
        }

        emp.setDesignation(req.getDesignation());
        emp.setEmpContactNumber(req.getContactNumber());
        emp.setSkills(req.getSkills());
        emp.setExpertiseModules(req.getExpertiseModules());

        Employee savedEmployee = employeeRepository.save(emp);

        return convertToProfileResponseDto(savedEmployee);
    }

    private ProfileResponseDto convertToProfileResponseDto(Employee savedEmployee) {
        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setEmpId(savedEmployee.getEmpId());
        responseDto.setEmpName(savedEmployee.getEmpName());
        responseDto.setEmpEmail(savedEmployee.getEmpEmail());
        responseDto.setDesignation(savedEmployee.getDesignation());
        responseDto.setEmpContactNumber(savedEmployee.getEmpContactNumber());
        responseDto.setSkills(savedEmployee.getSkills());
        responseDto.setExpertiseModules(savedEmployee.getExpertiseModules());
        responseDto.setProfileImageUrl(savedEmployee.getProfileImageUrl());
        ProfileImageStatus imageStatus = savedEmployee.getProfileImageStatus();

        responseDto.setProfileImageStatus(
                imageStatus == null ? ProfileImageStatus.NOT_UPLOADED : imageStatus);
        return responseDto;
    }
}
