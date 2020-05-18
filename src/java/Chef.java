import java.util.ArrayList;
import java.util.Arrays;

import Kitchen.Ingredient;
import Kitchen.Order;
import Kitchen.Recipe;
import Kitchen.Recipes;
import jason.environment.grid.Location;

public class Chef {
	//states
	private Boolean OrderFinished,FoundRecipe;
	private ArrayList<Boolean> States=new ArrayList<Boolean>(Arrays.asList(true,false,false,false,false,false,false));
	private Ingredient Carrying;
	private Recipe Active;
	private Recipes RecipeBook;
	private Order Picked,Ready,LeftOver;
	public Integer TargetX=0;
	public Chef()
	{
		OrderFinished=false;
		Picked=new Order();
		LeftOver=new Order();
		FoundRecipe=false;
		Ready=new Order();
		Carrying=new Ingredient("",0,false);
		Active=new Recipe("",null,false,0,false,0,0);
		RecipeBook=new Recipes();
		LeftOver.setOrder("SoupA", 0);
		LeftOver.setOrder("SoupB", 0);
		LeftOver.setOrder("MainA", 0);
		LeftOver.setOrder("MainB", 0);
		LeftOver.setOrder("DessertA", 0);
		LeftOver.setOrder("DessertB", 0);
	}
	public void CheckResources(ArrayList<StorageBox> Storage,ArrayList<Order> Orders)
	{
		ArrayList<Ingredient> Required=new ArrayList<Ingredient>();
		Required.add(new Ingredient("A",0,false));
		Required.add(new Ingredient("B",0,false));
		Required.add(new Ingredient("C",0,false));
		Required.add(new Ingredient("D",0,false));
		Picked=Orders.get(0);
		Sumup();
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
				if(Required.get(j).getName().equals(Storage.get(i).getIngredient().getName()) && Required.get(j).getAmount()>Storage.get(i).getIngredient().getAmount()) States.set(1,true);
			}
		}
		
		if(!States.get(1)) 
			{
				FoundRecipe=false;
				States.set(2,true);
				States.set(0, false);
			}
	}	
	public void Prepare(ArrayList<StorageBox> Storage,ArrayList<Order> Orders,Integer x)
	{
		Sumup();
		if(Finished()) 
			{
				Ready=Orders.get(0);
				Orders.remove(0);
				if(x==6)
				{
					States.set(0,true);
				}
				else 
				{
					TargetX=6;
					States.set(3,true);
					OrderFinished=true;
				}
				States.set(2,false);
			}
		else
		{	
		if(!FoundRecipe) FindRecipe(Picked.getOrders().get(0));
		for(int i=0;i<Active.getIngredients().size();i++)
		{
			for(int j=0;j<Storage.size();j++)
			{
				if(Active.getIngredients().get(i).getName().equals(Storage.get(j).getIngredient().getName()) && Active.getIngredients().get(i).getAmount()>0)
				{
					TargetX=Storage.get(j).getX();
					States.set(2,false);
					States.set(3,true);
					return;
				}
				
			}
		}
		States.set(2,false);
		States.set(5,true);
		}
	}
	private void FindRecipe(String Meal)
	{
		for(int i=0;i<RecipeBook.getBook().size();i++)
		{
			if(RecipeBook.getBook().get(i).getName().equals(Meal))
				{
					Recipe tmp=RecipeBook.getBook().get(i);
					ArrayList<Ingredient> Ing = new ArrayList<Ingredient>();
					for(int j=0;j<tmp.getIngredients().size();j++)
					{
					Ing.add(new Ingredient(tmp.getIngredients().get(j).getName(),tmp.getIngredients().get(j).getAmount(),tmp.getIngredients().get(j).getChopped()));
					Active=new Recipe(tmp.getName(),Ing,tmp.getCooked(),tmp.getCookingTime(),tmp.getBaked(),tmp.getBakingTime(),tmp.getOutput());
					FoundRecipe=true;
					
					}
				}
		}
	}
	private Boolean Finished()
	{
		for(int i=0;i<Picked.getAmount().size();i++)
		{
			if(Picked.getAmount().get(0)>0)  return false;
		}
		return true; 
	}
	public void moveTo(RestaurantModel model,ArrayList<Machine> Machines)
	{
		Location position = model.getAgPos(2);
		if(position.x>TargetX) position.x--;
		else position.x++;
		model.setAgPos(2, position);
		if(position.x == TargetX)
		{
			if(OrderFinished) 
			{
				States.set(0,true);
				OrderFinished=false;
			}
			States.set(3,false);
		}
	}
	public void pickUp(ArrayList<StorageBox> Storage,RestaurantModel model,RestaurantView view,ArrayList<Machine> Machines)
	{
		for(int i=0;i<Storage.size();i++) 
			if(Storage.get(i).getX()==model.getAgPos(2).x)
			{
				Carrying.SetIngredient(getActionIngredient(Storage.get(i).getIngredient().getName()));
				Storage.get(i).getIngredient().setAmount(Storage.get(i).getIngredient().getAmount()-Carrying.getAmount());
				
				CompleteIngredient(Carrying.getName());
				if(!Carrying.getChopped()) {
					if(Active.getBaked()) 
					{
						for(int j=0;j<Machines.size();j++)
						{
							if(Machines.get(j).getname().equals("Oven")) TargetX=Machines.get(j).getX();
							States.set(3,true);
						}
					}
					else if(Active.getCooked())
					{
						for(int j=0;j<Machines.size();j++)
						{
							if(Machines.get(j).getname().equals("Stove")) TargetX=Machines.get(j).getX();
							States.set(3,true);
						}
					}
					else
					{
						Carrying.setAmount(0);
						States.set(2,true);
					}
				}
				else 
				{
					States.set(6,true);
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
			if(Active.getIngredients().get(i).getName().equals(item))
			{
				return Active.getIngredients().get(i);
			}
		}
		return Active.getIngredients().get(0);
	}
	
	public void putin(ArrayList<Machine> Machines)
	{
		Carrying.setAmount(0);
		States.set(2,true);
	}
	public void Chopping(ArrayList<Machine> Machines)
	{
		if(Active.getBaked()) 
		{
			for(int j=0;j<Machines.size();j++)
			{
				if(Machines.get(j).getname().equals("Oven")) TargetX=Machines.get(j).getX();
				States.set(3,true);
			}
		}
		else if(Active.getCooked())
		{
			for(int j=0;j<Machines.size();j++)
			{
				if(Machines.get(j).getname().equals("Stove")) TargetX=Machines.get(j).getX();
				States.set(3,true);
			}
		}
		else
		{
			Carrying.setAmount(0);
			States.set(2,true);
		}
		States.set(6,false);
	}
	public void make(ArrayList<Machine> Machines,RestaurantModel model,RestaurantView view)
	{
		if(model.getAgPos(2).x==6)
		{
			Machines.get(1).setview(view);
			Machines.get(1).setTime(Active.getCookingTime());
			Machines.get(1).run();
		}
		else if(model.getAgPos(2).x==5)
		{
			Machines.get(0).setview(view);
			Machines.get(0).setTime(Active.getBakingTime());
			Machines.get(0).run();
		}
		States.set(4,true);
		States.set(5,false);
	}
	public void serve(ArrayList<Machine> Machines,RestaurantModel model,RestaurantView view)
	{
		if(model.getAgPos(2).x==6)
		{
			Machines.get(1).stop();
			Machines.get(1).setStatus("Free");
			view.update(model.getAgPos(2).x,model.getAgPos(2).y+1);
			
		}
		else if(model.getAgPos(2).x==5)
		{
			Machines.get(0).stop();
			Machines.get(0).setStatus("Free");
			view.update(model.getAgPos(2).x,model.getAgPos(2).y+1);
		}
		Order tmp=new Order();
		tmp.setOrder(Active.getName(),Active.getOutput());
		addLeftOver(tmp);
		States.set(4,false);
		States.set(2,true);
		FoundRecipe=false;
	}
	
	private void Sumup()
	{
		for(int i=0;i<LeftOver.getOrders().size();i++)
		{
			for(int j=0;j<Picked.getOrders().size();j++)
			{
				if(LeftOver.getOrders().get(i).equals(Picked.getOrders().get(j)) && LeftOver.getAmount().get(i)>0)
				{
					if(LeftOver.getAmount().get(i)>=Picked.getAmount().get(j)) 
						{
							LeftOver.getAmount().set(i, LeftOver.getAmount().get(i)-Picked.getAmount().get(j));
							Picked.getAmount().set(j, 0);
						}
					else 
						{
							Picked.getAmount().set(j, Picked.getAmount().get(j)-LeftOver.getAmount().get(i));
							LeftOver.getAmount().set(i,0);
						}
				}
			}
		}
		for(int i=0;i<Picked.getAmount().size();i++)
		{
			if(Picked.getAmount().get(i)==0) Picked.removeOrder(i);
		}
	}
	private void addLeftOver(Order param)
	{
		for(int i=0;i<LeftOver.getOrders().size();i++)
		{
			for(int j=0;j<param.getOrders().size();j++)
			{
				if(LeftOver.getOrders().get(i).equals(param.getOrders().get(j)))
				{
					LeftOver.getAmount().set(i, LeftOver.getAmount().get(i)+param.getAmount().get(j));
				}
			}
		}
	}
	public Integer getTargetx()
	{
		return TargetX;
	}
	public Boolean getMake()
	{
		return States.get(5);
	}
	public Boolean getProblem()
	{
		return States.get(1);
	}
	public Boolean getMoveTo()
	{
		return States.get(3);
	}
	public Boolean getChopped()
	{
		return States.get(6);
	}
	public Boolean getPrepare()
	{
		return States.get(2);
	}
	public Boolean getServe()
	{
		return States.get(4);
	}
	public Boolean getCheckResources() {
		return States.get(0);
	}

}
