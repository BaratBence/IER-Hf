import java.util.ArrayList;

import jason.environment.grid.Location;

public class Host
{
	public Location HostTarget= new Location(-1,-1);
	public Integer minDiff=1000,a=0;
	private Boolean IsFollowed = false, Back=false;
	public Integer targetTableId=null;
	public void findTable(ArrayList<Table> tables,ArrayList<Customers> Waiting)
    {
    	for(int i=0;i<tables.size();i++)
    	{
    		if(tables.get(i).getSeats()>=Waiting.get(0).getPeople() && !tables.get(i).getTaken()) 
    			{
    				Integer diff = tables.get(i).getSeats()-Waiting.get(0).getPeople();
    				if(diff>=0 && diff<minDiff)
    				{
    					minDiff=diff;
    					targetTableId=i;
    					HostTarget=new Location(tables.get(i).getX(),tables.get(i).getY());
    				}
    			}
    	}
    	if(HostTarget.x!=-1 && HostTarget.y!=-1) 
    		{
    			minDiff=1000;
    			IsFollowed=true;
    		}
    }
    public void LeadToTable(ArrayList<Table> tables,ArrayList<Customers> Waiting,RestaurantModel model,RestaurantView view)
    {
    	Location host = model.getAgPos(1);
    	if(host.y!=HostTarget.y+1) host.y--;
    	else if(HostTarget.x>host.x) host.x++;
    	else if(HostTarget.x<host.x) host.x--;
    	else 
    	{
    		Back=true;
    		IsFollowed = false;
    		for(int i=0;i<tables.size();i++) 
    			if(tables.get(i).getX() == HostTarget.x && tables.get(i).getY() == HostTarget.y)
    			{
    				tables.get(i).setCostumers(Waiting.get(0));
    				tables.get(i).setTaken(true);
    				Waiting.remove(0);
    				view.update(HostTarget.x,HostTarget.y);
    			}
    	}
    	model.setAgPos(1,host);
    }
    public void GetBack(RestaurantModel model)
    {
    	Location host = model.getAgPos(1);
    	if(host.x == 4 && host.y<12) host.y++;
    	else if(4>host.x) host.x++;
    	else if(4<host.x) host.x--;
    	else  Back=false; 
    	model.setAgPos(1,host);
    }
    public Boolean getBack()
    {
    	return Back;
    }
    public Boolean getIsFollowed()
    {
    	return IsFollowed;
    }
    
}