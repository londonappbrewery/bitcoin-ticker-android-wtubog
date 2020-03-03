package com.londonappbrewery.bitcointicker;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.protocol.RequestDefaultHeaders;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    private final String PUBLIC_KEY = "MjNmODVlMTFmOTAzNDMzMGFhNGU1NTcyNjJmZWJlNDQ";

    // Member Variables:
    TextView mPriceTextView;

    ArrayAdapter<CharSequence> mAdapter;

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        mAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(mAdapter);

        // TODO: Set an OnItemSelected listener on the spinner

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Bitcoin Ticker", String.valueOf(adapterView.getItemAtPosition(i)));

                letsDoSomeNetworking(String.valueOf(adapterView.getItemAtPosition(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pb = findViewById(R.id.indeterminateBar);

    }

    @Override
    protected void onResume() {
        super.onResume();

        letsDoSomeNetworking("AUD");
    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(final String currency) {
        mPriceTextView.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);
        String url = BASE_URL + currency;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-ba-key", PUBLIC_KEY);

        client.get(url, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                BtcDataModel btcData = BtcDataModel.fromJson(response);
                DecimalFormat formatter = new DecimalFormat("#,###,###");
//                Log.i("Bitcoin", btcData.getmPrice());
                mPriceTextView.setText(formatter.format(btcData.getmPrice()) + " " + currency);
                mPriceTextView.setVisibility(View.VISIBLE);
                
                pb.setVisibility(View.GONE);
            }
        });

    }


}
