package example.android.numberpickersample;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class NumberPickerSamplePreference extends PreferenceActivity implements 
	OnSharedPreferenceChangeListener
	{
	public static final String DEBUG_TAG = "Background Picker TAG";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setSharedPreferencesMode(MODE_PRIVATE);
        preferenceManager.setSharedPreferencesName("numberPicker.preferences");
        
        addPreferencesFromResource(R.xml.preference_settings);
    }
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key){
    	
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    }
/*
    // Read bitmap from Uri
	 public Bitmap readBitmap(Uri selectedImage) {
	     Bitmap bm = null;
	     BitmapFactory.Options options = new BitmapFactory.Options();
	     options.inSampleSize = 2; //reduce quality 
	     AssetFileDescriptor fileDescriptor =null;
	     try {
	         fileDescriptor = this.getContentResolver().openAssetFileDescriptor(selectedImage,"r");
	     } catch (FileNotFoundException e) {
	         e.printStackTrace();
	     }
	     finally{
	         try {
	             bm = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
	             fileDescriptor.close();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	     }
	     return bm;
	 }
	 
	 private void saveBackground(Bitmap Background) {
		 String strBackgroundFilename = "background_custom.jpg";
		 try {
		     Background.compress(CompressFormat.JPEG, 80, openFileOutput(strBackgroundFilename, MODE_PRIVATE));
		 } catch (Exception e) {
		     Log.e(DEBUG_TAG, "Background compression and save failed.", e);
		 }
		
		 Uri imageUriToSaveCameraImageTo = Uri.fromFile(new File(NumberPickerSamplePreference.this.getFilesDir(), strBackgroundFilename));
		
		 // Load this image
		 Bitmap bitmapImage = BitmapFactory.decodeFile(imageUriToSaveCameraImageTo.getPath());
		 Drawable bgrImage = new BitmapDrawable(bitmapImage);
		
		 //show it in a view
		 ImageView backgroundView = (ImageView) findViewById(R.id.BackgroundImageView);
		 backgroundView.setImageURI(null); 
		 backgroundView.setImageDrawable(bgrImage);
	}
*/
}
