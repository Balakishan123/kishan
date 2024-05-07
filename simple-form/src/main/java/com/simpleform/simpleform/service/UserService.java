package com.simpleform.simpleform.service;

import com.simpleform.simpleform.model.UserModel;
import com.simpleform.simpleform.repository.UserJpaRepository;
import com.simpleform.simpleform.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService implements UsersRepository{

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Override
    public UserModel addUser(UserModel user) {
        return null;
    }

    @Override
    public UserModel updatePassword() {
        return null;
    }
}

