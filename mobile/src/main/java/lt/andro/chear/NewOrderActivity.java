package lt.andro.chear;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import lt.andro.chear.adapters.DishAdapter;
import lt.andro.chear.dialog.Dialogs;
import lt.andro.chear.dialog.NewOrderDialog;
import lt.andro.chear.oms.Dish;
import lt.andro.chear.oms.Order;
import lt.andro.chear.oms.OrderManagementSystem;
import lt.andro.chear.util.WearUtils;


public class NewOrderActivity extends FragmentActivity {

    private static final String TAG = NewOrderActivity.class.getCanonicalName();
    private static final int NOT_AVAILABLE = -1;
   // @InjectView(R.id.new_order_menu)
    //ListView menuListView;
    @InjectView(R.id.gridview_order_menu)
    GridView menuGridView;
    private DishAdapter adapter;
    private ArrayList<Dish> mDishes = new ArrayList<Dish>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        ButterKnife.inject(this);
        populateDishesData();
        adapter = new DishAdapter(this,mDishes);
        menuGridView.setAdapter(adapter);
        Intent intent = getIntent();

        handleReplyIntent(intent);
    }

    public void populateDishesData(){
        mDishes.add(new Dish("pasta", "none", R.drawable.ic_launcher));
        mDishes.add(new Dish("pasta", "none", R.drawable.ic_launcher));
        mDishes.add(new Dish("pasta", "none", R.drawable.ic_launcher));
        mDishes.add(new Dish("pasta", "none", R.drawable.ic_launcher));

    }

    @OnItemClick(R.id.gridview_order_menu)
    protected void onMenuItemClick(int position) {
        Dish dish = adapter.getItem(position);
        Dialogs.show(NewOrderDialog.newInstance(dish), this);
        Log.d(TAG, "new Order: " + dish.getDishName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void handleReplyIntent(Intent intent) {
        CharSequence message = WearUtils.getRejectionMessage(intent);
        if (!TextUtils.isEmpty(message) && intent.hasExtra(WearUtils.EXTRA_WEAR_ORDER_ID)) {
            int orderId = intent.getIntExtra(WearUtils.EXTRA_WEAR_ORDER_ID, NOT_AVAILABLE);
            intent.removeExtra(WearUtils.EXTRA_WEAR_ORDER_ID);
            showRejectionMessage(orderId, message.toString());
        }
    }

    private void showRejectionMessage(int orderId, String message) {
        Order order = OrderManagementSystem.getInstance().getOrder(orderId);
        if (order == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(order.dishName);
        if (order.hasSpecialNote()) {
            message += "\n\nSpecial notes were: " + order.specialNote;
        }
        builder.setMessage(message);
        builder.show();
    }
}
