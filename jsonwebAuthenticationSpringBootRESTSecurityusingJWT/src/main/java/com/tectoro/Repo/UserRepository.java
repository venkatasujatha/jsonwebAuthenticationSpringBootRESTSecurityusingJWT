package com.tectoro.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tectoro.Model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByName(String username);
}
