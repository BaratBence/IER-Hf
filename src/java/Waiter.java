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
}