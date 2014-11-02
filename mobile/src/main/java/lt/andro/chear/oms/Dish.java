package lt.andro.chear.oms;

/**
 * Created by alon on 02/11/14.
 */
public class Dish {
    private final int imageResId;
    private String dishName;
    private String specialNote;

    public Dish(String dishName, String specialNote, int imageResId) {
        this.dishName = dishName;
        this.specialNote = specialNote;
        this.imageResId = imageResId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getImageResId() {
        return imageResId;
    }
}
