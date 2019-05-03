package nl.ou.applab.meldapp;

import com.google.firebase.database.DataSnapshot;

/**
 * Interface benodigd om een luisteraar te gebruiken zodat geregistreerd kan worden of
 * de query op firebase succesvol is geweest of gefaalt heeft
 *
 * @author Steven Minken
 */
interface OnGetDataListener {

    void onSuccess(DataSnapshot dataSnapshot);

    void onFailure();
}
