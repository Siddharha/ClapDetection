package in.creativelizard.clapdetection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        checkRecordPermission();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

        if(event.getEventType().equalsIgnoreCase("clap")) {

            if(!showAlertDialog(event).isShowing()) {
                showAlertDialog(event).show();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initialize() {


    }

    private void checkRecordPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        }else {

            startService(new Intent(this,ClapDetcService.class));

        }
    }

    public void printResp(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private AlertDialog showAlertDialog(MessageEvent event){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Time in Clap Sound: "+event.getTime());
        builder.setTitle("Clap Detected!");

        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }
}
