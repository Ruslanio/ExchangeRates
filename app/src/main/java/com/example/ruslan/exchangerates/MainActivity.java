package com.example.ruslan.exchangerates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruslan.exchangerates.api.ApiRequester;
import com.example.ruslan.exchangerates.api.LogTransformer;
import com.example.ruslan.exchangerates.api.pojo.BaseRate;
import com.example.ruslan.exchangerates.api.pojo.Rates;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etDate;
    private Button btnSend;
    private TextView tvOutput;
    private ProgressBar progressBar;

    private static final String DATE_REGEX = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    private ApiRequester apiRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiRequester = ApiRequester.getInstance();

        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        etDate = (EditText) findViewById(R.id.et_date);
        tvOutput = (TextView) findViewById(R.id.tv_exchange_rates);

        progressBar = (ProgressBar) findViewById(R.id.pg);
    }

    @Override
    public void onClick(View v) {
        String date = etDate.getText().toString();

        if (!date.matches(DATE_REGEX)) {
            Toast.makeText(this, "Date format must be YYYY-MM-DD", Toast.LENGTH_SHORT).show();
            return;
        }

        apiRequester.getRates(date)
//                .compose(new LogTransformer<>())
                .map(BaseRate::getRates)
//                .doOnSubscribe(() -> {showProgress(true);})
//                .doAfterTerminate(() -> {showProgress(false);})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showRates, throwable -> showError(throwable));
    }

    private void showRates(Rates rates) {
        String output = "for EUR:" + "\n" + "USD : " + rates.getUSD().toString() + "\n" + "SEK : " + rates.getSEK();
        tvOutput.setText(output);
    }

    private static final String TAG_ERROR = "error";

    private void showError(Throwable e) {
        Toast.makeText(this, "something wrong with the connection", Toast.LENGTH_SHORT).show();
        Log.i(TAG_ERROR, e.getMessage());
    }

}
