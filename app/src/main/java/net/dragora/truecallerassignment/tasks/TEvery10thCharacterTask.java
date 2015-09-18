package net.dragora.truecallerassignment.tasks;

import android.content.Context;

import net.dragora.truecallerassignment.network.NetworkApi;

import org.androidannotations.annotations.EBean;

import retrofit.mime.TypedByteArray;

/**
 * Created by Luigi Papino on 13/09/15.
 */
@EBean
public class TEvery10thCharacterTask extends BaseTask<CharSequence> {


    private static final String TAG = TEvery10thCharacterTask.class.getSimpleName();
    public static final int TASK_ID = 1;

    public TEvery10thCharacterTask(Context context) {
        super(context);
    }



    @Override
    public CharSequence background() {
        String seq =  new String((
                (TypedByteArray) NetworkApi.getApiService().getSupport().getBody())
                .getBytes());
        int i = 9;
        int len = seq.length();
        StringBuilder builder = new StringBuilder();
        while (i < len) {
            builder.append(seq.charAt(i));
            i+=10;
        }
        return builder.toString();
    }


}
