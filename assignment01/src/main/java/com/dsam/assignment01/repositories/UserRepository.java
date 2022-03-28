package com.dsam.assignment01.repositories;

import com.dsam.assignment01.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface  UserRepository extends JpaRepository<User, Long> {

	User findUserByUsername(String username) throws UsernameNotFoundException;
}
