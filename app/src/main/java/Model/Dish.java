package Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Dishes")
public class Dish {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Dish_id")
    private long id;

    @ColumnInfo(name = "Dish_name")
    private String name;

    @ColumnInfo(name = "Dish_price")
    private String price;

    @Ignore
    public Dish() {
    }

    public Dish(long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Ignore
    public Dish(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
