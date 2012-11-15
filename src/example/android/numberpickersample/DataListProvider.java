package example.android.numberpickersample;

import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DataListProvider extends AsyncTask<Void, Void, Cursor>{
	public static final String TAG = "com.example.android.numberpickersample.DataListProvider";
	public static final String SONG_ID = "_ID";
	public static final String SONG_TITLE = "TITLE";
	private NumberPickerSamplePreference mNpsp;
	private MusicSelectingPreference mMsp;
	private Cursor mCursor;
	
	private CharSequence[] mEntries;
	private CharSequence[] mEntryValues;
	 
	
	public DataListProvider(MusicSelectingPreference msp, NumberPickerSamplePreference npsp){
		mMsp = msp;
		mNpsp = npsp;
	}
	
	@Override
	protected Cursor doInBackground(Void... notused) throws NullPointerException{
		final String[] projection = {
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.TITLE
		};
		final String selectionClause = null;
		final String[] selectionArgs = null;
		mCursor = mNpsp.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				projection, 
				selectionClause, 
				selectionArgs, 
				null
			);
		return mCursor;
	}
	
	@Override
	protected void onPostExecute(Cursor cursor){
		
		mNpsp.startManagingCursor(cursor);
		cursor.moveToFirst();
		int mCount = cursor.getCount();
		
		mEntries = new CharSequence[mCount];
		mEntryValues = new CharSequence[mCount];
		for(int i=0; i<mCount; i++){
			mEntryValues[i] = cursor.getString(cursor.getColumnIndexOrThrow(DataListProvider.SONG_ID));
			mEntries[i] = cursor.getString(cursor.getColumnIndexOrThrow(DataListProvider.SONG_TITLE));
			cursor.moveToNext();
		}
		ListView view = new ListView(mMsp.getContext());
		view.setId(233);
		
		
		view.setAdapter(mNpsp.getListAdapter());
		
		mMsp.setLayoutResource(view.getId());
		
		mMsp.setEntries(mEntries);
		mMsp.setEntryValues(mEntryValues);
	}
	
	public void attach(NumberPickerSamplePreference npsp, MusicSelectingPreference msp){
		mNpsp = npsp;
		mMsp = msp;
	}
	
	public void detach(){
		mNpsp = null;
		mMsp = null;
	}
		/*dataListCursor.moveToFirst();
		for(int i=0; i<mCount; i++){
			entryValues[i] = dataListCursor.getString(dataListCursor.getColumnIndexOrThrow(DataListProvider.SONG_ID));
			entries[i] = dataListCursor.getString(dataListCursor.getColumnIndexOrThrow(DataListProvider.SONG_TITLE));
			dataListCursor.moveToNext();
		}
		
	}
	
	
	
	
	/*
	public Cursor queryExternalDb(){
		Log.i(TAG, "Database queried.");
		final String[] projection = {
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.TITLE,
		};
		final String selectionClause = null;
		final String[] selectionArgs = null;
		Thread mThread = new Thread(new Runnable(){
			public void run(){
				mCursor = mContext.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				projection, 
				selectionClause, 
				selectionArgs, 
				null);
			}
		});
		mThread.start();
		return mCursor;
	}
	
	private ArrayAdapter<String> getAdapter(){
		return new ArrayAdapter<String>(mMsp.getContext(), android.R.layout.simple_expandable_list_item_1);
	}
	*/
}
