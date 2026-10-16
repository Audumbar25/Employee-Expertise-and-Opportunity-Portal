package com.workplace.support.controller;

import com.workplace.support.dto.OpportunityRequestDto;
import com.workplace.support.dto.OpportunityResponseDto;
import com.workplace.support.service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private final ManagerService managerService;

    ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }
    @PostMapping("/opportunity")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<OpportunityResponseDto> saveOpportunity(@Valid @RequestBody OpportunityRequestDto requestDto){
        OpportunityResponseDto responseDto = managerService.saveOpportunity(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/opportunity")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<OpportunityResponseDto>> viewMyAddedOpportunities(){
        return ResponseEntity.status(HttpStatus.OK).body(managerService.getMyAddedOpportunities());
    }

    @PutMapping("/opportunity/{opId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<OpportunityResponseDto> updateOpportunity(
            @PathVariable Long opId,
            @Valid @RequestBody OpportunityRequestDto requestDto){

        OpportunityResponseDto responseDto =
                managerService.updateOpportunity(opId, requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @DeleteMapping("/opportunity/{opId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<String> deleteOpportunity(
            @PathVariable Long opId){

        managerService.deleteOpportunity(opId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Opportunity deleted successfully");
    }

}
