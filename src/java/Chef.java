import java.util.ArrayList;

import Kitchen.Ingredient;
import Kitchen.Order;
import Kitchen.Recipes;

public class Chef {
	private Boolean problem=false,Work=false;
	private Recipes RecipeBook=new Recipes();
	public void CheckResources(ArrayList<StorageBox> Storage,ArrayList<Order> Orders)
	{
		ArrayList<Ingredient> Required=new ArrayList<Ingredient>();
		Required.add(new Ingredient("A",0,false));
		Required.add(new Ingredient("B",0,false));
		Required.add(new Ingredient("C",0,false));
		Required.add(new Ingredient("D",0,false));
		Order Picked=Orders.get(0);
		for(int i=0;i<Picked.getOrders().size();i++)
		{
			for(int j=0;j<RecipeBook.getBook().size();j++)
			{
				if(Picked.getOrders().get(i).equals(RecipeBook.getBook().get(j).getName()))
				{
					for(int k=0;k<RecipeBook.getBook().get(j).getIngredients().size();k++)
					{
						for(int h=0;h<Required.size();h++)
						{
							if(Required.get(h).getName().equals(RecipeBook.getBook().get(j).getIngredients().get(k).getName()))
							{
								Required.get(h).setAmount(Required.get(h).getAmount()+(RecipeBook.getBook().get(j).getIngredients().get(k).getAmount()*Picked.getAmount().get(i)));
							}
						}
							
					}
				}
			}
			
		}
		for(int i=0;i<Storage.size();i++)
		{
			for(int j=0;j<Required.size();j++)
			{
				if(Required.get(j).getName().equals(Storage.get(i).getIngredient().getName()) && Required.get(j).getAmount()>Storage.get(i).getIngredient().getAmount()) problem=true;
			}
		}
		if(!problem) Work=true;
	}
	public Boolean getWork()
	{
		return Work;
	}
	public Boolean getProblem()
	{
		return problem;
	}
}
