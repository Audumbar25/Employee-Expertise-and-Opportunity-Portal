package com.workplace.support.controller;

import com.workplace.support.dto.OpportunityResponseDto;
import com.workplace.support.dto.ProfileRequestDto;
import com.workplace.support.dto.ProfileResponseDto;
import com.workplace.support.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emp")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @PutMapping("/profile")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    public ResponseEntity<ProfileResponseDto> saveProfile(@Valid @RequestBody ProfileRequestDto req){
        ProfileResponseDto responseDto = employeeService.saveProfile(req);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/profile/{empId}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long empId){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getProfile(empId));
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getMyProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getProfile());
    }

    @GetMapping("/opportunity/{opId}")
    public ResponseEntity<OpportunityResponseDto> getOpportunityById(
            @PathVariable Long opId){

        OpportunityResponseDto responseDto =
                employeeService.getOpportunityById(opId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

}
