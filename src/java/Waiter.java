import java.util.ArrayList;

import Kitchen.Order;
import jason.environment.grid.Location;

public class Waiter
{	 
	private Order orderToTake, orderToServe;
	
	public void OrderToServe(Order ord)
	{
		orderToServe = ord;
	}
	public void OrderToTake(Order ord, int x, int y)
	{
		orderToTake = ord;
		orderToTake.setFromX(x);
		orderToTake.setFromY(y);
	}
	public Order GetOrderToServe()
	{
		return orderToServe;
	}
	public Order GetOrderToTake()
	{
		return orderToTake;
	}
	public void PutOrder(ArrayList<Order> to)
	{
		
	}
	
	public void moveTowards(RestaurantModel model, ArrayList<Wall> Walls, int x, int y)
	{
		 Location waiter = model.getAgPos(0);
		 
		 if(waiter.x == x && waiter.y == y)return;
		 
		 Boolean up = false;
		 Boolean right = false;
		 Boolean stayX = false;
		 Boolean stayY = false;
		 Boolean upBlock = false;
		 Boolean downBlock = false;
		 Boolean leftBlock = false;
		 Boolean rightBlock = false;
		 if(waiter.y > y) up = true;
		 if(waiter.x < x) right = true;
		 if(waiter.y == y) stayY = true;
		 if(waiter.x == x) stayX = true;
		 
		 for(int i = 0; i < Walls.size(); ++i)
		 {
			 Wall current = Walls.get(i);
			 if(current.x == waiter.x + 1 && current.y == waiter.y) rightBlock = true;
			 if(current.x == waiter.x - 1 && current.y == waiter.y) leftBlock = true;
			 if(current.x == waiter.x && current.y == waiter.y + 1) downBlock = true;
			 if(current.x == waiter.x && current.y == waiter.y - 1) upBlock = true;
		 }
		 if(x % 2 == 1)
		 { 
			 if(up)
			 {
				 if(!upBlock && !stayY) model.setAgPos(0,waiter.x,waiter.y-1);
				 else
				 {
					 if(right && !rightBlock && !stayX) model.setAgPos(0,waiter.x+1,waiter.y);
					 else if(!right && !leftBlock && !stayX) model.setAgPos(0, waiter.x-1, waiter.y);
				 }
			 }
			 else
			 {
				 if(!downBlock && !stayY) model.setAgPos(0,waiter.x,waiter.y+1);
				 else
				 {
					 if(right && !rightBlock && !stayX) model.setAgPos(0,waiter.x+1,waiter.y);
					 else if(!right && !leftBlock && !stayX) model.setAgPos(0, waiter.x-1, waiter.y);
				 }
			 }
		 }
		 else
		 { 
			 if(right)
			 {
				 if(!rightBlock && !stayX) model.setAgPos(0,waiter.x+1,waiter.y);
				 else
				 {
					 if(up && !upBlock && !stayY) model.setAgPos(0,waiter.x,waiter.y-1);
					 else if(!up && !downBlock && !stayY) model.setAgPos(0, waiter.x, waiter.y+1);
				 }
			 }
			 else
			 {
				 if(!leftBlock && !stayX) model.setAgPos(0,waiter.x-1,waiter.y);
				 else
				 {
					 if(up && !upBlock && !stayY) model.setAgPos(0,waiter.x,waiter.y-1);
					 else if(!up && !downBlock && !stayY) model.setAgPos(0, waiter.x, waiter.y+1);
				 }
			 }
		 }
	}
	 
	public void CheckOrders(Order ord)
	{
		if(ord != null)
		{
			orderToTake = ord;
			 
		}
	}
}