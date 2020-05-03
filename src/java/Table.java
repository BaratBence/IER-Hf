public class Table
{
	private Integer x,y,seats;
	private Costumers cost;
	private Boolean taken;
	
	
	public Table(Integer xp, Integer yp,Integer seatsp)
	{
		x=xp; y=yp; seats=seatsp;
		taken=false;
		
	}
	public void setCostumers(Costumers costp)
	{
		cost=costp;
	}
	public Costumers getCostumers()
	{
		return cost;
	}
	public Integer getX() {
		return x;
	}
	public Integer getY() {
		return y;
	}
	public Integer getSeats() {
		return seats;
	}
	public Boolean getTaken() {
		return taken;
	}
	public void serTaken(Boolean takenp) {
	    taken=takenp;
	}
	
	
}