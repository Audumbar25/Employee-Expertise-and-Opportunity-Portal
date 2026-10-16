package com.workplace.support.controller;

import com.workplace.support.dto.LoginRequestDto;
import com.workplace.support.dto.LoginRespDto;
import com.workplace.support.dto.RegisterReqDto;
import com.workplace.support.dto.RegisterRespDto;
import com.workplace.support.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authservice;

    @Autowired
    AuthController(AuthService authservice){
        this.authservice=authservice;
    }
    @PostMapping("register")
    public ResponseEntity<RegisterRespDto> register(@Valid @RequestBody RegisterReqDto emp){
       RegisterRespDto empResp = authservice.registerEmployee(emp);
       return ResponseEntity.status(HttpStatus.OK).body(empResp);
    }

    @PostMapping("login")
    public ResponseEntity<LoginRespDto> login(@Valid @RequestBody LoginRequestDto reqDto){
        LoginRespDto dto = authservice.login(reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
