public class Customers
{
	private Integer Eatingtime,People;
	public Customers(int eatingtime, int people)
	{
		Eatingtime=eatingtime;
		People=people;
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