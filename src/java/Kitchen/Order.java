package Kitchen;

import java.util.ArrayList;
import java.util.logging.Logger;
public class Order {
	private ArrayList<String> Orders=new ArrayList<String>();
	private ArrayList<Integer> Amount=new ArrayList<Integer>();
	private Integer fromX,fromY;
	public void setOrder(String order,Integer amount)
	{
		Orders.add(order);
		Amount.add(amount);
	}
	public void removeOrder(Integer place)
	{
		
		if(Orders.size()>1)
		{
			for(int i=place;i<Orders.size()-1;i++)
			{
				Orders.set(place,Orders.get(place+1));
				Amount.set(place,Amount.get(place+1));
			}
			Orders.remove(Orders.size()-1);
			Amount.remove(Amount.size()-1);
		}
		else
		{
			Orders=new ArrayList<String>();
			Amount=new ArrayList<Integer>();
		}

	}
	public void setOrders(ArrayList<String> orders,ArrayList<Integer> amount)
	{
		Orders.addAll(orders);
		Amount.addAll(amount);
	}
	public void setPref(ArrayList<String> orders)
	{
		Orders.addAll(orders);
	}
	public void setFromX(Integer fromx)
	{
		fromX=fromx;
	}
	public void setFromY(Integer fromy)
	{
		fromY=fromy;
	}
	public Integer getFromX()
	{
		return fromX;
	}
	public Integer getFromY()
	{
		return fromY;
	}
	public ArrayList<String> getOrders()
	{
		return Orders;
	}
	public ArrayList<Integer> getAmount()
	{
		return Amount;
	}
}
