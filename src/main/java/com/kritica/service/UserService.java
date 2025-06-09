package com.kritica.service;

import com.kritica.model.Users;
import com.kritica.payload.UsersDTO;

import java.util.List;

public interface UserService {
    public List<Users> getAllusers();

    public UsersDTO save(UsersDTO usersDTO);
}
