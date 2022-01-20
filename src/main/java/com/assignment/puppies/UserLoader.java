package com.assignment.puppies;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.assignment.puppies.domains.Post;
import com.assignment.puppies.domains.User;
import com.assignment.puppies.repositories.PostRepository;
import com.assignment.puppies.repositories.UserRepository;

@Component
public class UserLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		User mark = userRepository.save(new User("Mark", "Klein", "foo123", "mark.klein@puppies.com"));
		User bob = userRepository.save(new User("Bob", "Laude", "foo111", "boblaude@puppies.com"));
		User mika = userRepository.save(new User("Mika", "Pey", "foo555", "mikepey123@puppies.com"));
		User jane = userRepository.save(new User("Jane", "Raz", "foo999", "jane.raz@puppies.com"));

		Post post = new Post();
		post.setText("Mark's Puppy");
		post.setImageName("mark-puppy.jpg");
		post.setDate(new Date());
		post.setUser(mark);
		postRepository.save(post);

		Post post2 = new Post();
		post2.setText("Bob's Puppy");
		post2.setImageName("bob-puppy.jpg");
		post2.setDate(new Date());
		post2.setUser(bob);

		Post post3 = new Post();
		post3.setText("Mika's puppy");
		post3.setImageName("mika-puppy.jpg");
		post3.setDate(new Date());
		post3.setUser(mika);

		Post post4 = new Post();
		post4.setText("Jane's puppy");
		post4.setImageName("jane-puppy.jpg");
		post4.setDate(new Date());
		post4.setUser(jane);

		postRepository.saveAll(Arrays.asList(post, post2, post3, post4));
	}

}
