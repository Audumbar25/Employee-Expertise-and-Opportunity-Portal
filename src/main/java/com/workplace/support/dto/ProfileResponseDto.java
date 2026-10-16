package com.workplace.support.dto;

import lombok.Data;

@Data
public class ProfileResponseDto {

        private Long empId;

        private String empName;

        private String empEmail;

        private String empContactNumber;
        private String designation;

        private String skills;

        private String expertiseModules;

        private String profileImageUrl;

        private ProfileImageStatus profileImageStatus;

}
