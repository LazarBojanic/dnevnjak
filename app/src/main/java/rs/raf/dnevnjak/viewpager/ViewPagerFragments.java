package rs.raf.dnevnjak.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerFragments extends ViewPager {
    public ViewPagerFragments(Context context) {
        super(context);
    }

    public ViewPagerFragments(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent){
        return false;
    }

}
