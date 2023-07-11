package com.pipe.spotpipe2.controllers.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pipe.spotpipe2.models.UserModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    public UserResponse(UserModel userModel) {
        this.id = userModel.getId();
        this.name = userModel.getName();
        this.email = userModel.getEmail();
        this.birthDate = userModel.getBirthDate();
    }
}
