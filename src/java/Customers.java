public class Customers
{
	private Integer Ordertime,Eatingtime,People;
	public Customers(Integer ordertime,Integer eatingtime,Integer people)
	{
		Ordertime=ordertime;
		Eatingtime=eatingtime;
		People=people;
	}
	
	private String status="Taken";
	public void Ordering()
	{
		try {
			Thread.sleep(Ordertime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		status="Order";
	}
	public Integer getPeople()
	{
		return People;
	}
	public void OrderTaken()
	{
		status="Ordered";
	}
	public String getStatus()
	{
		return status;
	}
	public void Eating()
	{
		try {
			if(status.equals("Eating"))
			Thread.sleep(Eatingtime);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}