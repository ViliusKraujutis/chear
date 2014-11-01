package lt.andro.chear;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import lt.andro.chear.dialog.Dialogs;
import lt.andro.chear.dialog.NewOrderDialog;


public class NewOrderActivity extends FragmentActivity {

    private static final String TAG = NewOrderActivity.class.getCanonicalName();
    @InjectView(R.id.new_order_menu)
    ListView menuListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        ButterKnife.inject(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.restaurant_menu_items));
        menuListView.setAdapter(adapter);
    }

    @OnItemClick(R.id.new_order_menu)
    protected void onMenuItemClick(int position) {
        String dishName = adapter.getItem(position);
        Dialogs.show(NewOrderDialog.newInstance(dishName), this);
        Log.d(TAG, "new Order: " + dishName);
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
}
