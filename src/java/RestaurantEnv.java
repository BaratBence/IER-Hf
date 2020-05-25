import jason.asSyntax.*;
import jason.environment.Environment;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

import Kitchen.Order;

public class RestaurantEnv extends Environment {

    public static final Integer RestaurantSize = 13;
    public static final Integer RestaurantLength = 10;
    public static final int WALL = 8;
    public static final int TABLE = 16;
    public static final int INGREDIENT =32;
    public static final int MACHINE = 64;
    public static final int OBSTACLE=128;
    
    static Logger logger = Logger.getLogger(RestaurantEnv.class.getName());
    public ArrayList<Table> tables =new ArrayList<Table>();
    public ArrayList<Customers> Waiting=new ArrayList<Customers>();
    public ArrayList<StorageBox> Storage=new ArrayList<StorageBox>();
    public ArrayList<Machine> Machines=new ArrayList<Machine>();
    public ArrayList<Order> Orders=new ArrayList<Order>();
    public ArrayList<Order> Ready=new ArrayList<Order>();
    public ArrayList<Wall> Walls=new ArrayList<Wall>();
    
    private Order prefrences=new Order();
    private RestaurantModel model;
    private RestaurantView  view;
    private Host host=new Host();
    private Waiter waiter=new Waiter();
    private Chef chef=new Chef();
    public Boolean a=true;
    
