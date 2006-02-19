package org.db4ospring.examples.recipemanager.domain;

public class Category {
	//entree, salad, desert, soup, ...
	private String name;
	
	//avoid no-name categories
	public Category(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
