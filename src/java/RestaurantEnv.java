import jason.asSyntax.*;
import jason.environment.Environment;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RestaurantEnv extends Environment {

    public static final Integer RestaurantSize = 13;
    public static final Integer RestaurantLength = 10;
    public static final int WALL = 8;
    public static final int TABLE = 16;
    
    static Logger logger = Logger.getLogger(RestaurantEnv.class.getName());
    public ArrayList<Table> tables =new ArrayList<Table>();
    public ArrayList<Costumers> Waiting=new ArrayList<Costumers>();
    
    private RestaurantModel model;
    private RestaurantView  view;
    private Host host=new Host();
    private Waiter waiter=new Waiter();

    @Override
    public void init(String[] args) {
        model = new RestaurantModel(RestaurantLength,RestaurantSize,WALL,TABLE,tables,Waiting);
        view  = new RestaurantView(model,tables);
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
        addPercept("host",Literal.parseLiteral("findtable("+ Waiting.size() +","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+")"));
        addPercept("host",Literal.parseLiteral("getTable("+ host.getIsFollowed() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));
        addPercept("host",Literal.parseLiteral("getBack("+ host.getBack() + ","+ model.getAgPos(1).x + "," + model.getAgPos(1).y+ ")"));

    }

    

    
}
