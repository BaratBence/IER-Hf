import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import jason.environment.grid.GridWorldView;

class RestaurantView extends GridWorldView {
		private ArrayList<Table> tables=new ArrayList<Table>();
        public RestaurantView(RestaurantModel model,ArrayList<Table> tables) {
            super(model, "Mars World", 600);
            this.tables=tables;
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
            }
        }

        @Override
        public void drawAgent(Graphics g, int x, int y, Color c, int id) {
            String label = "C"+(id+1);
            c = Color.blue;
            super.drawAgent(g, x, y, c, 1);
            if (id == 0) g.setColor(Color.black);
            else g.setColor(Color.white);
            super.drawString(g, x, y, defaultFont, label);
        }
        public void drawWall(Graphics g, int x, int y) {
        	super.drawObstacle(g, x, y);
        }
        public void drawTable(Graphics g,int x, int y) {
        	super.drawObstacle(g, x, y);
        	Integer id=0; String status=""; Costumers cost;
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

    }