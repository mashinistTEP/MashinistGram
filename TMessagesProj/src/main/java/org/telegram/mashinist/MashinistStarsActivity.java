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
        long userId = getUserConfig().getClientUserId();
        StarsHelper.loadStars(userId, new StarsHelper.StarsCallback() {
            @Override
            public void onResult(int stars, boolean hasPremium) {
                starsBalanceView.setText("⭐ " + stars);
                premiumStatusView.setText(hasPremium ? "Premium Active ✅" : "No Premium");
            }
        });
    }
}
