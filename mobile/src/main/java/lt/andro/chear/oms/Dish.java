package lt.andro.chear.oms;

/**
 * @author Alon
 * @since 02/11/14
 */
public class Dish {
    private final int imageResId;
    private String dishName;

    public Dish(String dishName, int imageResId) {
        this.dishName = dishName;
        this.imageResId = imageResId;
    }

    public String getDishName() {
        return dishName;
    }

    public int getImageResId() {
        return imageResId;
    }
}
