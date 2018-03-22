package seedu.recipe.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.recipe.model.recipe.Calories;
import seedu.recipe.model.recipe.CookingTime;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Servings;
import seedu.recipe.model.recipe.Url;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_INGREDIENT = "alice@gmail.com";
    public static final String DEFAULT_INSTRUCTION = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_COOKING_TIME = "20m";
    public static final String DEFAULT_PREPARATION_TIME = "85355255";
    public static final String DEFAULT_CALORIES = "5000";
    public static final String DEFAULT_SERVINGS = "3";
    public static final String DEFAULT_URL = "https://www.jamieoliver.com/recipes/rice-recipes/a-basic-risotto-recipe/";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Ingredient ingredient;
    private Instruction instruction;
    private CookingTime cookingTime;
    private PreparationTime preparationTime;
    private Calories calories;
    private Servings servings;
    private Url url;
    private Set<Tag> tags;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        ingredient = new Ingredient(DEFAULT_INGREDIENT);
        instruction = new Instruction(DEFAULT_INSTRUCTION);
        cookingTime = new CookingTime(DEFAULT_COOKING_TIME);
        preparationTime = new PreparationTime(DEFAULT_PREPARATION_TIME);
        calories = new Calories(DEFAULT_CALORIES);
        servings = new Servings(DEFAULT_SERVINGS);
        url = new Url(DEFAULT_URL);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        ingredient = recipeToCopy.getIngredient();
        instruction = recipeToCopy.getInstruction();
        cookingTime = recipeToCopy.getCookingTime();
        preparationTime = recipeToCopy.getPreparationTime();
        calories = recipeToCopy.getCalories();
        servings = recipeToCopy.getServings();
        url = recipeToCopy.getUrl();
        tags = new HashSet<>(recipeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    //@@Author kokonguyen191
    /**
     * Sets the {@code Instruction} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withInstruction(String instruction) {
        this.instruction = new Instruction(instruction);
        return this;
    }

    /**
     * Sets the {@code Ingredient} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withIngredient(String ingredient) {
        this.ingredient = new Ingredient(ingredient);
        return this;
    }

    /**
     * Sets the {@code CookingTime} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withCookingTime(String cookingTime) {
        this.cookingTime = new CookingTime(cookingTime);
        return this;
    }
    
    /**
     * Sets the {@code PreparationTime} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withPreparationTime(String preparationTime) {
        this.preparationTime = new PreparationTime(preparationTime);
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withCalories(String calories) {
        this.calories = new Calories(calories);
        return this;
    }

    /**
     * Sets the {@code Servings} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withServings(String servings) {
        this.servings = new Servings(servings);
        return this;
    }

    //@@author RyanAngJY
    /**
     * Sets the {@code Url} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withUrl(String url) {
        this.url = new Url(url);
        return this;
    }
    //@@author

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, ingredient, instruction, cookingTime, preparationTime, calories, servings, url, tags);
    }
}
