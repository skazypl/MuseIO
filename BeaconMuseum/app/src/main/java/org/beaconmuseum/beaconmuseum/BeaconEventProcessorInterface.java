package org.beaconmuseum.beaconmuseum;

import com.kontakt.sdk.android.ble.discovery.EventType;
import com.kontakt.sdk.android.common.profile.RemoteBluetoothDevice;

public interface BeaconEventProcessorInterface {
    public void processBeaconEvent(EventType event, RemoteBluetoothDevice device);
}