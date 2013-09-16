package be.urlab.app.spaceapi;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import be.urlab.app.R;
import be.urlab.app.Utils;
import be.urlab.app.pamela.PamelaTask;

/**
 * Created by geecko on 16/09/13
 */
public class SpaceAPITask extends AsyncTask<TextView, Void, Boolean> {

    TextView spaceTV;

    @Override
    protected Boolean doInBackground(TextView... params) {
        spaceTV = params[0];
        JSONObject spaceJSON;
        try {
            URL spaceURL = new URL("http://api.urlab.be/spaceapi/");
            spaceJSON = new JSONObject(Utils.getUrlAsString(spaceURL));
            return spaceJSON.getBoolean("open");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean open) {
        if (open == null)
            return;
        ViewGroup vg = ((ViewGroup) spaceTV.getParent());
        if (open) {
            spaceTV.setText(R.string.status_open);
            spaceTV.setTextColor(Color.parseColor("#009900"));
            if (vg != null) {
                TextView pamelaTV = (TextView) vg.findViewById(R.id.pamela_val);
                new PamelaTask().execute(pamelaTV);
            }
        } else {
            spaceTV.setText(R.string.status_closed);
            spaceTV.setTextColor(Color.parseColor("#A80000"));
            if (vg != null)
                ((TextView) vg.findViewById(R.id.pamela_val)).setText(R.string.nobody);
        }
    }

}
