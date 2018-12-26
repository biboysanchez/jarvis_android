
package com.jarvis.app.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.jarvis.app.R;
import com.jarvis.app.model.PieModel;
import com.jarvis.app.utils.Util;

import java.util.ArrayList;


/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class PieMarkerView extends MarkerView {
    private final TextView tvContent;
    private final TextView tvTitle;
    private final TextView tvPercent;
    private ArrayList<PieModel> data = new ArrayList<>();

    public PieMarkerView(Context context, ArrayList<PieModel> data, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
        tvTitle  = findViewById(R.id.tvTitle);
        tvPercent = findViewById(R.id.tvPercent);
        this.data = data;
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint("DefaultLocale")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            Log.i("CANDLE", "CANDLE::> " + ce);
            tvContent.setText(Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            Log.i("CANDLE1", "CANDLE::> " + e);
            Log.i("CANDLE1", "CANDLE::> " + highlight);

            int index = (int) highlight.getX();
            PieModel d = data.get(index);
//            String label = "${d?.item} [${d?.percentage}%] ${Util.priceFormat(d?.portofolio!!.toFloat()).replace(" .00 ","
//            ")} B"
            tvTitle.setText(d.getItem());
            tvContent.setText(String.format("%s B", Util.INSTANCE.priceFormat((float) d.getPortofolio())).replace(".00", ""));
            tvPercent.setText(String.format("%.0f%s", e.getY(), "%"));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
