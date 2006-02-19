package org.db4ospring.examples.recipemanager.persistence.db4o;

import java.util.List;

import org.db4ospring.examples.recipemanager.domain.Recipe;
import org.db4ospring.examples.recipemanager.domain.Unit;
import org.db4ospring.examples.recipemanager.persistence.RecipeManager;
import org.db4ospring.support.Db4oDaoSupport;

import com.db4o.query.Query;

public class Db4oRecipeManager extends Db4oDaoSupport implements RecipeManager {
	public List getAllUnits() {
		return getDb4oTemplate().get(new Unit(null));
	}

	public void saveUnit(Unit unit) {
		getDb4oTemplate().set(unit);
	}
	
	public List getAllRecipes() {
		Query allRecipes = getDb4oTemplate().query();
		allRecipes.constrain(Recipe.class);
		return allRecipes.execute();
	}

	public void saveRecipe(Recipe r) {
		getDb4oTemplate().set(r);
	}
	
	public long getId(Object persistable) {
		return getDb4oTemplate().getID(persistable);
	}
	
	public Recipe getById(long id) {
		return (Recipe) getDb4oTemplate().getByID(id);
	}
}
