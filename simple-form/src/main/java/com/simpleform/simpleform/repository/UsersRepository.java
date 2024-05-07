package com.simpleform.simpleform.repository;

import com.simpleform.simpleform.model.UserModel;

public interface UsersRepository {
   UserModel addUser(UserModel user);
   UserModel updatePassword();

}

