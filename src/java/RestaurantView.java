import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import jason.environment.grid.GridWorldView;

class RestaurantView extends GridWorldView {
		private ArrayList<Table> tables=new ArrayList<Table>();
		private ArrayList<StorageBox> Storage=new ArrayList<StorageBox>();
		private ArrayList<Machine> Machines=new ArrayList<Machine>();
        public RestaurantView(RestaurantModel model,ArrayList<Table> tables,ArrayList<StorageBox> Storage,ArrayList<Machine> Machines) {
            super(model, "Restaurant", 600);
            this.tables=tables;
            this.Storage=Storage;
            this.Machines=Machines;
            defaultFont = new Font("Arial", Font.BOLD, 9);
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
            case RestaurantEnv.INGREDIENT:
            	drawIngredient(g,x,y);
            	break;
            case RestaurantEnv.MACHINE:
            	drawMachine(g,x,y);
            	break;
            case RestaurantEnv.OBSTACLE:
            	drawObst(g,x,y);
            	break;
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id) {
            String label;
            c = Color.blue;
            super.drawAgent(g, x, y, c, 1);
            if (id == 0 || id==3) label="Waiter" ;
            else if(id == 1) label="Host";
            else label="Chef";
            g.setColor(Color.white);
            super.drawString(g, x, y, defaultFont, label);
        }
        public void drawWall(Graphics g, int x, int y) {
        	super.drawObstacle(g, x, y);
        }
        public void drawTable(Graphics g,int x, int y) {
        	super.drawObstacle(g, x, y);
        	Integer id=0; String status=""; Customers cost;
        	for(int i=0;i<tables.size();i++) 
        		if(tables.get(i).getX() == x && tables.get(i).getY() == y) 
        		{ 
        			id=i+1; 
        			cost=tables.get(i).getCostumers();
        			if(cost==null) status="free";
        			else status = cost.getStatus();
        		}
            g.setColor(Color.RED);
            drawString(g, x, y, defaultFont, "Table"+ id.toString()+ " " + status);
        }
        public void drawIngredient(Graphics g,int x, int y) {
        	super.drawObstacle(g, x, y);
        	g.setColor(Color.RED);
        	for(int i=0;i<Storage.size();i++) 
        		if(Storage.get(i).getX() == x && Storage.get(i).getY() == y) drawString(g, x, y, defaultFont,Storage.get(i).getIngredient().getName() + " " + Storage.get(i).getIngredient().getAmount().toString());
        }
        public void drawMachine(Graphics g,int x, int y) {
        	super.drawObstacle(g, x, y);
        	g.setColor(Color.RED);
        	for(int i=0;i<Machines.size();i++) 
        		if(Machines.get(i).getX() == x && Machines.get(i).getY() == y) drawString(g, x, y, defaultFont,Machines.get(i).getname() + " " + Machines.get(i).getStatus());
        }
        public void drawObst(Graphics g,int x, int y) {
        	super.drawObstacle(g, x, y);
        	g.setColor(Color.RED);
        	if(x == 7 && y ==2) drawString(g, x, y, defaultFont,"Orders");
        	else drawString(g, x, y, defaultFont,"table");
        }
    }