package com.simpleform.simpleform.repository;

import com.simpleform.simpleform.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserJpaRepository extends JpaRepository<UserModel,Long>{
    boolean existsByEmailId(String email);
    UserModel findByEmailId(String emailId);
    UserModel findByResetToken(String token);


}





