package org.one.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserModel {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String dateOfBirth;
    private int shoeSize;
}
