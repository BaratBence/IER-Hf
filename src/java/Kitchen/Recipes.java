package Kitchen;
import java.util.ArrayList;

public class Recipes {
	private ArrayList<Recipe> Book = new ArrayList<Recipe>();
	public Recipes()
	{
		ArrayList<Ingredient> SoupA = new ArrayList<Ingredient>();
		SoupA.add(new Ingredient("A",4,true));
		SoupA.add(new Ingredient("B",3,false));
		SoupA.add(new Ingredient("C",2,false));
		SoupA.add(new Ingredient("D",0,false));
		Book.add(new Recipe("SoupA",SoupA,true,2000,false,0,2));
		ArrayList<Ingredient> SoupB = new ArrayList<Ingredient>();
		SoupB.add(new Ingredient("A",0,false));
		SoupB.add(new Ingredient("B",4,false));
		SoupB.add(new Ingredient("C",2,false));
		SoupB.add(new Ingredient("D",3,false));
		Book.add(new Recipe("SoupB",SoupB,true,5000,false,0,4));
		ArrayList<Ingredient> MainA = new ArrayList<Ingredient>();
		MainA.add(new Ingredient("A",5,true));
		MainA.add(new Ingredient("B",2,true));
		MainA.add(new Ingredient("C",3,false));
		MainA.add(new Ingredient("D",1,false));
		Book.add(new Recipe("MainA",MainA,true,4000,false,0,3));
		ArrayList<Ingredient> MainB = new ArrayList<Ingredient>();
		MainB.add(new Ingredient("A",4,false));
		MainB.add(new Ingredient("B",4,false));
		MainB.add(new Ingredient("C",4,true));
		MainB.add(new Ingredient("D",4,false));
		Book.add(new Recipe("MainB",MainB,false,0,true,5000,6));
		ArrayList<Ingredient> DessertA = new ArrayList<Ingredient>();
		DessertA.add(new Ingredient("A",2,false));
		DessertA.add(new Ingredient("B",1,false));
		DessertA.add(new Ingredient("C",0,false));
		DessertA.add(new Ingredient("D",0,false));
		Book.add(new Recipe("DessertA",DessertA,false,0,true,1000,3));
		ArrayList<Ingredient> DessertB = new ArrayList<Ingredient>();
		DessertB.add(new Ingredient("A",0,false));
		DessertB.add(new Ingredient("B",2,true));
		DessertB.add(new Ingredient("C",0,false));
		DessertB.add(new Ingredient("D",2,false));
		Book.add(new Recipe("DessertB",DessertB,false,0,false,0,3));
		ArrayList<Ingredient> DessertC = new ArrayList<Ingredient>();
		DessertC.add(new Ingredient("A",0,false));
		DessertC.add(new Ingredient("B",0,false));
		DessertC.add(new Ingredient("C",1,false));
		DessertC.add(new Ingredient("D",2,false));
		Book.add(new Recipe("DessertC",DessertC,false,0,false,0,3));
	}
	public ArrayList<Recipe> getBook()
	{
		return Book;
	}
}
