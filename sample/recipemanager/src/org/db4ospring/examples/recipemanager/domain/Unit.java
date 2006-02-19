package org.db4ospring.examples.recipemanager.domain;

public class Unit {
	public String name;
	
	public Unit(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
