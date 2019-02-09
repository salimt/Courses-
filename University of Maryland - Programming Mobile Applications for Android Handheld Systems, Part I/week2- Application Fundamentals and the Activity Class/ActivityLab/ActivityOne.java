package course.labs.activitylab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import course.labs.activitylab.R;

public class ActivityOne extends Activity {
	// Use these as keys when you're saving state between reconfigurations
	private static final String RESTART_KEY = "restart";
	private static final String RESUME_KEY = "resume";
	private static final String START_KEY = "start";
	private static final String CREATE_KEY = "create";
	// String for LogCat documentation
	private final static String TAG = "Lab-ActivityOne";
	// Lifecycle counters
	private int mCreate = 0, mRestart = 0, mStart = 0, mResume = 0;
	// displaying the current count of each counter variable
	public TextView mTvRestart, mTvCreate, mTvStart, mTvResume;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one);

		// appropriate TextViews to the TextView variables
		mTvCreate  = (TextView) findViewById(R.id.create);
		mTvRestart = (TextView) findViewById(R.id.restart);
		mTvStart   = (TextView) findViewById(R.id.start);
		mTvResume  = (TextView) findViewById(R.id.resume);

		Button launchActivityTwoButton = (Button) findViewById(R.id.bLaunchActivityTwo);
		launchActivityTwoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(ActivityOne.this, ActivityTwo.class);
				// Launch the Activity using the intent
				ActivityOne.this.startActivity(myIntent);
			}
		});

		// Has previous state been saved?
		if (savedInstanceState != null) {
			mCreate = savedInstanceState.getInt(CREATE_KEY);
			mRestart = savedInstanceState.getInt(RESTART_KEY);
			mStart = savedInstanceState.getInt(START_KEY);
			mResume = savedInstanceState.getInt(RESUME_KEY);
		}
		// Emit LogCat message
		Log.i(TAG, "Entered the onCreate() method");
		mCreate++;
		displayCounts();
	}

	// Lifecycle callback overrides

	@Override
	public void onStart() {
		super.onStart();
		// Emit LogCat message
		Log.i(TAG, "Entered the onStart() method");
		mStart++;
		displayCounts();
	}

	@Override
	public void onResume() {
		super.onResume();
		// Emit LogCat message
		Log.i(TAG, "Entered the onResume() method");
		mResume++;
		displayCounts();
	}

	@Override
	public void onPause() {
		super.onPause();
		// Emit LogCat message
		Log.i(TAG, "Entered the onPause() method");
	}

	@Override
	public void onStop() {
		super.onStop();
		// Emit LogCat message
		Log.i(TAG, "Entered the onStop() method");
	}

	@Override
	public void onRestart() {
		super.onRestart();
		// Emit LogCat message
		Log.i(TAG, "Entered the onRestart() method");
		mRestart++;
		displayCounts();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Emit LogCat message
		Log.i(TAG, "Entered the onDestroy() method");
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(CREATE_KEY, mCreate);
		savedInstanceState.putInt(START_KEY, mStart);
		savedInstanceState.putInt(RESUME_KEY, mResume);
		savedInstanceState.putInt(RESTART_KEY, mRestart);
	}

	// Updates the displayed counters
	public void displayCounts() {
		mTvCreate.setText("onCreate() calls: " + mCreate);
		mTvStart.setText("onStart() calls: " + mStart);
		mTvResume.setText("onResume() calls: " + mResume);
		mTvRestart.setText("onRestart() calls: " + mRestart);
	}
}
