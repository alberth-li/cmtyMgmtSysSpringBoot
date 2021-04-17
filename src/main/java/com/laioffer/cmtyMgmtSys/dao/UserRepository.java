package com.laioffer.cmtyMgmtSys.dao;

import com.laioffer.cmtyMgmtSys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmailId(String emailId);
}
