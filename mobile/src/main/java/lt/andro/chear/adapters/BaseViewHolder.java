package lt.andro.chear.adapters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author Vilius Kraujutis
 * @since 2014-08-18 22:49
 */
public abstract class BaseViewHolder {
    public static <T extends BaseViewHolder> T getViewHolder(@NonNull View convertView, Class<T> tClass) {
        if (convertView.getTag() == null || !(convertView.getTag() instanceof BaseViewHolder)) {
            try {
                T viewHolder = tClass.newInstance();
                ButterKnife.inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
                return viewHolder;
            } catch (Exception e) {
                Log.e(tClass.getCanonicalName(), "Could not inject viewholder", e);
                throw new RuntimeException(e);
            }
        }
        //noinspection unchecked
        return (T) convertView.getTag();
    }
}
