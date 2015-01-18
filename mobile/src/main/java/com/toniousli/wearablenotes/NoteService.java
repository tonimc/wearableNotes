package com.toniousli.wearablenotes;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.toniousli.wearablenotes.data.Note;

import java.util.ArrayList;

public class NoteService extends Service {
    private NotificationManagerCompat mNotificationManager;
    private Binder mBinder = new LocalBinder();
    private Note mNote;

    public class LocalBinder extends Binder {
        NoteService getService() {
            return NoteService.this;
        }
    }

    @Override
    public void onCreate() {
        mNotificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service", intent.getAction().toString());
        if (intent.getAction().equals(Note.ACTION_SHOW)) {
            createNotification(intent);
            return START_STICKY;
        }
        return START_NOT_STICKY;

    }

    private void createNotification(Intent intent) {
        mNote = Note.fromBundle(intent.getBundleExtra(Note.EXTRA_NOTE));
        ArrayList<Notification> notificationPages = new ArrayList<Notification>();

        Log.d("Service", mNote.toString());

        final NotificationCompat.Builder wearableNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(mNote.getTitle())
                .setContentText(mNote.getText())
                .setOngoing(false)
                .setOnlyAlertOnce(true)
                .setGroup("GROUP")
                .setGroupSummary(false);

        mNotificationManager.notify(mNote.getId(), wearableNotificationBuilder.build());
    }
}
