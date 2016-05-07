package org.beaconmuseum.beaconmuseum;

import com.google.inject.Inject;
import org.beaconmuseum.beaconmuseum.beacons.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

class myComparator implements Comparator<BeaconInfo> {
    @Override
    public int compare(BeaconInfo b1, BeaconInfo b2) {
        if (b1.range < b2.range) return -1;
        if (b1.range > b2.range) return 1;
        return 0;
    }
}

public class AppManager {
    @Inject BeaconsInRangeList beaconsInRangeList;

    public BeaconInfo[] refreshGUI() {
        BeaconInfo bList[] = beaconsInRangeList.getList();
        Collections.sort(new ArrayList<>(Arrays.asList(bList)), new myComparator());
        return bList;
    }
}

