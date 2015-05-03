// @author  Askao Ahmed Saad 3/5/2015

package com.datetimecountdown;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.datetime.R;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView days_txt, hours_txt, minutes_txt, seconds_txt, title_txt;
	private Handler customHandler = new Handler(); // handler for repeating
													// thread every certain time
	String targetime_date_string = "Aug 7, 2016 09:00:00 PM"; // put here the
																// target
																// datetime
	SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy h:mm:ss a",
			Locale.ENGLISH);// set datetime format due to previous string
							// datetime
	Date target_date = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		days_txt = (TextView) findViewById(R.id.days_txt);
		hours_txt = (TextView) findViewById(R.id.hours_txt);
		minutes_txt = (TextView) findViewById(R.id.minutes_txt);
		seconds_txt = (TextView) findViewById(R.id.seconds_txt);
		title_txt = (TextView) findViewById(R.id.title_txt);

		new SetDateTime().run(); // execute SetDateTime thread

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class SetDateTime implements Runnable {

		@Override
		public void run() {
			Date now = new Date(); // get datetime now
			try {
				target_date = sdf.parse(targetime_date_string);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			long differenceInMillis = target_date.getTime() - now.getTime();

			// calculate D/H/M/S
			long diffDays = differenceInMillis / (24 * 60 * 60 * 1000); // we
																		// divide
																		// by
																		// (24h
																		// * 60m
																		// * 60s
																		// *
																		// 1000ms)
			long modulus_Days = differenceInMillis % (24 * 60 * 60 * 1000);
			long diffHours = modulus_Days / (60 * 60 * 1000);
			long modulus_Hours = modulus_Days % (60 * 60 * 1000);
			long diffMiutes = modulus_Hours / (60 * 1000);
			long modulus_Miutes = modulus_Hours % (60 * 1000);
			long diffSeconds = modulus_Miutes / (1000);

			// set D/H/M/S to textviews
			days_txt.setText("" + diffDays);
			hours_txt.setText("" + diffHours);
			minutes_txt.setText("" + diffMiutes);
			seconds_txt.setText("" + diffSeconds);

			if (diffDays <= 0 && diffHours <= 0 && diffMiutes <= 0
					&& diffSeconds <= 0) {
				title_txt.setText("Done !");
			}

			// repeat calculation every 1 second
			customHandler.postDelayed(this, 1000);
		}
	};

}
