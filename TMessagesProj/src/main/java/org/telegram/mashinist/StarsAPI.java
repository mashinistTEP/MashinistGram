package org.telegram.mashinist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class StarsAPI {

    private static final String BASE_URL = "https://mashinistgram.atwebpages.com/api/";

    public static class UserData {
        public long userId;
        public int starsBalance;
        public boolean hasPremium;
        public long premiumUntil;

        public UserData(long userId, int starsBalance, boolean hasPremium, long premiumUntil) {
            this.userId = userId;
            this.starsBalance = starsBalance;
            this.hasPremium = hasPremium;
            this.premiumUntil = premiumUntil;
        }
    }

    public static UserData getUserData(long userId) {
        try {
            URL url = new URL(BASE_URL + "get_user.php?user_id=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();

            JSONObject json = new JSONObject(response.toString());
            int stars = json.optInt("stars_balance", 0);
            boolean premium = json.optBoolean("has_premium", false);
            long premiumUntil = json.optLong("premium_until", 0);

            return new UserData(userId, stars, premium, premiumUntil);
        } catch (Exception e) {
           android.util.Log.e("MashinistGram", "StarsAPI error for user " + userId, e);
            return new UserData(userId, 0, false, 0);
        }
    }
}	
