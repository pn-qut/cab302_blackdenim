package com.example.habittracker.model;

import java.util.List;

public interface IAuthenticationService {
    void register(String username, String password, int postcode);
    User login(String username, String password);
    void changePassword(String username, String currentPassword, String newPassword);
    List<String> getSuburbsByPostcode(int postcode);
}
