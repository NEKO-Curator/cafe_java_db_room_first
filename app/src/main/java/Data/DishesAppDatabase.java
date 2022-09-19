package Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import Model.Dish;

@Database(entities = {Dish.class}, version = 1)
public abstract class DishesAppDatabase extends RoomDatabase {

    public abstract DishDAO getDishDAO();

}
