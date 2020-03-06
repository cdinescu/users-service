package com.vitanum.userservice.shared;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -953297098295050686L;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
}
