package lt.andro.chear.oms;

import io.realm.RealmObject;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:51
 */
public class Order extends RealmObject {
    private int id;
    private String dishName;
    private String specialNote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }
}
