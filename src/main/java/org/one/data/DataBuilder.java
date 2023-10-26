package org.one.data;

import com.github.javafaker.Faker;
import org.ecco.utils.DateUtil;

import java.util.Date;

public class DataBuilder {
    private static final Faker FAKER = Faker.instance ();
    private static final Date from = DateUtil.getDateInSpecificFormat ("01/01/1981", "dd/MM/yyyy");
    private static final Date to = DateUtil.getDateInSpecificFormat ("31/12/2010", "dd/MM/yyyy");

    public static UserModel getUserData() {
        return UserModel.builder ()
                .firstName (FAKER.name ().firstName ())
                .lastName (FAKER.name ().lastName ())
                .mobileNumber (String.valueOf (FAKER.number ()
                        .numberBetween (9990000000L, 9999999999L)))
                .dateOfBirth (FAKER.date ()
                        .between (from, to)
                        .toString ())
                .shoeSize (FAKER.random ().nextInt (37,50))
                .build ();
    }
}
