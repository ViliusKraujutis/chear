package lt.andro.chear.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lt.andro.chear.R;
import lt.andro.chear.oms.Dish;
import lt.andro.chear.oms.Order;

/**
 * Created by alon on 02/11/14.
 */
public class DishAdapter extends ArrayAdapter<Dish>{


    public DishAdapter(Context c, ArrayList<Dish> orders) {
        super(c,0,orders);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Dish dish = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dish_item, parent, false);
        }
        TextView dishNameTV = (TextView) convertView.findViewById(R.id.dish_item_name);
        ImageView dishImageView = (ImageView) convertView.findViewById(R.id.dish_item_image);

        dishNameTV.setText(dish.getDishName());
        dishImageView.setImageResource(dish.getImageResId());

        return convertView;
    }
}
