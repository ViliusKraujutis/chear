package lt.andro.chear.oms;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:51
 */
public class Order {
    public final String dishName;
    public final String specialNote;
    public final int id;

    public Order(String dishName, String specialNote, int id) {
        this.dishName = dishName;
        this.specialNote = specialNote;
        this.id = id;
    }
}
