
import Kitchen.Ingredient;

public class StorageBox {
	private Ingredient Ingredient;
	private Integer x,y;
	public StorageBox(Integer X,Integer Y,Ingredient ingredient)
	{
		x=X;
		y=Y;
		Ingredient=ingredient;
	}
	public Integer getX() {
		return x;
	}
	public Integer getY() {
		return y;
	}
	public Ingredient getIngredient()
	{
		return Ingredient;
	}

}
