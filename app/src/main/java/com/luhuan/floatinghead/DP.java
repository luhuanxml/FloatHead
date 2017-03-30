package com.luhuan.floatinghead;

import android.content.Context;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class DP {
    public static int d2p(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int p2d(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
