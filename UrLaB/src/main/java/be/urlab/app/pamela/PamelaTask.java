package be.urlab.app.pamela;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import be.urlab.app.R;
import be.urlab.app.Utils;

/**
 * Created by geecko on 16/09/13
 */

public class PamelaTask extends AsyncTask<TextView, Void, PamelaResult> {

    TextView pamelaTV;

    @Override
    protected PamelaResult doInBackground(TextView... params) {
        pamelaTV = params[0];
        JSONObject pamelaJSON;
        PamelaResult result = new PamelaResult();
        try {
            URL pamelaURL = new URL("http://pamela.urlab.be/mac.json");
            pamelaJSON = new JSONObject(Utils.getUrlAsString(pamelaURL));
            JSONArray jsonNicks = pamelaJSON.getJSONArray("color");
            result.nicks = new String[jsonNicks.length()];
            for (int i = 0; i < jsonNicks.length(); i++)
                result.nicks[i] = jsonNicks.getString(i);
            result.hidden = pamelaJSON.getInt("hidden");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(PamelaResult result) {
        if (result == null)
            return;
        if (result.nicks.length != 0 && result.hidden != 0) {
            StringBuilder sb = new StringBuilder();
            for (String nick : result.nicks)
                sb.append("- ").append(nick).append("\n");
            sb.append("- ").append(result.hidden).append(" ");
            Context context = pamelaTV.getContext();
            if (context != null)
                sb.append(context.getString(R.string.hidden_alias));
            pamelaTV.setText(sb);
        } else
            pamelaTV.setText(R.string.nobody);
    }

}

