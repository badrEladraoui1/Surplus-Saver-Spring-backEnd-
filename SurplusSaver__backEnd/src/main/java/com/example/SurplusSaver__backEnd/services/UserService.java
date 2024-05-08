package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public List<User> getUsersByRole(String token, String role);
    public void deleteUser(String token, Long id);
    public User getUserById(String token);
    public User saveUser(String token, User user);
}
