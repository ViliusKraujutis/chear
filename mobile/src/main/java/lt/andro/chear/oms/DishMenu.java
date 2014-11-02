package lt.andro.chear.oms;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import lt.andro.chear.R;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-02 11:06
 */
public class DishMenu {
    private static DishMenu instance;
    private ArrayList<Dish> dishes = new ArrayList<Dish>();

    public static DishMenu getInstance() {
        if (instance == null) {
            instance = new DishMenu();
        }
        return instance;
    }

    public DishMenu() {
        dishes.add(new Dish("Nutcutlet", R.drawable.dish_nutcutlet));
        dishes.add(new Dish("Pasta", R.drawable.dish_pasta));
        dishes.add(new Dish("Pizza Pepperoni", R.drawable.dish_pepperoni_pizza));
        dishes.add(new Dish("Pizza Rozmarinpesto", R.drawable.dish_pizza_med_rosmarinpesto));
        dishes.add(new Dish("Pizza Nepolitana", R.drawable.dish_nepolitana));
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    @Nullable
    public Dish getDish(String dishName) {
        for (Dish dish : dishes) {
            if (dish.getDishName().equalsIgnoreCase(dishName)) {
                return dish;
            }
        }
        return null;
    }
}
