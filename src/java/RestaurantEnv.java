import jason.asSyntax.*;
import jason.environment.Environment;

import java.util.ArrayList;
import java.util.logging.Logger;

import Kitchen.Order;

public class RestaurantEnv extends Environment {

    public static final Integer RestaurantSize = 13;
    public static final Integer RestaurantLength = 10;
    public static final int WALL = 8;
    public static final int TABLE = 16;
    public static final int INGREDIENT =32;
    public static final int MACHINE = 64;
    public static final int OBSTICLE=128;
    
    static Logger logger = Logger.getLogger(RestaurantEnv.class.getName());
    public ArrayList<Table> tables =new ArrayList<Table>();
    public ArrayList<Customers> Waiting=new ArrayList<Customers>();
    public ArrayList<StorageBox> Storage=new ArrayList<StorageBox>();
    public ArrayList<Machine> Machines=new ArrayList<Machine>();
    public ArrayList<Order> Orders=new ArrayList<Order>();
    
    private RestaurantModel model;
    private RestaurantView  view;
    private Host host=new Host();
    private Waiter waiter=new Waiter();
    private Chef chef=new Chef();

    @Override
    public void init(String[] args) {
        model = new RestaurantModel(RestaurantLength,RestaurantSize,WALL,TABLE,INGREDIENT,MACHINE,OBSTICLE,tables,Storage,Machines);
        view  = new RestaurantView(model,tables,Storage,Machines);
        Waiting.add(new Customers(100,100,4));
        Waiting.add(new Customers(100,100,4));
        Order tmp=new Order();
        tmp.setOrder("SoupA",1);
        tmp.setOrder("SoupB",1);
        Orders.add(tmp);
        model.setView(view);
        updatePercepts();
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        try {
            if (action.getFunctor().equals("next")) { waiter.nextSlot(model); } 
            else if(action.getFunctor().equals("findtable")) { host.findTable(tables,Waiting); }
            else if(action.getFunctor().equals("leadToTable")) {host.LeadToTable(tables,Waiting,model,view); }
            else if(action.getFunctor().equals("goBack")) {host.GetBack(model); }
            else if(action.getFunctor().equals("checkResources")) {chef.CheckResources(Storage,Orders); }
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
        clearAllPercepts();

        addPercept(Literal.parseLiteral("pos(waiter," +model.getAgPos(0).x + "," + model.getAgPos(0).y + ")"));
        addPercept(Literal.parseLiteral("pos(host," + model.getAgPos(1).x + "," + model.getAgPos(1).y + ")"));
        addPercept(Literal.parseLiteral("pos(chef)," + model.getAgPos(2).x + "," +model.getAgPos(2).y +")"));
        addPercept("host",Literal.parseLiteral("findtable("+ Waiting.size() +","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+")"));
        addPercept("host",Literal.parseLiteral("getTable("+ host.getIsFollowed() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));
        addPercept("host",Literal.parseLiteral("getBack("+ host.getBack() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));
        addPercept("chef",Literal.parseLiteral("checkResources("+ Orders.size() +","+ model.getAgPos(2).x + "," + model.getAgPos(2).y+")"));
        addPercept("chef",Literal.parseLiteral("chefKnows("+chef.getWork() +"," + chef.getProblem()+")"));
    }

    

    
}
