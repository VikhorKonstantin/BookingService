package by.vikhor.bookingservice.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LogInDto {
    @Email
    private String name;
    @NotBlank
    @Size(min = 8)
    private String password;
}
