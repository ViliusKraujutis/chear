package lt.andro.chear.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.InjectView;
import lt.andro.chear.R;
import lt.andro.chear.oms.Dish;

/**
 * @author Alon
 * @since 02/11/14
 */
public class DishAdapter extends ArrayAdapter<Dish> {
    public DishAdapter(Context c, ArrayList<Dish> orders) {
        super(c, 0, orders);
    }

    /**
     * Create a new ImageView for each item referenced by the Adapter
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        Dish dish = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dish_item, parent, false);
        }
        ViewHolder viewHolder = BaseViewHolder.getViewHolder(convertView, ViewHolder.class);

        viewHolder.name.setText(dish.getDishName());
        viewHolder.image.setImageResource(dish.getImageResId());

        return convertView;
    }

    public static class ViewHolder extends BaseViewHolder {
        @InjectView(R.id.dish_item_name)
        public TextView name;
        @InjectView(R.id.dish_item_image)
        public ImageView image;
    }
}
