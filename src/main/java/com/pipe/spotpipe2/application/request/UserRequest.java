package com.pipe.spotpipe2.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pipe.spotpipe2.domain.models.users.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRequest {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    public UserModel toModel() {
        return new UserModel(username, email, password, birthDate);
    }
}
