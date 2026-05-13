package org.telegram.mashinist;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;

public class MashinistStarsActivity extends BaseFragment {

    private TextView starsBalanceView;
    private TextView premiumStatusView;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(org.telegram.messenger.R.drawable.ic_ab_back);
        actionBar.setTitle("⭐ Stars");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(0, 100, 0, 0);

        starsBalanceView = new TextView(context);
        starsBalanceView.setTextSize(48);
        starsBalanceView.setTextColor(Color.WHITE);
        starsBalanceView.setGravity(Gravity.CENTER);

        premiumStatusView = new TextView(context);
        premiumStatusView.setTextSize(20);
        premiumStatusView.setTextColor(0xFFE0BB00);
        premiumStatusView.setGravity(Gravity.CENTER);
        premiumStatusView.setPadding(0, 20, 0, 0);

        layout.addView(starsBalanceView);
        layout.addView(premiumStatusView);

        loadData();

        fragmentView = layout;
        return layout;
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    java.net.URL url = new java.net.URL("https://mashinistgram.atwebpages.com/api/get_user.php?user_id=1");
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    conn.disconnect();

                    final String result = sb.toString();
                    if (getParentActivity() != null) {
                        getParentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                starsBalanceView.setText("⭐ " + result);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (getParentActivity() != null) {
                        getParentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                starsBalanceView.setText("Error: " + e.getMessage());
                            }
                        });
                    }
                }
            }
        }).start();
    }
}
