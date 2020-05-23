import java.util.ArrayList;

import Kitchen.Ingredient;
import jason.environment.grid.GridWorldModel;

public class RestaurantModel extends GridWorldModel {
    	public RestaurantModel(Integer RestaurantLength,Integer RestaurantSize,int WALL,int TABLE,int INGREDIENT,int MACHINE,int OBSTACLE,ArrayList<Table> tables,ArrayList<StorageBox> Storage,ArrayList<Machine> Machines, ArrayList<Wall> Walls) {
            super(RestaurantLength,RestaurantSize, 3);
            setAgPos(0, 2, 5);
            setAgPos(1, 4,12);
            setAgPos(2,6,2);
            //setAgPos(3,8,5);
            for(int i=0;i<RestaurantSize;i++)
            	for(int j=0; j<RestaurantLength;j++)
            	{
            		if((i==0 || i==RestaurantSize-1 || j==0 || j==RestaurantLength-1) && !(i==12 && j==4))
            		{
            			add(WALL, j, i);
            			Walls.add(new Wall(j,i));
            		}

            	}
            for(int i=0;i<RestaurantLength-2;i++)
            {
            	add(WALL,i,4);
            	Walls.add(new Wall(i,4));
            }
            add(WALL,7,3);
            Walls.add(new Wall(7,3));
            for(int i=6;i<11;i+=2)
            {
            	add(TABLE,1,i); tables.add(new Table(1,i,2));
                add(TABLE,3,i); tables.add(new Table(3,i,4));
                add(TABLE,5,i); tables.add(new Table(5,i,4));
                add(TABLE,7,i); tables.add(new Table(7,i,7));
                Walls.add(new Wall(1,i));
                Walls.add(new Wall(3,i));
                Walls.add(new Wall(5,i));
                Walls.add(new Wall(7,i));
            }
            add(INGREDIENT,1,3);
            add(INGREDIENT,2,3);
            add(INGREDIENT,3,3);
            add(INGREDIENT,4,3);
            for(int i=1;i<5;i++)
            {
            	add(OBSTACLE,i,1);
            	Walls.add(new Wall(i,1));
            }
            add(OBSTACLE,7,2);
            add(MACHINE,5,3);
            add(MACHINE,6,3);
            Walls.add(new Wall(7,2));
            Walls.add(new Wall(5,3));
            Walls.add(new Wall(6,3));
            Machines.add(new Machine("Oven",5,3));
            Machines.add(new Machine("Stove",6,3));
            Storage.add(new StorageBox(1,3,new Ingredient("A",33,false)));
            Storage.add(new StorageBox(2,3,new Ingredient("B",33,false)));
            Storage.add(new StorageBox(3,3,new Ingredient("C",29,false)));
            Storage.add(new StorageBox(4,3,new Ingredient("D",24,false)));
            Walls.add(new Wall(1,3));
            Walls.add(new Wall(2,3));
            Walls.add(new Wall(3,3));
            Walls.add(new Wall(4,3));
        }       
}