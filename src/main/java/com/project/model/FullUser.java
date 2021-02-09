package com.project.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullUser implements Serializable {

    @Pattern(regexp = "^[0-9]*$", message = "{FullUser.id}")
    private String id;

    @NotBlank(message = "{FullUser.username.notBlank}")
    @NotNull(message = "{FullUser.username.notNull}")
    @NotEmpty(message = "{FullUser.username.notEmpty}")
    @Length(min = 3, max = 13,message = "{FullUser.username.size}")
    private String username;


    @NotBlank(message = "{FullUser.password.notBlank}")
    @NotNull(message = "{FullUser.password.notNull}")
    @NotEmpty(message = "{FullUser.password.notEmpty}")
    @Length(min = 3, max = 13,message = "{FullUser.password.size}")
    private String password;


    @NotBlank(message = "{FullUser.role.notBlank}")
    @NotNull(message = "{FullUser.role.notNull}")
    @NotEmpty(message = "{FullUser.role.notEmpty}")
    @Pattern(regexp = "^[0-9]*$", message = "{FullUser.role.pattern}")
    private String role;


}