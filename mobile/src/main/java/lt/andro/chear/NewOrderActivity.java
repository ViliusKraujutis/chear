package lt.andro.chear;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import lt.andro.chear.adapters.DishAdapter;
import lt.andro.chear.adapters.OrdersAdapter;
import lt.andro.chear.dialog.Dialogs;
import lt.andro.chear.dialog.NewOrderDialog;
import lt.andro.chear.oms.Dish;
import lt.andro.chear.oms.DishMenu;
import lt.andro.chear.oms.Order;
import lt.andro.chear.oms.OrderManagementSystem;
import lt.andro.chear.util.NotificationUtil;

import static lt.andro.chear.MainApplication.realm;


public class NewOrderActivity extends FragmentActivity {

    private static final String TAG = NewOrderActivity.class.getCanonicalName();
    private static final int NOT_AVAILABLE = -1;
    public static final int NUM_COLUMNS = 2;

    @InjectView(R.id.gridview_order_menu)
    GridView menuGridView;

    @InjectView(R.id.gridview_pending_orders)
    GridView pendingOrdersGridView;

    private DishAdapter menuAdapter;
    private OrdersAdapter pendingOrdersAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        ButterKnife.inject(this);
        setViewPager();

        menuAdapter = new DishAdapter(this, DishMenu.getInstance().getDishes());
        menuGridView.setNumColumns(NUM_COLUMNS);
        menuGridView.setAdapter(menuAdapter);

        pendingOrdersAdapter = new OrdersAdapter(this, OrderManagementSystem.getInstance().getPendingOrders());
        pendingOrdersGridView.setNumColumns(NUM_COLUMNS);
        pendingOrdersGridView.setAdapter(pendingOrdersAdapter);

        Intent intent = getIntent();

        handleRejectionDoneIntent(intent);
    }

    private void setViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        // Set the ViewPager adapter
        WizardPagerAdapter wizardAdapter = new WizardPagerAdapter();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(wizardAdapter);
    }

    @OnItemClick(R.id.gridview_order_menu)
    protected void onMenuItemClick(int position) {
        Dish dish = menuAdapter.getItem(position);
        Dialogs.show(NewOrderDialog.newInstance(dish, new NewOrderDialog.IConfirmOrder() {
            @Override
            public void onOrderConfirmed() {
                pendingOrdersAdapter.notifyDataSetChanged();
            }
        }), this);
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


    private void handleRejectionDoneIntent(Intent intent) {
        if (intent.hasExtra(NotificationUtil.EXTRA_WEAR_ORDER_ID)) {
            CharSequence message = NotificationUtil.getRejectionMessage(intent);
            int orderId = intent.getIntExtra(NotificationUtil.EXTRA_WEAR_ORDER_ID, NOT_AVAILABLE);
            Order order = OrderManagementSystem.getInstance().getOrder(orderId);
            if (order == null) return;
            intent.removeExtra(NotificationUtil.EXTRA_WEAR_ORDER_ID);
            if (!TextUtils.isEmpty(message)) {
                showRejectionMessage(order, message.toString());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.order_completion_dialog_title)
                        .setMessage(order.getDishName() + "\n\n" + order.getSpecialNote())
                        .setPositiveButton(R.string.ok_confirmation_button, new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();

                realm.where(Order.class).equalTo("id", orderId).findAll().first().setStatus(Order.Status.COMPLETED);
            }
        }
    }

    private void showRejectionMessage(@NonNull Order order, @NonNull String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(order.getDishName());
        String specialNote = order.getSpecialNote();
        if (TextUtils.isEmpty(specialNote)) {
            message += "\n\nSpecial notes were: " + specialNote;
        }
        builder.setMessage(message);
        builder.show();
    }

    class WizardPagerAdapter extends PagerAdapter {

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.page_one;
                    break;
                case 1:
                    resId = R.id.page_two;
                    break;
            }
            return findViewById(resId);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "Menu";
            switch (position) {
                case 0:
                    title  = "Menu";
                    break;
                case 1:
                    title  = "Pending Orders";
                    break;
            }
            return title;
        }
    }

}
