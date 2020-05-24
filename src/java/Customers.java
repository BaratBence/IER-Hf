import java.util.ArrayList;
import java.util.Random;

public class Customers
{
	private Integer Eatingtime,People;
	private ArrayList<String> preferences;
	
	public Customers(int eatingtime, int people)
	{
		Eatingtime=eatingtime;
		People=people;
		preferences = new ArrayList<String>();
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
			preferences.add(temp.remove(rand.nextInt(i)));
		}
	}
	
	public ArrayList<String> GetPreferences()
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
	public void OrderTaken()
	{
		status="Eating";
		try {
			Thread.sleep(Eatingtime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getStatus()
	{
		return status;
	}
}