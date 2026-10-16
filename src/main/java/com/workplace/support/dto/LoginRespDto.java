package com.workplace.support.dto;

import lombok.Data;

@Data
public class LoginRespDto {

    private String empEmail;

    private Role empRole;

    private String token;

}
