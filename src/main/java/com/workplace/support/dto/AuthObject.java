package com.workplace.support.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthObject {

    private Long empId;

    private String empName;

    private String empEmail;

    private Role empRole;

}
