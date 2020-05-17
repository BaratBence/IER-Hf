package Kitchen;

import java.util.ArrayList;

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
		if(Orders.size()>=1)
		{
			for(int i=place;i<Orders.size()-1;i++)
			{
				Orders.set(place,Orders.get(place+1));
				Amount.set(place,Amount.get(place+1));
			}
		}
		else
		{
			Orders=null;
			Amount=null;
		}

	}
	public void setOrders(ArrayList<String> orders,ArrayList<Integer> amount)
	{
		Orders.addAll(orders);
		Amount.addAll(amount);
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
