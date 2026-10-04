package com.example.habittracker.model;

import java.util.List;

public interface IAuthenticationService {
    void register(String username, String password, int postcode);
    User login(String username, String password);
    List<String> getSuburbsByPostcode(int postcode);
}