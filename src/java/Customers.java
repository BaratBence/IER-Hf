import java.util.ArrayList;
import java.util.Random;

import Kitchen.Order;

public class Customers
{
	private Integer Eatingtime,People;
	private Order preferences;
	private Boolean finished = false;
	
	public Customers(int eatingtime, int people)
	{
		Eatingtime=eatingtime;
		People=people;
		preferences = new Order();
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("SoupA");
		temp.add("SoupB");
		temp.add("MainA");
		temp.add("MainB");
		temp.add("DessertA");
		temp.add("DessertB");
		temp.add("DessertC");
		for(int i = temp.size(); i > 0; --i)
		{
			Random rand = new Random();
			preferences.setOrder(temp.remove(rand.nextInt(i)),People);
		}
	}
	
	public Order GetPreferences()
	{
		return preferences;
	}
	
	private String status="Taken";
	
	public void Ordering()
	{
		status="Ordered";
	}
	public Integer getPeople()
	{
		return People;
	}
	public void Served()
	{
		status="Eating";
		try {
			Thread.sleep(Eatingtime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Finished();
	}
	public String getStatus()
	{
		return status;
	}
	public void Finished()
	{
		finished = true;
	}
	public Boolean FinishedYet()
	{
		return finished;
	}
}