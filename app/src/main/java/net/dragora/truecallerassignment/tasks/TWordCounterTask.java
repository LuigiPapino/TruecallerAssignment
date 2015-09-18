package net.dragora.truecallerassignment.tasks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import net.dragora.truecallerassignment.network.NetworkApi;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;
import java.util.Map;

import retrofit.mime.TypedByteArray;

/**
 * Created by Luigi Papino on 13/09/15.
 */
@EBean
public class TWordCounterTask extends BaseTask<Map<String, Integer>> {


    private static final String TAG = TWordCounterTask.class.getSimpleName();
    public static final int TASK_ID = 3;

    public TWordCounterTask(Context context) {
        super(context);
    }

    @Override
    @Nullable
    public Map<String, Integer> background() {
        String[] words = new String((
                (TypedByteArray) NetworkApi.getApiService().getSupport().getBody())
                .getBytes())
                .split("[\\s\\n\\r\\t]");
        Map<String, Integer> wordsFrequency = new HashMap<>();
        SparseArray<String> sparseArray = new SparseArray<>();

        for (String word : words) {
            String wordLower = word.toLowerCase(); // case insensitive
            Integer count = wordsFrequency.get(wordLower);
            if (count == null) {
                wordsFrequency.put(wordLower, 1);
            }else{
                wordsFrequency.put(wordLower, count + 1);
            }
        }
        return wordsFrequency.size() > 0 ? wordsFrequency : null;
    }


}
