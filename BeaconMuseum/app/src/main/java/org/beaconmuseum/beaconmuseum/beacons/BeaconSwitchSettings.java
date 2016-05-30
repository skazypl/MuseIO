package org.beaconmuseum.beaconmuseum.beacons;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.google.inject.Inject;

import org.beaconmuseum.beaconmuseum.AppManager;
import org.beaconmuseum.beaconmuseum.GUIManager;
import org.beaconmuseum.beaconmuseum.R;

import java.util.ArrayList;

/**
 * Created by filip on 30.05.16.
 */
public class BeaconSwitchSettings {
    static Activity _activity;

    private static BeaconSwitchSettings ourInstance = new BeaconSwitchSettings(_activity);

    public static BeaconSwitchSettings getInstance() {
        return ourInstance;
    }

    private BeaconSwitchSettings(Activity activity) {
        _activity = activity;
        manualModeOn = false;
        updateLastNearestBeacon();
    }

    @Inject
    private AppManager appManager;
    @Inject
    private NearestBeacon nearestBeacon;

    BeaconInfo lastNearestBeacon;
    boolean manualModeOn;

    public void toggleMode() {
        manualModeOn = !manualModeOn;
    }

    public void updateLastNearestBeacon() {
        lastNearestBeacon = nearestBeacon.getInfo();
    }

    public boolean nearestBeaconHasChanged() {
        return nearestBeacon.getInfo() == lastNearestBeacon;
    }

    public void updateSlideMenu(final Activity t) {
        final HorizontalScrollView slideMenu = (HorizontalScrollView) _activity.findViewById(R.id.scrollView);
        final ArrayList<String> list = new ArrayList<>();
        final LinearLayout ll = new LinearLayout(t);
        for (BeaconInfo beacon : appManager.refreshGUI()) {
            Log.d("Update beacon", beacon.id);
            list.add(beacon.id);
        }
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll.setOrientation(LinearLayout.HORIZONTAL);
                if (slideMenu == null) {
                    return;
                }
                slideMenu.removeAllViews();
                slideMenu.addView(ll);
                for (String str : list) {
                    Log.d("str", str);
                    Button b = new Button(t);
                    b.setText(str);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            displayAnotherPainting(v);
                        }
                    });
                    ll.addView(b);
                }

                slideMenu.refreshDrawableState();
            }
        });
    }
    private void displayAnotherPainting(View v) {
        Button b = (Button) v;
        String beaconName = b.getText().toString();
        String link = GUIManager.getBeaconLink(Integer.parseInt(beaconName));

        WebView closestPainting = (WebView) _activity.findViewById(R.id.webView);
        closestPainting.loadUrl(link);

    }
}