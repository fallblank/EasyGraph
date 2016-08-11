package me.fallblank.fbgraphlibrary;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import me.fallblank.easygraph.adapter.GraphAdapter;
import me.fallblank.easygraph.graph.PieGraph;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PieGraph pie = (PieGraph) findViewById(R.id.pie);
        pie.setDistance(32);
        final TextView tv01 = (TextView) findViewById(R.id.tv01);
        final TextView tv02 = (TextView) findViewById(R.id.tv02);

        pie.setPieClickedListener(new PieGraph.PieClickedListener() {
            @Override public void onPieClicked(int index) {
                GraphAdapter adapter = pie.getAdapter();
                tv01.setText("selected:" + adapter.getName(index));
                tv02.setText(String.format("percentage:%.2f", adapter.getPercent(index)));
            }
        });
        pie.setAdapter(new GraphAdapter() {
            @Override public int getCount() {
                return 4;
            }


            @Override public int getColor(int index) {
                if (index == 0) {
                    return Color.RED;
                } else if (index == 1) {
                    return Color.GREEN;
                } else if (index == 2) {
                    return Color.YELLOW;
                } else {
                    return Color.BLUE;
                }
            }


            @Override public String getName(int index) {
                if (index == 0) {
                    return "Red";
                } else if (index == 1) {
                    return "Greenn";
                } else if (index == 2) {
                    return "Yellow";
                } else {
                    return "BLUE";
                }
            }


            @Override public double getPercent(int index) {
                if (index == 0) {
                    return 1.0 / 4;
                } else if (index == 1) {
                    return 1.0 / 8;
                } else if (index == 2) {
                    return 1.0 / 6;
                } else {
                    return 1.0 - 1.0 / 4 - 1.0 / 6 - 1.0 / 8;
                }
            }
        });

    }
}
