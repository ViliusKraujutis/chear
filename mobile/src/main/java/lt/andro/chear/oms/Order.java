package lt.andro.chear.oms;

import android.support.annotation.IntDef;

import io.realm.RealmObject;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:51
 */
public class Order extends RealmObject {
    private int id;
    private String dishName;
    private String specialNote;
    @Status.STATUS private int status;


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

    @Status.STATUS public int getStatus() {
        return status;
    }

    public void setStatus(@Status.STATUS int status) {
        this.status = status;
    }

    public static class Status {
        public static final int PENDING = 0;
        public static final int COMPLETED = 1;
        public static final int REJECTED = 2;

        @IntDef({PENDING, COMPLETED, REJECTED})
        public @interface STATUS {
        }
    }
}
