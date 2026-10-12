package com.xauusd.signals;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(48, 96, 48, 48);
        TextView t = new TextView(this);
        t.setTextSize(18);
        t.setText("XAUUSD Signals (M5, live)\nEMA 20/50 + filtre EMA 200\nSL 1.5xATR, TP 3xATR\n\nEntrez le prix XAUUSD affiché chez Exness, puis démarrez.\n");
        EditText in = new EditText(this);
        in.setHint("Prix Exness actuel (ex: 4012.35)");
        in.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        Button start = new Button(this);
        start.setText("Démarrer la surveillance");
        start.setOnClickListener(v -> {
            Intent s = new Intent(this, SignalService.class);
            String txt = in.getText().toString().trim().replace(',', '.');
            if (!txt.isEmpty()) {
                try { s.putExtra("exness", Double.parseDouble(txt)); } catch (Exception e) { }
            }
            startForegroundService(s);
        });
        Button stop = new Button(this);
        stop.setText("Arrêter");
        stop.setOnClickListener(v -> stopService(new Intent(this, SignalService.class)));
        l.addView(t);
        l.addView(in);
        l.addView(start);
        l.addView(stop);
        setContentView(l);
    }
}
