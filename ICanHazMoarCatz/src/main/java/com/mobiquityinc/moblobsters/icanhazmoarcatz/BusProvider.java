package com.mobiquityinc.moblobsters.icanhazmoarcatz;

import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;


/**
 * Created by paeder on 11/6/13.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();
    public static Bus getInstance() {
        Log.i("PRA", "returning bus");
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}