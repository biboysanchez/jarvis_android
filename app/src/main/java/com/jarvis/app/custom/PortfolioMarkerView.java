
package com.jarvis.app.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.jarvis.app.R;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class PortfolioMarkerView extends MarkerView {


    private  ArrayList<String> keys = new ArrayList<>();

    private final TextView tvContent;
    private final TextView tvTitle;

    public PortfolioMarkerView(Context context, ArrayList<String> keys, int layoutResource) {
        super(context, layoutResource);
        this.keys = keys;
        tvContent = findViewById(R.id.tvContent);
        tvTitle  = findViewById(R.id.tvTitle);
    }


    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            Log.i("CANDLE", "CANDLE::> " + ce);
            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            Log.i("CANDLE1", "CANDLE::> " + e);
            Log.i("CANDLE1", "CANDLE::> " + highlight);
            tvContent.setText(String.format("%.0f%s", e.getY(), "%"));
            tvTitle.setText(keys.get(highlight.getDataSetIndex()));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
