package com.soullotto;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.soullotto.soullotto.R;

import java.net.URISyntaxException;

public class LottoNumberReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    public static final String LOTTO_URL = "http://nlotto.co.kr/gameResult.do?method=byWin";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("결과를 확인해보세요!")
                .setContentText("클릭 시 홈페이지로 이동합니다.")
                .setOngoing(false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        Intent i = null;
        try {
            i = Intent.parseUri(LOTTO_URL, Intent.URI_INTENT_SCHEME);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        i,
                        PendingIntent.FLAG_ONE_SHOT
                );
        // example for blinking LED
        builder.setLights(0xFFb71c1c, 1000, 2000);
        builder.setContentIntent(pendingIntent);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

}