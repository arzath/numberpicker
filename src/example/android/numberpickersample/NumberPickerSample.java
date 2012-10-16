package example.android.numberpickersample;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import example.android.numberpicker.NumberPickerDialog;
import example.android.numberpicker.R;

public class NumberPickerSample extends Activity implements NumberPickerDialog.OnNumberSetListener {
    private static final String TAG = NumberPickerSample.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        NumberPickerDialog dialog = new NumberPickerDialog(this, -1, 0);
        
        dialog.setTitle(getString(R.string.dialog_picker_title));
        dialog.setOnNumberSetListener(this);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_dialog_item) {
            NumberPickerDialog dialog = new NumberPickerDialog(this, -1, 0);
            
            //dialog.setTitle(getString(R.string.dialog_picker_title));
            dialog.setOnNumberSetListener(this);
            dialog.show();

            return true;
        } else if (item.getItemId() == R.id.menu_preferences_item) {
            startActivity(new Intent(this, NumberPickerPreferenceActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onNumberSet(int number) {
    	//Log.d(TAG, "Total Seconds: " + number);
    	new CountDownTimer(number*1000, 1000){
    		TextView tv = (TextView) findViewById(R.id.number_display);
    		public void onTick(long millisUntilFinished){
    			tv.setText("Seconds Remaining: " + millisUntilFinished/1000);
    		}
    		public void onFinish(){
    			Intent myIntent = new Intent();
    			myIntent.setAction(android.content.Intent.ACTION_VIEW);
    			File myfile = new File("mnt/sdcard/Music/limit.mp3");
    			myIntent.setDataAndType(Uri.fromFile(myfile), "audio/*");
    			startActivity(myIntent);
    			tv.setText("Done!");
    		}
    	}.start();
    }
    
}
