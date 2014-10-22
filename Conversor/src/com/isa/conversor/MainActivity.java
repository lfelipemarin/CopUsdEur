package com.isa.conversor;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private static final String API_URL = "http://openexchangerates.org/api/latest.json?app_id=95d7dec69d3a44938deadfc80a9f2b8c";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnConvert = (Button) findViewById(R.id.buttonConv);

		final EditText inputCop = (EditText) findViewById(R.id.editTextCop);
		final EditText viewUsds = (EditText) findViewById(R.id.editTextUsds);
		final EditText viewEurs = (EditText) findViewById(R.id.editTextEurs);

		btnConvert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!inputCop.getText().toString().equals("")) {
					AsyncHttpClient client = new AsyncHttpClient();
					client.get(API_URL, new AsyncHttpResponseHandler() {

						@Override
						public void onFailure(Throwable error, String content) {
							// TODO Auto-generated method stub
							super.onFailure(error, content);
						}

						@Override
						public void onSuccess(String content) {
							Log.i("CONVERSOR", "Exito HTTP");
							try {
								JSONObject jsonObj = new JSONObject(content);
								JSONObject jsonRates = jsonObj
										.getJSONObject("rates");

								Double jsonUsd = jsonRates.getDouble("USD");
								Double jsonEur = jsonRates.getDouble("EUR");
								Double jsonCop = jsonRates.getDouble("COP");

								Double usd = jsonUsd / jsonCop;
								Double eur = jsonEur / jsonCop;

								Log.i("CONVERSOR", "USD: " + jsonUsd);
								Log.i("CONVERSOR", "EUR: " + jsonEur);
								Log.i("CONVERSOR", "COP: " + jsonCop);

								Double cops = Double.valueOf(inputCop.getText()
										.toString());
								Double usds = usd * cops;
								Double euros = eur * cops;
								viewUsds.setText(usds.toString());
								viewEurs.setText(euros.toString());

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							super.onFinish();
						}

						@Override
						public void onStart() {
							// TODO Auto-generated method stub
							super.onStart();
						}

					});
				} else {
					Toast.makeText(getApplicationContext(),
							"Por favor ingrese un valor en pesos colombianos ",
							Toast.LENGTH_LONG).show();
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
