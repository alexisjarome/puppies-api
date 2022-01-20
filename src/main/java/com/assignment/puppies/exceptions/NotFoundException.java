package com.assignment.puppies.exceptions;

public class NotFoundException extends RuntimeException {

	public NotFoundException(Long id) {
		super("Could not found entity " + id);
	}
	
}