    private int[] lastWaiterPos = {2,5};
    @Override
    public void init(String[] args) {
        model = new RestaurantModel(RestaurantLength,RestaurantSize,WALL,TABLE,INGREDIENT,MACHINE,OBSTACLE,tables,Storage,Machines,Walls);
        view  = new RestaurantView(model,tables,Storage,Machines);
        
        Waiting.add(new Customers(100,4));
        Waiting.add(new Customers(100,2));
        Waiting.add(new Customers(100,6));
        model.setView(view);
        updatePercepts();
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        try {
            if (action.getFunctor().equals("moveTowards")) { waiter.moveTowards(model, Walls, 
            		Integer.parseInt(action.getTerm(0).toString()),
            		Integer.parseInt(action.getTerm(1).toString())); }
            else if(action.getFunctor().equals("checkOrders")){
            	if(Ready.size() > 0)
            	{
            		Order currentOrder = Ready.remove(0);
            		waiter.OrderToServe(currentOrder);
            		int orderx = currentOrder.getFromX();
            		int ordery = currentOrder.getFromY();
            		if(currentOrder.getSuccess())
            			addPercept("waiter",Literal.parseLiteral("ordertoserve(" + orderx + "," + ordery + ",success" + ")"));
            		else
            			addPercept("waiter",Literal.parseLiteral("ordertoserve(" + orderx + "," + ordery + ",failure" + ")"));
            	}
            }
            else if(action.getFunctor().equals("takeOrder")) {
            	int waiterx = model.getAgPos(0).x;
            	int waitery = model.getAgPos(0).y;
            	for(int i = 0; i < tables.size(); ++i)
            	{
            		Table currentTable = tables.get(i);
            		int tablex = currentTable.getX();
            		int tabley = currentTable.getY();
            		if(tabley == waitery && ((waiterx - tablex) == 1 || (waiterx - tablex) == -1) && currentTable.getTaken())
            		{
            			Customers currentCu = currentTable.getCustomers();
            			if(currentCu.getStatus() == "Taken")
            			{
            				waiter.OrderToTake(currentCu.GetPreferences(),tablex,tabley);
            				currentCu.Ordering();
            				addPercept("waiter",Literal.parseLiteral("order(" + tablex + "," + tabley + ")"));
            				break;
            			}
            		}
            	}
            }
            else if(action.getFunctor().equals("putOrder")) {
            	Order toPut = waiter.GetOrderToTake();
            	Orders.add(toPut);
            	int ordx = toPut.getFromX();
            	int ordy = toPut.getFromY();
            	removePercept("waiter",Literal.parseLiteral("order(" + ordx + "," + ordy + ")"));
            }
            else if(action.getFunctor().equals("serveOrder")) {
            	Order toServe = waiter.GetOrderToServe();
            	int posx = toServe.getFromX();
            	int posy = toServe.getFromY();
            	for(int i = 0; i < tables.size(); ++i)
            	{
            		Table currentTable = tables.get(i);
            		if(currentTable.getX() == posx && currentTable.getY() == posy && currentTable.getTaken())
            		{
            			currentTable.getCustomers().Served();
            			removePercept("waiter",Literal.parseLiteral("ordertoserve(" + posx + "," + posy + ",success)"));
            			break;
            		}
            	}
            }
            else if(action.getFunctor().equals("sendpplhome")) {
            	Order failedOrder = waiter.GetOrderToServe();
            	int posx = failedOrder.getFromX();
            	int posy = failedOrder.getFromY();
            	for(int i = 0; i < tables.size(); ++i)
            	{
            		Table currentTable = tables.get(i);
            		if(currentTable.getX() == posx && currentTable.getY() == posy && currentTable.getTaken())
            		{
            			currentTable.getCustomers().Finished();
            			removePercept("waiter",Literal.parseLiteral("ordertoserve(" + posx + "," + posy + ",failure)"));
            			break;
            		}
            	}
            }
            else if(action.getFunctor().equals("findtable")) { host.findTable(tables,Waiting); }
            else if(action.getFunctor().equals("leadToTable")) {host.LeadToTable(tables,Waiting,model,view); }
            else if(action.getFunctor().equals("goBack")) {host.GetBack(model); }
            else if(action.getFunctor().equals("checkResources")) {chef.CheckResources(Storage,Orders); }
            else if(action.getFunctor().equals("prepare")) {chef.Prepare(Storage,Orders,model.getAgPos(2).x,Ready); }
            else if(action.getFunctor().equals("moveTo")) {chef.moveTo(model,Machines); }
            else if(action.getFunctor().equals("pickUp")) {chef.pickUp(Storage,model,view,Machines); }
            else if(action.getFunctor().equals("putin")) {chef.putin(Machines); }
            else if(action.getFunctor().equals("making")) {chef.make(Machines,model,view); }
            else if(action.getFunctor().equals("serve")) {chef.serve(Machines,model,view);}
            else if(action.getFunctor().equals("chopping")) {chef.Chopping(Machines);}
            else if(action.getFunctor().equals("problem")) {chef.Problem(Storage,Orders,Ready);}
            else return false;
        } catch (Exception e) {}
        updatePercepts();
        try {
            Thread.sleep(500);
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
    }

    void updatePercepts() {
        //clearAllPercepts();

        clearPercepts("host");
        clearPercepts("chef");
        
        int waiterx = model.getAgPos(0).x;
        int waitery = model.getAgPos(0).y;
        Boolean foundOne = false;
        for(int i = 0; i < tables.size(); ++i)
        {
        	Table currentTable = tables.get(i);
        	int currentx = currentTable.getX();
        	int currenty = currentTable.getY();
        	if((currentx == waiterx + 1 || currentx == waiterx - 1) && waitery == currenty && !foundOne && currentTable.getTaken())
        	{
        		Customers currentCustomer = currentTable.getCustomers();
        		if(currentCustomer.getStatus() == "Taken")
        		{
        			addPercept("waiter",Literal.parseLiteral("newcustomer"));
        			foundOne = true;
        		}
        	}
        	if(currentTable.getTaken())
        	{
        		if(currentTable.getCustomers().FinishedYet())
        		{
        			currentTable.setCostumers(null);
        			currentTable.setTaken(false);
        			view.update(currentx, currenty);
        		}
        	}
        }
        if(!foundOne)removePercept("waiter",Literal.parseLiteral("newcustomer"));
        removePercept("waiter",Literal.parseLiteral("pos(waiter," + lastWaiterPos[0] + "," + lastWaiterPos[1] + ")"));
        addPercept("waiter",Literal.parseLiteral("pos(waiter," + waiterx + "," + waitery + ")"));
        lastWaiterPos[0] = waiterx;
        lastWaiterPos[1] = waitery;
        
        
        //addPercept("waiter",Literal.parseLiteral("pos(host," + model.getAgPos(1).x + "," + model.getAgPos(1).y + ")"));
        addPercept("host",Literal.parseLiteral("findtable("+ Waiting.size() +","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+","+FreeTables()+")"));
        addPercept("host",Literal.parseLiteral("getTable("+ host.getIsFollowed() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));
        addPercept("host",Literal.parseLiteral("getBack("+ host.getBack() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));
        addPercept("chef",Literal.parseLiteral("stateMachine("+Orders.size()+","+Machines.get(1).getWorking()+","+Machines.get(0).getWorking()+","+ model.getAgPos(2).x+","+chef.TargetX+","
        +chef.getCheckResources()+","+chef.getProblem()+","+chef.getPrepare()+","+chef.getMoveTo()+","+chef.getChopped()+","+chef.getServe()+","+chef.getMake()+")"));
    }
    public Integer FreeTables()
    {
    	Integer count=0;
    	for(int i=0;i<tables.size();i++) if(!tables.get(i).getTaken()) count++;
    	return count;
    }

    

    
}
