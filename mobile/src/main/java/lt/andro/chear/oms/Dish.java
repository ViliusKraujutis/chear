package lt.andro.chear.oms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static lt.andro.chear.MainApplication.context;

/**
 * @author Alon
 * @since 02/11/14
 */
public class Dish {
    private final int imageResId;
    private String dishName;
    private final Bitmap dishImage;

    public Dish(String dishName, int imageResId) {
        this.dishName = dishName;
        this.imageResId = imageResId;
        dishImage = BitmapFactory.decodeResource(context.getResources(), imageResId);
    }

    public String getDishName() {
        return dishName;
    }

    public int getImageResId() {
        return imageResId;
    }

    public Bitmap getDishImage() {
        return dishImage;
    }
}
