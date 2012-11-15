package example.android.numberpickersample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class BackgroundPicker extends Activity{
	public static final String DEBUG_TAG = "Background Picker TAG";
	public void onCreate(Bundle savedInstanceState){
		
	}
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
	
	 Uri imageUriToSaveCameraImageTo = Uri.fromFile(new File(BackgroundPicker.this.getFilesDir(), strBackgroundFilename));
	
	 // Load this image
	 Bitmap bitmapImage = BitmapFactory.decodeFile(imageUriToSaveCameraImageTo.getPath());
	 Drawable bgrImage = new BitmapDrawable(bitmapImage);
	
	 //show it in a view
	 ImageView backgroundView = (ImageView) findViewById(R.id.BackgroundImageView);
	 backgroundView.setImageURI(null); 
	 backgroundView.setImageDrawable(bgrImage);
	}
}

