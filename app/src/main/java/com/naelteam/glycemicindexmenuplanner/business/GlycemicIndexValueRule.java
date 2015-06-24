package com.naelteam.glycemicindexmenuplanner.business;

import android.content.Context;
import android.content.res.Resources;

import com.naelteam.glycemicindexmenuplanner.R;
import com.naelteam.glycemicindexmenuplanner.view.GlycemicIndexValueView;

/**
 * Created by fab on 20/06/15.
 */
public class GlycemicIndexValueRule {

    public final static int LOW_IG_THRESHOLD = 40;
    public final static int HIGH_IG_THRESHOLD = 55;

    public static void setGlycemicIndexValue(Context context, GlycemicIndexValueView glycemicIndexValueView, String value) {
        if (value != null) {
            int intValue = Integer.parseInt(value);
            int color = 0;
            int gradientColor = 0;

            final Resources resources = context.getResources();
            if (intValue < LOW_IG_THRESHOLD) {
                color = resources.getColor(R.color.green);
                gradientColor = resources.getColor(R.color.green_light);
            } else if ((intValue >= LOW_IG_THRESHOLD) && (intValue < HIGH_IG_THRESHOLD)) {
                color = resources.getColor(R.color.orange);
                gradientColor = resources.getColor(R.color.orange_light);
            } else if (intValue >= HIGH_IG_THRESHOLD) {
                color = resources.getColor(R.color.marroon);
                gradientColor = resources.getColor(R.color.marroon_light);
            }
            glycemicIndexValueView.setColor(color);
            glycemicIndexValueView.setGradientColor(gradientColor);
        }
    }

}
