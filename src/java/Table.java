public class Table
{
	private int x,y,seats;
	private Boolean taken;
	private enum status {
		Free("Free"), Taken("Taken"), Order("Order"), Ordered("Ordered"), Eating("Eating");
		private String state;
		private status(String s)
		{
			state=s;
		}
		public String toString()
		{
			return this.state;
		}
	};
	private status currentstatus;
	public Table(int xp, int yp,int seatsp)
	{
		x=xp; y=yp; seats=seatsp;
		taken=false;
		currentstatus = status.Free;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getSeats() {
		return seats;
	}
	public Boolean getTaken() {
		return taken;
	}
	public void serTaken(Boolean takenp) {
	    taken=takenp;
	}
	public String getCurrentStatus()
	{
		return currentstatus.toString();
	}
	public void nextStatus()
	{
		switch(currentstatus)
		{
		case Free:
			currentstatus= status.Taken;
			break;
		case Taken:
			currentstatus= status.Order;
			break;
		case Order:
			currentstatus= status.Ordered;
			break;
		case Ordered:
			currentstatus= status.Eating;
			break;
		}
	}
	
}