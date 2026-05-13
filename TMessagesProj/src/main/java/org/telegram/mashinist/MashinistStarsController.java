package org.telegram.mashinist;

import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;

public class MashinistStarsController {

    private static int cachedStars = -1;
    private static boolean cachedPremium = false;
    private static long lastUpdate = 0;

    public static int getStars() {
        if (System.currentTimeMillis() - lastUpdate > 30000) {
            loadFromServer();
        }
        return Math.max(cachedStars, 0);
    }

    public static boolean hasPremium() {
        return cachedPremium;
    }

    private static void loadFromServer() {
        try {
            long userId = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
            StarsAPI.UserData data = StarsAPI.getUserData(userId);
            cachedStars = data.starsBalance;
            cachedPremium = data.hasPremium;
            lastUpdate = System.currentTimeMillis();
        } catch (Exception e) {
            cachedStars = 0;
            cachedPremium = false;
        }
    }
}
