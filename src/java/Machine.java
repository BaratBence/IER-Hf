

public class Machine extends Thread{
	private Integer Time;
	private int x,y;
	private String status,Name;
	private RestaurantView view;
	private Boolean Working;
	
	public Machine(String name, int X,int Y)
	{
		Working=false;
		Name=name;
		x=X;
		y=Y;
		status="Free";
	}
	public void setview(RestaurantView view)
	{
		this.view=view;
	}
	public void setTime(Integer time)
	{
		Time=time;
	}
	public String getname()
	{
		return Name;
	}
	public String getStatus()
	{
		 return status;
	}
	public void setStatus(String Status)
	{
		status=Status;
	}
	public Integer getX() {
		return x;
	}
	public Integer getY() {
		return y;
	}
	public Boolean getWorking()
	{
		return Working;
	}
	public void setWorking(Boolean Wparam)
	{
		Working=Wparam;
	}
	public void run()
	{
		status="Work";
		view.update(x,y);
		try { sleep(Time); } catch (InterruptedException e) {e.printStackTrace();}
		status="Ready";
		view.update(x,y);
	}
}
