package be.urlab.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by geecko on 16/09/13
 */
public class Utils {

    public static String getUrlAsString(URL paramURL)
            throws IOException {

        HttpURLConnection localHttpURLConnection = (HttpURLConnection) paramURL.openConnection();
        localHttpURLConnection.setRequestMethod("GET");
        localHttpURLConnection.setReadTimeout(15000);
        localHttpURLConnection.setUseCaches(false);
        localHttpURLConnection.connect();
        InputStreamReader localInputStreamReader = new InputStreamReader(localHttpURLConnection.getInputStream());
        BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
        StringBuilder localStringBuilder = new StringBuilder();
        while (true) {
            String str = localBufferedReader.readLine();
            if (str == null)
                break;
            localStringBuilder.append(str).append("\n");
        }
        localInputStreamReader.close();
        return localStringBuilder.toString();
    }
}
