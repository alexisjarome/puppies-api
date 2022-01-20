package com.assignment.puppies.rest.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.puppies.beans.OkBean;
import com.assignment.puppies.domains.Post;
import com.assignment.puppies.domains.User;
import com.assignment.puppies.exceptions.NotFoundException;
import com.assignment.puppies.repositories.PostRepository;
import com.assignment.puppies.repositories.UserRepository;

@RestController
public class PostRestController {

	@Autowired
	PostRepository postRepositories;
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/post")
	public Post create(@RequestBody Post post, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		post.setUser(user);
		post.setDate(new Date());
		return postRepositories.save(post);
	}
	
	@GetMapping("/users/{userId}/posts")
	public List<Post> getUserPosts(@PathVariable Long userId) {
		User user = userRepository.getById(userId);
		return user.getPosts();
	}
	
	@GetMapping("/posts/{postId}")
	public Post get(@PathVariable Long postId) {		
		return postRepositories.findById(postId).orElseThrow(() -> new NotFoundException(postId));
	}	
	
	@GetMapping("/likedPosts")
	public List<Post> likedPosts(Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		return user.getLikedPosts();
	}
	
	@GetMapping("/users/{userId}/userPosts")
	public List<Post> userPosts(@PathVariable Long userId) {
		return postRepositories.findAllByUser_Id(userId);
	}
	
	@GetMapping("/feed")
	public List<Post> feeds(Principal principal) {
		User u = userRepository.findByEmail(principal.getName());

		List<Post> feeds = postRepositories.findAllSorted();
		feeds.forEach(f -> {
			if (u.getLikedPosts() != null && u.getLikedPosts().contains(f)) {
				f.setLike(true);
			}
		});

		return feeds;
	}
	
	@PostMapping("/post/like/{postId}")
	public ResponseEntity like(@PathVariable Long postId, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		Post postToLike = postRepositories.getById(postId);

		user.addLikedPost(postToLike);

		userRepository.save(user);
		
		return ResponseEntity.ok(new OkBean("success"));		
	}
	
}
