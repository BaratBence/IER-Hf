import java.util.ArrayList;

import Kitchen.Ingredient;
import Kitchen.Order;
import Kitchen.Recipe;
import Kitchen.Recipes;
import jason.environment.grid.Location;

public class Chef {
	//states
	private Boolean CheckResources,problem,Prepare,MoveTo,PickUp,PutIn,Serve,Make,SumUp,FoundRecipe;
	private Ingredient Carrying;
	private Recipe Active;
	private Recipes RecipeBook;
	private Order Picked,Ready,LeftOver;
	public Integer a=0,b=0,c=0,d=0,e=0,TargetX=0,TargetY=0;
	public String bl="a",al="a",status="a",carried="b",k="a";
	public Chef()
	{
		Picked=new Order();
		LeftOver=new Order();
		FoundRecipe=false;
		//LeftOver.setOrder("SoupA", 4);
		Ready=new Order();
		Carrying=new Ingredient("",0,false);
		Active=new Recipe("",null,false,0,false,0,0);
		RecipeBook=new Recipes();
		CheckResources=true;
		problem=false;
		SumUp=false;
		Prepare=false;
		MoveTo=false;
		PickUp=false;
		PutIn=false;
		Serve=false;
		Make=false;
		LeftOver.setOrder("SoupA", 0);
		LeftOver.setOrder("SoupB", 0);
		LeftOver.setOrder("MainA", 0);
		LeftOver.setOrder("MainB", 0);
		LeftOver.setOrder("DessertA", 0);
		LeftOver.setOrder("DessertB", 0);
	}
	public void CheckResources(ArrayList<StorageBox> Storage,ArrayList<Order> Orders)
	{
		status="checkResources";
		ArrayList<Ingredient> Required=new ArrayList<Ingredient>();
		Required.add(new Ingredient("A",0,false));
		Required.add(new Ingredient("B",0,false));
		Required.add(new Ingredient("C",0,false));
		Required.add(new Ingredient("D",0,false));
		Picked=Orders.get(0);
		Ready=Orders.get(0);
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
				if(Required.get(j).getName().equals(Storage.get(i).getIngredient().getName()) && Required.get(j).getAmount()>Storage.get(i).getIngredient().getAmount()) problem=true;
			}
		}
		
		if(!problem) 
			{
				//Orders.remove(0);
				FoundRecipe=false;
				Prepare=true;
				CheckResources=false;
			}
	}	
	public void Prepare(ArrayList<StorageBox> Storage)
	{
		status="prepare";
		Sumup();
		if(Finished()) status="done";
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
					Prepare=false;
					MoveTo=true;
					return;
				}
				
			}
		}
		Prepare=false;
		Make=true;
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
	public void moveTo(RestaurantModel model,ArrayList<Machine> Machines)
	{
		status="moveTo";
		Location position = model.getAgPos(2);
		if(position.x>TargetX) position.x--;
		else position.x++;
		model.setAgPos(2, position);
		if(position.x == TargetX) MoveTo=false;
	}
	public void pickUp(ArrayList<StorageBox> Storage,RestaurantModel model,RestaurantView view,ArrayList<Machine> Machines)
	{
		status="pickup";
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
							MoveTo=true;
						}
					}
					else if(Active.getCooked())
					{
						for(int j=0;j<Machines.size();j++)
						{
							if(Machines.get(j).getname().equals("Stove")) TargetX=Machines.get(j).getX();
							MoveTo=true;
						}
					}
					else 
					{
						Carrying=new Ingredient("",0,false);
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
			if(Active.getIngredients().get(i).getName().equals(item))
			{
				return Active.getIngredients().get(i);
			}
		}
		return Active.getIngredients().get(0);
	}
	
	public void putin(ArrayList<Machine> Machines)
	{
		status="putin";
		Carrying.setAmount(0);
		Prepare=true;
	}
	public void make(ArrayList<Machine> Machines,RestaurantModel model,RestaurantView view)
	{
		status="make";
		if(model.getAgPos(2).x==6)
		{
			Machines.get(1).setview(view);
			Machines.get(1).setTime(Active.getCookingTime());
			Machines.get(1).run();
			Serve=true;
			Make=false;
		}
		else
		{
			Machines.get(0).setview(view);
			Machines.get(0).setTime(Active.getBakingTime());
			Machines.get(0).run();
			Serve=true;
			Make=false;
		}
	}
	public void serve(ArrayList<Machine> Machines,RestaurantModel model,RestaurantView view)
	{
		status="serve";
		if(model.getAgPos(2).x==6)
		{
			Machines.get(1).stop();
			Machines.get(1).setStatus("Free");
			view.update(model.getAgPos(2).x,model.getAgPos(2).y+1);
			Order tmp=new Order();
			tmp.setOrder(Active.getName(),Active.getOutput());
			addLeftOver(tmp);
			Serve=false;
			Prepare=true;
			FoundRecipe=false;
		}
		else
		{
			Machines.get(0).stop();
			Machines.get(0).setStatus("Free");
			view.update(model.getAgPos(2).x,model.getAgPos(2).y+1);
			Order tmp=new Order();
			tmp.setOrder(Active.getName(),Active.getOutput());
			addLeftOver(tmp);
			Serve=false;
			Prepare=true;
			FoundRecipe=false;
		}
	}
	private Boolean Finished()
	{
		for(int i=0;i<Picked.getAmount().size();i++)
		{
			if(Picked.getAmount().get(0)>0)  return false;
		}
		CheckResources=true;
		return true; 
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
			if(Picked.getAmount().get(i)==0) {Picked.removeOrder(i);bl=Picked.getOrders().get(i);}
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
	public Ingredient getCarrying()
	{
		return Carrying;
	}
	public Boolean getMake()
	{
		return Make;
	}
	public Boolean getProblem()
	{
		return problem;
	}
	public Boolean getMoveTo()
	{
		return MoveTo;
	}
	public Boolean getPickUp()
	{
		return PickUp;
	}
	public Boolean getPrepare()
	{
		return Prepare;
	}
	public Boolean getServe()
	{
		return Serve;
	}
	public Boolean getPutIn()
	{
		return PutIn;
	}
	public Boolean getCheckResources() {
		return CheckResources;
	}
	public Boolean getSumup()
	{
		return SumUp;
	}

}
