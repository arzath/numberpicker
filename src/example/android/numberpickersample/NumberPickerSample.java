package example.android.numberpickersample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import example.android.numberpicker.NumberPickerDialog;
import example.android.numberpicker.R;

public class NumberPickerSample extends Activity implements 
	NumberPickerDialog.OnNumberSetListener {
    //private static final String TAG = NumberPickerSample.class.getSimpleName();
    private TextView tv;
    private CManager cdt;
    private ProgressBar bar;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = (TextView) findViewById(R.id.number_display);
        bar = (ProgressBar) findViewById(R.id.bar);
        bar.setMax(100);
        
        cdt = (CManager) getLastNonConfigurationInstance();
        if(cdt == null){
        	NumberPickerDialog dialog = new NumberPickerDialog(this, -1, 0);
        	dialog.setOnNumberSetListener(this);
            dialog.show();
        }
        else{
        	cdt.attach(this);
        	bar.setMax(cdt.getBarMax());
        	bar.setVisibility(View.VISIBLE);
        	updateProgressBar(cdt.getProgress());
        }
    }

    @Override
    public Object onRetainNonConfigurationInstance(){
    	cdt.detach();
    	return cdt;
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
        }
        else if (item.getItemId() == R.id.menu_preferences_item) {
            startActivity(new Intent(this, NumberPickerSamplePreference.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    public TextView getTextViewFromNPS(){
    	return tv;
    }
    
    private void updateProgressBar(int aBarTicker){
    	bar.setProgress(aBarTicker);
    }
    
    private void updateCounterDisplay(long millisUntilFinished){
    	tv.setText("" + millisUntilFinished);
    }
    
    private void hideProgressBar(){
    	bar.setVisibility(View.GONE);
    }

    public void onNumberSet(int number){
    	bar.setProgress(0);
    	bar.setVisibility(View.VISIBLE);
    	bar.setMax(number * 1000);
    	cdt = new CManager(this, (number+1) * 1000, 1000);
    	cdt.start();
    }
    
    
    
    static class CManager extends CountDownTimer{
    	private NumberPickerSample mNumberPickerSample;
    	private int barTicker = 0;
    	private int barMax = 100; 
    	
		public CManager(NumberPickerSample numberPickerSample, long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			mNumberPickerSample = numberPickerSample;
			barMax = (int) millisInFuture;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			if(mNumberPickerSample == null) Log.w("CManager", "onTick() skipped - no activity");
			else{
				barTicker += 1000;
				mNumberPickerSample.updateProgressBar(barTicker);
				mNumberPickerSample.updateCounterDisplay(millisUntilFinished/1000);
			}
		}

		@Override
		public void onFinish() {			
			Intent myIntent = new Intent(mNumberPickerSample.getApplicationContext(), MusicService.class);
			mNumberPickerSample.startService(myIntent);
			mNumberPickerSample.hideProgressBar();
			mNumberPickerSample.getTextViewFromNPS().setText(R.string.time_up);
			
			ServiceKiller sk = new ServiceKiller(mNumberPickerSample);
			sk.setCancelable(false);
			sk.show(mNumberPickerSample.getFragmentManager(), "KillMusicService");
		}
		
		public int getProgress(){
			return barTicker;
		}
		
		public int getBarMax(){
			return barMax;
		}
		
		private void detach(){
			mNumberPickerSample = null;
		}
		
		private void attach(NumberPickerSample numberPickerSample){
			mNumberPickerSample = numberPickerSample;
		}
    }
    
    private static class ServiceKiller extends DialogFragment{
    	private NumberPickerSample mNumberPickerSample;
    	public ServiceKiller(NumberPickerSample numberPickerSample){
    		mNumberPickerSample = numberPickerSample;
    	}
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.confirm_kill_service)
				.setPositiveButton(R.string.confirm_kill_ok, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int id) {
						mNumberPickerSample.stopService(
								new Intent(mNumberPickerSample.getApplicationContext(), 
										MusicService.class));
					}
				});
			return builder.create();
		}
	}
}
