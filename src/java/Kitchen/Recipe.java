package Kitchen;
import java.util.ArrayList;

public class Recipe {
	private String Name;
	private ArrayList<Ingredient> Ingredient = new ArrayList<Ingredient>();
	private Integer CookingTime,BakingTime;
	private Boolean Cooked,Baked;
	public Recipe(String name,ArrayList<Ingredient> ingredient,Boolean cooked,Integer cookingTime,Boolean baked,Integer bakingTime)
	{
		Name=name;
		Ingredient=ingredient;
		Cooked=cooked;
		CookingTime=cookingTime;
		Baked=baked;
		BakingTime=bakingTime;
	}
	public String getName()
	{
		return Name;
	}
	public Integer getCookingTime()
	{
		return CookingTime;
	}
	public Integer getBakingTime()
	{
		return BakingTime;
	}
	public Boolean getCooked()
	{
		return Cooked;
	}
	public Boolean getBaked()
	{
		return Baked;
	}
	public ArrayList<Ingredient> getIngredients()
	{
		return Ingredient;
	}
	
}
