package com.icrn.substitutes.model;

import lombok.Data;

@Data
public class User implements UserInterface {
    private long id;
    private String name;
    private String contactNumber;
    private String address;

}
