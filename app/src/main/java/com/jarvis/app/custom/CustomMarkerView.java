
package com.jarvis.app.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.jarvis.app.R;


/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class CustomMarkerView extends MarkerView {

    private final TextView tvTop;
    private final TextView tvSub;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvTop     = findViewById(R.id.tvTop);
        tvSub     =  findViewById(R.id.tvBottom);
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
//        if (e instanceof CandleEntry) {
//            CandleEntry ce = (CandleEntry) e;
//            tvTop.setText(Utils.formatNumber(ce.getHigh(), 0, true));
//        } else {
            tvTop.setText(String.format("%.2f", e.getX()));
            tvSub.setText(String.format("%.2f", e.getY()));
//        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
