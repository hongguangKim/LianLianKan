package whu.iss.sric.view;

/**
 * Created by jinhongguang on 2016/11/16.
 */


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.SeekBar;

import whu.iss.sric.android.R;

public class SnailBar extends SeekBar {

    public SnailBar(Context context) {
        super(context);
        init();
    }


    public SnailBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnailBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setMax(100);
        this.setThumbOffset(dip2px(getContext(), 10));
        this.setProgressDrawable(getResources().getDrawable(R.drawable.snailbar_define_style));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        AnimationDrawable drawable = (AnimationDrawable)this.getThumb();
        drawable.start();
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}

