package lt.andro.chear.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author Vilius Kraujutis
 * @since 2014-11-01 16:17
 */
public class BaseDialog extends DialogFragment {
    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    public String getTagName() {
        return ((Object) this).getClass().getSimpleName();
    }
}
