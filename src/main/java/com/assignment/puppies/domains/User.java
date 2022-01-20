package com.assignment.puppies.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "password"}, allowSetters = true)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String firstName;
	private String lastName;
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "like_post", 
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "post_id", 
      referencedColumnName = "id"))
	private List<Post> likedPosts;
	
	@Column(unique=true)
	private String email;
	
	public User() {
		
	}
	
	public User(String firstName, String lastName, String password, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return this.firstName + " " + this.lastName;
	}
	
	@JsonIgnore
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@JsonIgnore
	public List<Post> getLikedPosts() {
		if (likedPosts != null) {
			likedPosts.forEach(l -> l.setLike(true));
		}
		return likedPosts;
	}

	public void setLikedPosts(List<Post> likedPosts) {
		this.likedPosts = likedPosts;
	}

	public void setName(String name) {
		String[] parts = name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
	}
	
	public void addLikedPost(Post post) {
		if (likedPosts == null) {
			likedPosts = new ArrayList<>();
		}
		
		likedPosts.add(post);
	}
	
	  @Override
	  public boolean equals(Object o) {

	    if (this == o)
	      return true;
	    if (!(o instanceof User))
	      return false;
	    User user = (User) o;
	    return Objects.equals(this.id, user.id) && Objects.equals(this.firstName, user.firstName)
	        && Objects.equals(this.lastName, user.lastName) && Objects.equals(this.email, user.email);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(this.id, this.firstName, this.lastName, this.email);
	  }

	  @Override
	  public String toString() {
	    return "user{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
	        + '\'' + ", email='" + this.email + '\'' + '}';
	  }
	
}
