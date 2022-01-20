package com.assignment.puppies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.puppies.domains.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	

	  public User findByEmail(String email);

}
