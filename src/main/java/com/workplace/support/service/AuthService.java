package com.workplace.support.service;

import com.workplace.support.dto.*;
import com.workplace.support.exceptions.EmailIsPersonalException;
import com.workplace.support.exceptions.EmployeeAlreadyExistException;
import com.workplace.support.entity.Employee;
import com.workplace.support.exceptions.InvalidCredentialsException;
import com.workplace.support.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Value("${app.allowed.email.domain}")
    private String allowedEmailDomain;
    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtilService jwtUtilService;

    @Autowired
    AuthService(EmployeeRepository employeeRepository,PasswordEncoder passwordEncoder,JwtUtilService jwtUtilService){
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilService =  jwtUtilService;
    }

    public RegisterRespDto registerEmployee(RegisterReqDto emp){

        if(!emp.getEmpEmail().endsWith(allowedEmailDomain)){
            log.warn("Employee was trying to register with his personal mail : {}",emp.getEmpEmail());
            throw new EmailIsPersonalException("Please register yourself with the mail provided by company.");
        }
        boolean flag = employeeRepository.existsByEmpEmail(emp.getEmpEmail());
        if(flag){
            log.warn("Employee with email {} already exist in system",emp.getEmpEmail());
            throw new EmployeeAlreadyExistException(
                "Employee with email : " + emp.getEmpEmail() + " Already Exist."
            );
        }


        Employee empToSave = new Employee();
        empToSave.setEmpName(emp.getEmpName());
        empToSave.setEmpEmail(emp.getEmpEmail());
        empToSave.setEmpPassword(passwordEncoder.encode(emp.getEmpPassword()));
        empToSave.setRole(Role.EMPLOYEE);

        Employee savedEmployee = employeeRepository.save(empToSave);
        log.info("Employee saved successfully : {}",savedEmployee);

        RegisterRespDto dto = new RegisterRespDto();
        dto.setEmpName(savedEmployee.getEmpName());
        dto.setEmpEmail(savedEmployee.getEmpEmail());

        return dto;
    }

    public LoginRespDto login(LoginRequestDto reqDto) {
        Employee emp = employeeRepository.findByEmpEmail(reqDto.getEmpEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Employee email or password is wrong."));

        if(!passwordEncoder.matches(reqDto.getEmpPassword(),emp.getEmpPassword())){
            throw new InvalidCredentialsException("Employee email or password is wrong.");
        }

        String token = jwtUtilService.generateToken(emp);
        LoginRespDto dto = new LoginRespDto();
        dto.setEmpEmail(emp.getEmpEmail());
        dto.setEmpRole(emp.getRole());
        dto.setToken(token);
        return dto;
    }
}
