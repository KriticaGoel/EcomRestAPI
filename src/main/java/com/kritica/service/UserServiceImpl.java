package com.kritica.service;

import com.kritica.model.Users;
import com.kritica.payload.UsersDTO;
import com.kritica.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Users> getAllusers() {
        return usersRepository.findAll();
        //  return modelMapper.map(users,UsersResponse.class);
    }

    @Override
    public UsersDTO save(UsersDTO usersDTO) {

        Users user = usersRepository.save(modelMapper.map(usersDTO, Users.class));
        return modelMapper.map(user, UsersDTO.class);
    }
}
