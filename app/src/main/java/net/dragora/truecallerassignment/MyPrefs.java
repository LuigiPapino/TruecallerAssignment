package net.dragora.truecallerassignment;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Luigi Papino on 14/09/15.
 */
@SharedPref
public interface MyPrefs {

    @DefaultBoolean(false)
    boolean injectError();

    @DefaultInt(0)
    int injectDelay();
}
