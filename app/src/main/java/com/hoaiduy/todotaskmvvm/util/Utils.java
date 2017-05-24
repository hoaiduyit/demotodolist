package com.hoaiduy.todotaskmvvm.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class Utils {

    public static float convertDpToPixel(int dp) {
        Resources r = Resources.getSystem();
        return Math.round(dp * (r.getDisplayMetrics().densityDpi / 160f));
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displaymetrics);

        int screenWidth = displaymetrics.widthPixels;

        return screenWidth;
    }

    public static void setTittleDetail(CharSequence tittle, TextView txtTitle, Context context){
        txtTitle.setText(tittle);
        int width = getScreenWidth(context);

        Rect bounds = new Rect();
        Paint textPaint = txtTitle.getPaint();
        textPaint.getTextBounds(txtTitle.getText().toString(), 0, txtTitle.getText().toString().length(), bounds);
        int widthText = bounds.width();

        txtTitle.setGravity(Gravity.LEFT);
        txtTitle.setWidth((int) (width / 2.0 + widthText / 2.0 - Utils.convertDpToPixel(72)));
    }

    public static void setTaskDetail(CharSequence task, TextView txtTask, Context context){
        txtTask.setText(task);
        int width = getScreenWidth(context);

        Rect bounds = new Rect();
        Paint textPaint = txtTask.getPaint();
        textPaint.getTextBounds(txtTask.getText().toString(), 0, txtTask.getText().toString().length(), bounds);
        int widthText = bounds.width();

        txtTask.setGravity(Gravity.LEFT);
        txtTask.setWidth((int) (width / 2.0 + widthText / 2.0 - Utils.convertDpToPixel(72)));
    }
}
