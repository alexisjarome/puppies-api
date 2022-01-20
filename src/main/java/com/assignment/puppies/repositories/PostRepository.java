package com.assignment.puppies.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.puppies.domains.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>  {

	  public List<Post> findAllByUser_Id(Long userId);
	  
	  @Query("Select p from Post p order by p.date desc")
	  public List<Post> findAllSorted();

}
