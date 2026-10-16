package com.workplace.support.service;

import com.workplace.support.dto.AuthObject;
import com.workplace.support.dto.OpportunityRequestDto;
import com.workplace.support.dto.OpportunityResponseDto;
import com.workplace.support.entity.Employee;
import com.workplace.support.entity.Opportunity;
import com.workplace.support.exceptions.EmployeeNotFoundException;
import com.workplace.support.repository.EmployeeRepository;
import com.workplace.support.repository.OpportunityRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {

    private final OpportunityRepository opportunityRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ManagerService(OpportunityRepository opportunityRepository,EmployeeRepository employeeRepository) {
        this.opportunityRepository = opportunityRepository;
        this.employeeRepository = employeeRepository;
    }

    public OpportunityResponseDto saveOpportunity(OpportunityRequestDto requestDto){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthObject authObject = null;
        if (authentication==null) {
            throw new RuntimeException("Authentication Not Found");
        }
            authObject = (AuthObject) authentication.getPrincipal();

            Employee emp = employeeRepository.findById(authObject.getEmpId())
                    .orElseThrow(()-> new EmployeeNotFoundException("Manager does not exist."));

            Opportunity op = new Opportunity();
            op.setTitle(requestDto.getTitle());
            op.setDescription(requestDto.getDescription());
            op.setSkills(requestDto.getSkills());
            op.setApplyBefore(requestDto.getApplyBefore());
            op.setCreatedBy(emp);

            Opportunity savedOpportunity = opportunityRepository.save(op);

            OpportunityResponseDto responseDto = new OpportunityResponseDto();
            responseDto.setOpId(savedOpportunity.getOpId());
            responseDto.setTitle(savedOpportunity.getTitle());
            responseDto.setDescription(savedOpportunity.getDescription());
            responseDto.setSkills(savedOpportunity.getSkills());
            responseDto.setApplyBefore(savedOpportunity.getApplyBefore());

            return responseDto;
    }

    public List<OpportunityResponseDto> getMyAddedOpportunities() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new RuntimeException("Authentication Not Found.Login Again.");
        }

        AuthObject authObject = (AuthObject) authentication.getPrincipal();

        if(authObject==null){
            throw new RuntimeException("Authentication Not Found.Login Again.");
        }

        Employee emp = employeeRepository.findById(authObject.getEmpId())
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Manager does not exist"));

        List<Opportunity> opportunities = opportunityRepository.findByCreatedBy(emp);
        List<OpportunityResponseDto> dtoList = new ArrayList<>();

        for(Opportunity op : opportunities){
            OpportunityResponseDto responseDto = new OpportunityResponseDto();
            responseDto.setOpId(op.getOpId());
            responseDto.setTitle(op.getTitle());
            responseDto.setDescription(op.getDescription());
            responseDto.setSkills(op.getSkills());
            responseDto.setApplyBefore(op.getApplyBefore());
            dtoList.add(responseDto);
        }
        return dtoList;
    }

    public OpportunityResponseDto updateOpportunity(
            Long opId,
            OpportunityRequestDto requestDto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            throw new RuntimeException("Authentication Not Found.Login Again.");
        }

        AuthObject authObject =
                (AuthObject) authentication.getPrincipal();

        if(authObject == null){
            throw new RuntimeException("Authentication Not Found.Login Again.");
        }

        Employee emp = employeeRepository.findById(authObject.getEmpId())
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Manager does not exist"));

        Opportunity op = opportunityRepository.findById(opId)
                .orElseThrow(() ->
                        new RuntimeException("Opportunity does not exist"));

        if(!op.getCreatedBy().getEmpId().equals(emp.getEmpId())){
            throw new RuntimeException(
                    "You are not authorized to update this opportunity");
        }

        op.setTitle(requestDto.getTitle());
        op.setDescription(requestDto.getDescription());
        op.setSkills(requestDto.getSkills());
        op.setApplyBefore(requestDto.getApplyBefore());

        Opportunity updatedOpportunity =
                opportunityRepository.save(op);

        OpportunityResponseDto responseDto =
                new OpportunityResponseDto();

        responseDto.setOpId(updatedOpportunity.getOpId());
        responseDto.setTitle(updatedOpportunity.getTitle());
        responseDto.setDescription(updatedOpportunity.getDescription());
        responseDto.setSkills(updatedOpportunity.getSkills());
        responseDto.setApplyBefore(updatedOpportunity.getApplyBefore());

        return responseDto;
    }

    public void deleteOpportunity(Long opId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            throw new RuntimeException("Authentication Failed.");
        }

        AuthObject authObject =
                (AuthObject) authentication.getPrincipal();

        if(authObject == null){
            throw new RuntimeException("Authentication Failed.");
        }

        Employee emp = employeeRepository.findById(authObject.getEmpId())
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Manager does not exist"));

        Opportunity op = opportunityRepository.findById(opId)
                .orElseThrow(() ->
                        new RuntimeException("Opportunity does not exist"));

        if(!op.getCreatedBy().getEmpId().equals(emp.getEmpId())){
            throw new RuntimeException(
                    "You are not authorized to delete this opportunity");
        }

        opportunityRepository.delete(op);
    }


}
