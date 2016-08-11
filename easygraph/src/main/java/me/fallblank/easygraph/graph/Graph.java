package me.fallblank.easygraph.graph;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import me.fallblank.easygraph.adapter.GraphAdapter;

/**
 * Created by fallb on 2016/8/10.
 */
public abstract class Graph extends View {

    protected int mWidth;
    protected int mHeight;

    protected Paint mPaint;

    protected int mPandingTop;
    protected int mPandingBottom;
    protected int mPandingLeft;
    protected int mPandingRight;


    protected GraphAdapter mAdapter;


    public void setAdapter(GraphAdapter adapter) {
        mAdapter = adapter;
        invalidate();
    }


    public GraphAdapter getAdapter() {
        return mAdapter;
    }


    /**
     * 完成一些初始化工作
     */
    protected abstract void init();

    public Graph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public Graph(Context context) {
        super(context);
    }


    public Graph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(21)
    public Graph(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
