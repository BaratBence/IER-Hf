import java.util.ArrayList;

import jason.environment.grid.GridWorldModel;

public class RestaurantModel extends GridWorldModel {
    	public RestaurantModel(Integer RestaurantLength,Integer RestaurantSize,int WALL,int TABLE,ArrayList<Table> tables,ArrayList<Costumers> Waiting) {
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
            	add(TABLE,1,i); tables.add(new Table(1,i,4));
                add(TABLE,3,i); tables.add(new Table(3,i,4));
                add(TABLE,5,i); tables.add(new Table(5,i,4));
                add(TABLE,7,i); tables.add(new Table(7,i,4));
            }
            Waiting.add(new Costumers(100,100,4));
            Waiting.add(new Costumers(100,100,4));
        }       
}