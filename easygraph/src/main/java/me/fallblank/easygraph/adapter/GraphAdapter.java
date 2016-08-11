package me.fallblank.easygraph.adapter;

import android.widget.Adapter;

/**
 * Created by fallb on 2016/8/11.
 */
public interface GraphAdapter {
    public int getCount();
    public int getColor(int index);
    public double getPercent(int index);
    public String getName(int index);
}
