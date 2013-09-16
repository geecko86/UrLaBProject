package be.urlab.app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import be.urlab.app.pamela.PamelaTask;
import be.urlab.app.spaceapi.SpaceAPITask;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isNetworkAvailable())
            new SpaceAPITask().execute((TextView) findViewById(R.id.status_val));
        else
            Toast.makeText(this, "No internet connection...", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case (R.id.action_refresh):
                this.onPostCreate(null);
                return true;
        }
        return false;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openIRC(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://webchat.freenode.net/?channels=urlab")));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this,"No app found",Toast.LENGTH_LONG).show();
        }
    }

    public void openMaps(View v) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:50.81292,4.38438?q=131+avenue+Buyl+1050+Ixelles")));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this,"No app found",Toast.LENGTH_LONG).show();
        }
    }

}