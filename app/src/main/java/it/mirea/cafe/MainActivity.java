package it.mirea.cafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Data.DishesAppDatabase;
import Model.Dish;

public class MainActivity extends AppCompatActivity {

    private DishesAdapter DishesAdapter;
    private ArrayList<Dish> DishArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
//    private DatabaseHandler dbHandler;
    private DishesAppDatabase DishesAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
//        dbHandler = new DatabaseHandler(this);
        DishesAppDatabase = Room.databaseBuilder(getApplicationContext(),
                DishesAppDatabase.class, "DishesDB")
                .build();

        new GetAllDishesAsyncTask().execute();

        DishesAdapter = new DishesAdapter(this, DishArrayList, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(DishesAdapter);


        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditDishes(false, null, -1);
            }


        });


    }

    public void addAndEditDishes(final boolean isUpdate, final Dish Dish, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_add_dish, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView newDishTitle = view.findViewById(R.id.newDishTitle);
        final EditText nameEditText = view.findViewById(R.id.nameEditText);
        final EditText priceEditText = view.findViewById(R.id.priceEditText);

        newDishTitle.setText(!isUpdate ? "Add Dish" : "Edit Dish");

        if (isUpdate && Dish != null) {
            nameEditText.setText(Dish.getName());
            priceEditText.setText(Dish.getPrice());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(isUpdate ? "Delete" : "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                if (isUpdate) {

                                    deleteDish(Dish, position);
                                } else {

                                    dialogBox.cancel();

                                }

                            }
                        });


        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter Dish name!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(priceEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter Dish price!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }


                if (isUpdate && Dish != null) {

                    updateDish(nameEditText.getText().toString(), priceEditText.getText().toString(), position);
                } else {

                    createDish(nameEditText.getText().toString(), priceEditText.getText().toString());
                }
            }
        });
    }

    private void deleteDish(Dish Dish, int position) {

        DishArrayList.remove(position);

        new DeleteDishAsyncTask().execute(Dish);

    }

    private void updateDish(String name, String price, int position) {

        Dish Dish = DishArrayList.get(position);

        Dish.setName(name);
        Dish.setPrice(price);

        new UpdateDishAsyncTask().execute(Dish);

        DishArrayList.set(position, Dish);


    }

    private void createDish(String name, String price) {

        new CreateDishAsyncTask().execute(new Dish(0, name, price));

    }

    private class GetAllDishesAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            DishArrayList.addAll(DishesAppDatabase.getDishDAO().getAllDishes());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DishesAdapter.notifyDataSetChanged();
        }
    }


    private class CreateDishAsyncTask extends AsyncTask<Dish, Void, Void> {


        @Override
        protected Void doInBackground(Dish... Dishes) {

            long id = DishesAppDatabase.getDishDAO().addDish(
                    Dishes[0]
            );


            Dish Dish = DishesAppDatabase.getDishDAO().getDish(id);

            if (Dish != null) {

                DishArrayList.add(0, Dish);


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DishesAdapter.notifyDataSetChanged();
        }
    }

    private class UpdateDishAsyncTask extends AsyncTask<Dish, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DishesAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Dish... Dishes) {

            DishesAppDatabase.getDishDAO().updateDish(Dishes[0]);

            return null;
        }
    }

    private class DeleteDishAsyncTask extends AsyncTask<Dish, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DishesAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Dish... Dishes) {

            DishesAppDatabase.getDishDAO().deleteDish(Dishes[0]);


            return null;
        }
    }


}














