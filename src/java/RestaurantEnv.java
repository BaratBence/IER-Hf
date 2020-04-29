import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Logger;

public class RestaurantEnv extends Environment {

    public static final int RestaurantSize = 13;
    public static final int RestaurantLength = 10;
    public static final int GARB  = 32; 
    public static final int WALL = 8;
    public static final int TABLE = 16;
    public static final Color BROWN = new Color(102,15,0);
    
    public static final Term ns = Literal.parseLiteral("next(slot)");


    static Logger logger = Logger.getLogger(RestaurantEnv.class.getName());
    public ArrayList<Table> tables =new ArrayList<Table>();
    
    private MarsModel model;
    private MarsView  view;

    @Override
    public void init(String[] args) {
        model = new MarsModel();
        view  = new MarsView(model);
        model.setView(view);
        updatePercepts();
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        try {
            if (action.equals(ns)) { model.nextSlot(); } 
            else if (action.getFunctor().equals("move_towards")) {
            	for(int i=0;i<tables.size();i++) 
            		if(!tables.get(i).getTaken() && tables.get(i).getSeats()>2)
            		{
            			 int x = tables.get(i).getX();
                         int y = tables.get(i).getY();
                         model.moveTowards(x,y);
            		}
            } else return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        updatePercepts();

        try {
            Thread.sleep(500);
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
    }

    void updatePercepts() {
        clearPercepts();

        Location r1Loc = model.getAgPos(0);
        Location r2Loc = model.getAgPos(1);

        Literal pos1 = Literal.parseLiteral("pos(r1," + r1Loc.x + "," + r1Loc.y + ")");
        Literal pos2 = Literal.parseLiteral("pos(r2," + r2Loc.x + "," + r2Loc.y + ")");

        addPercept(pos1);
        addPercept(pos2);

    }

    class MarsModel extends GridWorldModel {
        private MarsModel() {
            super(RestaurantLength,RestaurantSize, 2);
            setAgPos(0, 1, 2);
            setAgPos(1, 4,12);
            for(int i=0;i<RestaurantSize;i++)
            	for(int j=0; j<RestaurantLength;j++)
            	{
            		if((i==0 || i==RestaurantSize-1 || j==0 || j==RestaurantLength-1) && !(i==12 && j==4)) add(WALL, j, i);

            	}
            for(int i=0;i<RestaurantLength-2;i++) add(WALL,i,4);
            for(int i=6;i<11;i+=2)
            {
            	add(TABLE,2,i); tables.add(new Table(2,i,4));
                add(TABLE,4,i); tables.add(new Table(4,i,4));
                add(TABLE,5,i); tables.add(new Table(5,i,4));
                add(TABLE,7,i); tables.add(new Table(7,i,4));
            }
        }

        void nextSlot() throws Exception {
            Location r1 = getAgPos(0);
            r1.x++;
            if (r1.x == getWidth()) {
                r1.x = 0;
                r1.y++;
            }
            // finished searching the whole grid
            if (r1.y == getHeight()) {
                return;
            }
            setAgPos(0, r1);
            setAgPos(1, getAgPos(1)); 
        }

        void moveTowards(int x, int y) throws Exception {
            Location r1 = getAgPos(0);
            if (r1.x < x) r1.x++;
            else if (r1.x > x) r1.x--;
            if (r1.y < y) r1.y++;
            else if (r1.y > y) r1.y--;
            setAgPos(0, r1);
        }
    }

    class MarsView extends GridWorldView {

        public MarsView(MarsModel model) {
            super(model, "Mars World", 600);
            defaultFont = new Font("Arial", Font.BOLD, 10);
            setVisible(true);
        }
       
        @Override
        public void draw(Graphics g, int x, int y, int object) {
        	switch (object) {
            case RestaurantEnv.WALL:
            	drawWall(g,x,y);
            	break;
            case RestaurantEnv.TABLE:
            	drawTable(g,x,y);
            	break;
        	case RestaurantEnv.GARB:
        		drawGarb(g, x, y);
        		break;
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id) {
            String label = "C"+(id+1);
            c = Color.blue;
            super.drawAgent(g, x, y, c, -1);
            if (id == 0) g.setColor(Color.black);
            else g.setColor(Color.white);
            super.drawString(g, x, y, defaultFont, label);
        }
        public void drawGarb(Graphics g, int x, int y) {
        	super.drawObstacle(g, x, y);
            g.setColor(Color.white);
            drawString(g, x, y, defaultFont, "G");
        }
        public void drawWall(Graphics g, int x, int y) {
        	super.drawObstacle(g, x, y);
        }
        public void drawTable(Graphics g,int x, int y) {
        	super.drawObstacle(g, x, y);
        	Integer id=0; String status="";
        	for(int i=0;i<tables.size();i++) if(tables.get(i).getX() == x && tables.get(i).getY() == y) { id=i+1; status = tables.get(i).getCurrentStatus();}
            g.setColor(Color.RED);
            drawString(g, x, y, defaultFont, "Table"+ id.toString()+ " " + status);
        }

    }
}
