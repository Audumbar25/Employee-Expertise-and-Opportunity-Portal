package com.workplace.support.security;

import com.workplace.support.dto.AuthObject;
import com.workplace.support.entity.Employee;
import com.workplace.support.repository.EmployeeRepository;
import com.workplace.support.service.JwtUtilService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtilService jwtUtilService;
    private EmployeeRepository employeeRepository;

    @Autowired
    public JwtAuthenticationFilter(JwtUtilService jwtUtilService
    ,EmployeeRepository employeeRepository){
        this.jwtUtilService = jwtUtilService;
        this.employeeRepository = employeeRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        try{
            String token = authHeader.substring(7);

            String empId = jwtUtilService.extractEmployeeId(token);

            Employee emp = employeeRepository.findById(Long.valueOf(empId)).orElse(null);

            if(emp!=null){

                AuthObject auth = new AuthObject(emp.getEmpId(), emp.getEmpName(),emp.getEmpEmail(),emp.getRole());

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(auth,null,
                        List.of(new SimpleGrantedAuthority("ROLE_"+emp.getRole().name())));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch(JwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
