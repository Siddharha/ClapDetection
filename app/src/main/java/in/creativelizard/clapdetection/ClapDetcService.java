package in.creativelizard.clapdetection;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;

/**
 * Created by siddharthamaji on 17/03/18.
 */

public class ClapDetcService extends Service {
    //private Context context;
    private static final String TAG = "response";
    AudioDispatcher dispatcher;
    double threshold = 8;
    double sensitivity = 20;
    int clapCount;
    Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
         handler = new Handler();
        Log.e("hh","HI");
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);
        detectClap();
        //
        return START_STICKY;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void detectClap() {
        PercussionOnsetDetector mPercussionDetector = new PercussionOnsetDetector(22050, 1024,
                new OnsetHandler() {

                    @Override
                    public void handleOnset(double time, double salience) {
                        Log.d(TAG, "Clap detected!");

                        clapCount++;

                        if(clapCount == 2) {

                            MessageEvent messageEvent = new MessageEvent();
                            messageEvent.setEventType("clap");
                            messageEvent.setSalience(salience);
                            messageEvent.setTime(time);
                            EventBus.getDefault().post(messageEvent);
                        }else if(clapCount == 1){
                            delaySomeTime();
                        }

                    }
                }, sensitivity, threshold);

        dispatcher.addAudioProcessor(mPercussionDetector);
        new Thread(dispatcher,"Audio Dispatcher").start();
    }

    private void delaySomeTime() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               clapCount = 0;
            }
        }, 3000);
    }


}
