package com.icrn.substitutes.model;

public interface UserInterface {
    long getId();

    void setId(long id);

    void setName(String name);

    String getName();

    void setContactNumber(String contactNumber);

    String getContactNumber();

    void setAddress(String address);

    String getAddress();
}
