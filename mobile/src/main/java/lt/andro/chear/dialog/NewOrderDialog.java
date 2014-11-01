package lt.andro.chear.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnClick;
import lt.andro.chear.R;
import lt.andro.chear.oms.OrderManagementSystem;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:18
 */
public class NewOrderDialog extends BaseDialog {
    private static final String KEY_DISH_NAME = "KEY_DISH_NAME";
    private static final String TAG = NewOrderDialog.class.getCanonicalName();
    @InjectView(R.id.new_oder_dish_name)
    TextView dishNameView;
    @InjectView(R.id.new_order_special_notes)
    EditText specialNotesView;
    @InjectView(R.id.new_oder_cancel)
    Button cancelButton;
    @InjectView(R.id.new_oder_confirm)
    Button confirmButton;

    private String dishName;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dishName = getArguments().getString(KEY_DISH_NAME);
    }

    @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_new_order, container, false);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setTitle(R.string.new_order_dialog_title);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dishNameView.setText(dishName);
    }

    @OnClick(R.id.new_oder_confirm) protected void onConfirmClick() {
        String specialNote = getSpecialNote();
        Log.d(TAG, String.format("dishName=%s, specialNote=%s", dishName, specialNote));
        OrderManagementSystem.getInstance().addNewOrder(dishName, specialNote);
        dismiss();
    }

    @OnClick(R.id.new_oder_cancel) protected void onCancelClick() {
        dismiss();
    }

    public static NewOrderDialog newInstance(String dishName) {
        Bundle args = new Bundle();
        NewOrderDialog dialog = new NewOrderDialog();
        args.putString(KEY_DISH_NAME, dishName);
        dialog.setArguments(args);
        return dialog;
    }

    public String getSpecialNote() {
        return specialNotesView.getText().toString();
    }
}
