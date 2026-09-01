package com.example.habittracker.model;

public interface IAuthenticationService {
    void register(String username, String password);
    User login(String username, String password);
}