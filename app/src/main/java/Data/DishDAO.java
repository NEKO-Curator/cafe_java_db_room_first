package Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Model.Dish;

@Dao
public interface DishDAO {

    @Insert
    public long addDish(Dish Dish);

    @Update
    public void updateDish(Dish Dish);

    @Delete
    public void deleteDish(Dish Dish);

    @Query("select * from Dishes")
    public List<Dish> getAllDishes();

    @Query("select * from Dishes where Dish_id ==:DishId ")
    public  Dish getDish(long DishId);

}
