import java.util.ArrayList;

import jason.environment.grid.Location;

public class Waiter
{
	 public void nextSlot(RestaurantModel model) throws Exception {
         Location r1 = model.getAgPos(0);
         r1.x++;
         if (r1.x == model.getWidth()) {
             r1.x = 0;
             r1.y++;
         }
         if (r1.y == model.getHeight()) {
             return;
         }
         model.setAgPos(0, r1);
         model.setAgPos(1, model.getAgPos(1)); 
     }
	 
	 public void moveTowards(RestaurantModel model, ArrayList<Wall> Walls, int x, int y)
	 {
		 Location waiter = model.getAgPos(0);
		 
		 //if(waiter.x == x && waiter.y == y)return;
		 
		 Boolean up = false;
		 Boolean right = false;
		 Boolean stayX = false;
		 Boolean stayY = false;
		 Boolean upBlock = false;
		 Boolean downBlock = false;
		 Boolean leftBlock = false;
		 Boolean rightBlock = false;
		 if(waiter.y > y) up = true;
		 if(waiter.x < x) right = true;
		 if(waiter.y == y) stayY = true;
		 if(waiter.x == x) stayX = true;
		 
		 for(int i = 0; i < Walls.size(); ++i)
		 {
			 Wall current = Walls.get(i);
			 if(current.x == waiter.x + 1 && current.y == waiter.y) rightBlock = true;
			 if(current.x == waiter.x - 1 && current.y == waiter.y) leftBlock = true;
			 if(current.x == waiter.x && current.y == waiter.y + 1) downBlock = true;
			 if(current.x == waiter.x && current.y == waiter.y - 1) upBlock = true;
		 }
		 
		 if(up)
		 {
			 if(!upBlock && !stayY) model.setAgPos(0,waiter.x,waiter.y-1);
			 else
			 {
				 if(right && !rightBlock && !stayX) model.setAgPos(0,waiter.x+1,waiter.y);
				 else if(!right && !leftBlock && !stayX) model.setAgPos(0, waiter.x-1, waiter.y);
			 }
		 }
		 else
		 {
			 if(!downBlock && !stayY) model.setAgPos(0,waiter.x,waiter.y+1);
			 else
			 {
				 if(right && !rightBlock && !stayX) model.setAgPos(0,waiter.x+1,waiter.y);
				 else if(!right && !leftBlock && !stayX) model.setAgPos(0, waiter.x-1, waiter.y);
			 }
		 }
	 }
}