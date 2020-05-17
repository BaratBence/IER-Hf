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
    public Boolean a=true;
    @Override
    public void init(String[] args) {
        model = new RestaurantModel(RestaurantLength,RestaurantSize,WALL,TABLE,INGREDIENT,MACHINE,OBSTICLE,tables,Storage,Machines);
        view  = new RestaurantView(model,tables,Storage,Machines);
        Waiting.add(new Customers(100,100,4));
        Waiting.add(new Customers(100,100,4));
        Order tmp=new Order();
        //ArrayList<String> a= new ArrayList<String>();
        //a.add("MainB");
        //a.add("MainB");
        //ArrayList<Integer> b=new ArrayList<Integer>();
        //b.add(1);
        //b.add(1);
        //tmp.setOrders(a, b);
        tmp.setOrder("MainB", 1);
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
            else if(action.getFunctor().equals("prepare")) {chef.Prepare(Storage); }
            else if(action.getFunctor().equals("moveTo")) {chef.moveTo(model,Machines); }
            else if(action.getFunctor().equals("pickUp")) {chef.pickUp(Storage,model,view,Machines); }
            else if(action.getFunctor().equals("putin")) {chef.putin(Machines); }
            else if(action.getFunctor().equals("cooking")) {chef.make(Machines,model,view); }
            else if(action.getFunctor().equals("baking")) {chef.make(Machines,model,view); }
            else if(action.getFunctor().equals("serve")) {chef.serve(Machines,model,view);}
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

        addPercept("waiter",Literal.parseLiteral("pos(waiter," +model.getAgPos(0).x + "," + model.getAgPos(0).y + ")"));
        addPercept("waiter",Literal.parseLiteral("pos(host," + model.getAgPos(1).x + "," + model.getAgPos(1).y + ")"));
        addPercept("host",Literal.parseLiteral("findtable("+ Waiting.size() +","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+")"));
        addPercept("host",Literal.parseLiteral("getTable("+ host.getIsFollowed() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));
        addPercept("host",Literal.parseLiteral("getBack("+ host.getBack() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));
        addPercept("chef",Literal.parseLiteral("chefStatusTargetXCarried("+chef.status +","+chef.a+","+chef.b+","+chef.c+","+chef.d+")"));
        addPercept("chef",Literal.parseLiteral("chopping("+ chef.getCarrying().getChopped()+")"));
        addPercept("chef",Literal.parseLiteral("stateMachine("+Machines.get(1).getStatus()+","+Machines.get(0).getStatus()+","+ model.getAgPos(2).x+","+chef.TargetX+","+chef.getCheckResources()+","+chef.getPrepare()+","+chef.getMoveTo()+","+chef.getServe()+","+chef.getMake()+")"));
    }

    

    
}
