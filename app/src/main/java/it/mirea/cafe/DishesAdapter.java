package it.mirea.cafe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Model.Dish;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Dish> Dishes;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView priceTextView;


        public MyViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            priceTextView = view.findViewById(R.id.priceTextView);

        }
    }


    public DishesAdapter(Context context, ArrayList<Dish> Dishes, MainActivity mainActivity) {
        this.context = context;
        this.Dishes = Dishes;
        this.mainActivity = mainActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_list_item, parent, false);

        return new MyViewHolder(itemView);

    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        final Dish Dish = Dishes.get(position);

        holder.nameTextView.setText(Dish.getName());
        holder.priceTextView.setText(Dish.getPrice() + " р");

        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                mainActivity.addAndEditDishes(true, Dish, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Dishes.size();
    }


}
