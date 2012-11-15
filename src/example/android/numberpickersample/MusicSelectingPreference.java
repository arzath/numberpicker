package example.android.numberpickersample;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class MusicSelectingPreference extends ListPreference {
	public static final String NO_SELECTION = "0";
	
	private NumberPickerSamplePreference mNpsp;
	
	public MusicSelectingPreference(Context npsp) {
		super(npsp, null);
	}
	public MusicSelectingPreference(Context npsp, AttributeSet attrs){
		super(npsp, attrs);
		mNpsp = (NumberPickerSamplePreference) npsp;
		DataListProvider dlp = new DataListProvider(this, mNpsp);
		dlp.execute();
	}
/*
	@Override
	public View onCreateDialogView(){	
		ListView view = new ListView(getContext());
		DataListProvider dlp = new DataListProvider(this, mNpsp);
		
		view.setAdapter(getAdapter());
		setEntries(mEntries);
		setEntryValues(mEntryValues);
		//setValueIndex(initializeIndex());
		return view;
	}
	
	private ArrayAdapter<String> getAdapter(){
		return new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1);
	}
	
	private PreferenceScreen getMusicPreferenceScreen(){
		PreferenceScreen preferenceRoot = getPreferenceManager().createPreferenceScreen(this);
		ListPreference musicListPreference = new ListPreference(this);
		
		DataListProvider dataList = new DataListProvider(this);
		Cursor dataListCursor = dataList.queryExternalDb();
		startManagingCursor(dataListCursor);
		
		int mCount = dataListCursor.getCount();
		
		CharSequence[] entries = new CharSequence[mCount];
		CharSequence[] entryValues = new CharSequence[mCount];
		
		dataListCursor.moveToFirst();
		for(int i=0; i<mCount; i++){
			entries[i] = dataListCursor.getString(dataListCursor.getColumnIndexOrThrow(DataListProvider.SONG_ID));
			entryValues[i] = dataListCursor.getString(dataListCursor.getColumnIndexOrThrow(DataListProvider.SONG_TITLE));
		}
		
		musicListPreference.setEntries(entries);
		musicListPreference.setEntryValues(entryValues);
		musicListPreference.setDefaultValue(NO_SELECTION);
		musicListPreference.setTitle("Music List Title");
		musicListPreference.setSummary("Music List Summary");
		musicListPreference.setKey("MusicList.Key");
		preferenceRoot.addPreference(musicListPreference);
		
		return preferenceRoot;
	}*/
}
