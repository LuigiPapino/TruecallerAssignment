package net.dragora.truecallerassignment.tasks;

import android.content.Context;

import net.dragora.truecallerassignment.network.NetworkApi;

import org.androidannotations.annotations.EBean;

import retrofit.mime.TypedByteArray;

/**
 * Created by Luigi Papino on 13/09/15.
 */
@EBean
public class T10thCharacterTask extends BaseTask<String> {


    private static final String TAG = T10thCharacterTask.class.getSimpleName();
    public static final int TASK_ID = 2;

    public T10thCharacterTask(Context context) {
        super(context);
    }

    @Override
    public String background() {
        String seq = new String((
                (TypedByteArray)NetworkApi.getApiService().getSupport().getBody())
                .getBytes());
        String result = seq.length() >= 10 ? String.valueOf(seq.charAt(9)) : null;
        return result;
    }


}
