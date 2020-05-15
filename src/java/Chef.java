import java.util.ArrayList;

import Kitchen.Ingredient;
import Kitchen.Order;
import Kitchen.Recipe;
import Kitchen.Recipes;
import jason.environment.grid.Location;

public class Chef {
	private Boolean problem=false,Work=false,Waiting=false,MoveTo=false;
	private Ingredient Carrying = new Ingredient("",0,false);
	private Recipe Active=new Recipe("",null,false,0,false,0,0);
	private Recipes RecipeBook=new Recipes();
	private Order Picked=new Order(),LeftOver=new Order();
	public Integer a=0,b=0,c=0,d=0,TargetX=0,TargetY=0;
	public String al="a";
	public void CheckResources(ArrayList<StorageBox> Storage,ArrayList<Order> Orders)
	{
		ArrayList<Ingredient> Required=new ArrayList<Ingredient>();
		Required.add(new Ingredient("A",0,false));
		Required.add(new Ingredient("B",0,false));
		Required.add(new Ingredient("C",0,false));
		Required.add(new Ingredient("D",0,false));
		Picked=Orders.get(0);
		CheckLeft(Picked);
		for(int i=0;i<Picked.getOrders().size();i++)
		{
			FindRecipe(Picked.getOrders().get(i));
			for(int k=0;k<Active.getIngredients().size();k++)
			{
				for(int h=0;h<Required.size();h++)
				{
					if(Required.get(h).getName().equals(Active.getIngredients().get(k).getName()))
					{
						Required.get(h).setAmount(Required.get(h).getAmount()+(Active.getIngredients().get(k).getAmount()*Picked.getAmount().get(i)));
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
		a=Required.get(0).getAmount();
		b=Required.get(1).getAmount();
		c=Required.get(2).getAmount();
		d=Required.get(3).getAmount();
	}	
	private void CheckLeft(Order Picked)
	{
		for(int i=0;i<Picked.getOrders().size();i++)
		{
			for(int j=0;j<LeftOver.getOrders().size();j++)
			{
				if(Picked.getOrders().get(i).equals(LeftOver.getOrders().get(j)))
				{
					if(LeftOver.getAmount().get(j)>=Picked.getAmount().get(i))
					{
						LeftOver.getAmount().set(j, LeftOver.getAmount().get(j)-Picked.getAmount().get(i));
						Picked.getAmount().set(i, 0);
					
					}
					else 
					{	
						Picked.getAmount().set(i,Picked.getAmount().get(i)-LeftOver.getAmount().get(j));
						LeftOver.getAmount().set(j, 0);
					}
				}
			}
		}
	}
	public void Prepare(ArrayList<StorageBox> Storage)
	{
		FindRecipe(Picked.getOrders().get(0));
		for(int i=0;i<Active.getIngredients().size();i++)
		{
			for(int j=0;j<Storage.size();j++)
			{
				if(Active.getIngredients().get(i).getName().equals(Storage.get(j).getIngredient().getName()) && Active.getIngredients().get(i).getAmount()>0)
				{
					TargetX=Storage.get(j).getX();
					MoveTo=true;
					return;
				}
			}
		}
		
	}
	public void moveTo(RestaurantModel model)
	{
		Location position = model.getAgPos(2);
		if(position.x>TargetX) position.x--;
		else position.x++;
		model.setAgPos(2, position);
		if(position.x == TargetX) MoveTo=false;
	}
	public void pickUp(ArrayList<StorageBox> Storage,RestaurantModel model,RestaurantView view,ArrayList<Machine> Machines)
	{
		for(int i=0;i<Storage.size();i++) 
			if(Storage.get(i).getX()==model.getAgPos(2).x)
			{
				Carrying=getActionIngredient(Storage.get(i).getIngredient().getName());
				Storage.get(i).getIngredient().setAmount(Storage.get(i).getIngredient().getAmount()-Carrying.getAmount());
				CompleteIngredient(Carrying.getName());
				if(!Carrying.getChopped()) {
					if(Active.getBaked()) 
					{
						for(int j=0;j<Machines.size();j++)
						{
							if(Machines.get(j).getname().equals("Owen")) TargetX=Machines.get(j).getX();
							
							MoveTo=true;
						}
					}
					else if(Active.getCooked())
					{
						for(int j=0;j<Machines.size();j++)
						{
							if(Machines.get(j).getname().equals("Stove")) TargetX=Machines.get(j).getX();
							a=TargetX;
							MoveTo=true;
						}
					}
				}
				view.update(Storage.get(i).getX(),Storage.get(i).getY());
			}
	}
	private void CompleteIngredient(String item)
	{
		for(int i=0;i<Active.getIngredients().size();i++)
		{
			if(Active.getIngredients().get(i).getName().equals(item))
			{
				Active.getIngredients().get(i).setAmount(0);
			}
		}
	}
	private Ingredient getActionIngredient(String item)
	{
		for(int i=0;i<Active.getIngredients().size();i++)
		{
			if(Active.getIngredients().get(i).getName().equals(item));
			{
				return Active.getIngredients().get(i);
			}
		}
		return Active.getIngredients().get(0);
	}
	private void FindRecipe(String Meal)
	{
		for(int i=0;i<RecipeBook.getBook().size();i++)
		{
			if(RecipeBook.getBook().get(i).getName().equals(Meal)) Active=RecipeBook.getBook().get(i); 
		}
	}
	public Integer getTargetx()
	{
		return TargetX;
	}
	public Ingredient getCarrying()
	{
		return Carrying;
	}
	public Boolean getWork()
	{
		return Work;
	}
	public Boolean getWaiting()
	{
		return Waiting;
	}
	public Boolean getProblem()
	{
		return problem;
	}
	public Boolean getMoveTo()
	{
		return MoveTo;
	}

}
