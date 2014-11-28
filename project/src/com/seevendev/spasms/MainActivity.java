package com.seevendev.spasms;

import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ==============================================================
		// === VIEWS
		// ==============================================================
		
		final AutoCompleteTextView recipientTextView = (AutoCompleteTextView) findViewById(R.id.recipient);
		final EditText messageEditText = (EditText) findViewById(R.id.message);
		final Button sendButton = (Button) findViewById(R.id.send);
		final EditText iterationsEditText = (EditText) findViewById(R.id.iterations);
		
		// ==============================================================
		// === CONTACTS AUTOCOMPLETE
		// ==============================================================
		
		// Get a reference to the AutoCompleteTextView in the layout
		AutoCompleteTextView recipient = (AutoCompleteTextView) findViewById(R.id.recipient);
		
		// Get the Contacts numbers :
		Cursor contactCursor = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		contactCursor.moveToFirst();
		
		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> contactsAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1 //, contactsList
		);
		
		do {
			String num = contactCursor.getString(contactCursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
			contactsAdapter.add(num);
		} while (contactCursor.moveToNext());
		
		recipient.setAdapter(contactsAdapter);
		
		// ==============================================================
		// === SEND SMS
		// ==============================================================
		
		final SmsManager smsManager = SmsManager.getDefault();
		
		sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Number, message and iterations :
            	String num = recipientTextView.getText().toString();
            	String message = messageEditText.getText().toString();
            	int iterations = Integer.parseInt(iterationsEditText.getText().toString());
            	
            	// Send the SMS's :
            	for (int i = 0 ; i < iterations ; i++) {
            		smsManager.sendTextMessage(num, null, message, null, null);
            	}
            }
        });
		
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
}
