package example.android.numberpickersample;

import java.io.File;
import java.io.IOException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service implements OnPreparedListener, OnErrorListener, OnCompletionListener{
	public final static String TAG = "NumberPickerSample MusicService";
	private File mMusic = new File(Environment.getExternalStorageDirectory().getPath() + "/Music/", "air.mp3");
	private MediaPlayer mp = new MediaPlayer();
	private Uri musicUri;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		super.onStartCommand(intent, flags, startId);
		
		musicUri = Uri.fromFile(mMusic);
		//spawn a thread and let it run the MediaPlayer
		try{
			mp.setVolume(1.0f, 1.0f);
			mp.setWakeMode(getApplicationContext(), PowerManager.FULL_WAKE_LOCK);
			mp.setOnPreparedListener(this);
			mp.setOnCompletionListener(this);
			mp.setOnErrorListener(this);
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setDataSource(getApplicationContext(), musicUri);
			mp.prepareAsync();
		}
		catch(IOException e){
			Log.e(getString(R.string.app_name), e.getMessage());
		}
		
		return START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.i(TAG, "MusicService destroyed.");
		releaseResource();
	}
	
	private void releaseResource(){
		if(mp != null){
			mp.reset();
			mp.release();
			mp = null;
		}
		
		stopSelf();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
	}
	
	@Override
	public boolean onError(MediaPlayer mediaPlayer, int what, int extra){
		Toast.makeText(getApplicationContext(), "Media player error! Resetting.",
	            Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error: what=" + String.valueOf(what) + ", extra=" + String.valueOf(extra));

        releaseResource();
		return true;
	}
	
	@Override
	public void onCompletion(MediaPlayer mediaPlayer){
		Log.i(TAG, "Completed: " + mediaPlayer.toString());
		releaseResource();
	}
}
