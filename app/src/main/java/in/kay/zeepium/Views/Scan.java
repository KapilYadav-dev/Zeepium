package in.kay.zeepium.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.kay.zeepium.Api.RetrofitClient;
import in.kay.zeepium.Model.ResponseModel;
import in.kay.zeepium.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scan extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> Scan.this.runOnUiThread(() -> {
            Progress();
            Gson gson = new Gson();
            ResponseModel responseModel;
            try {
                responseModel = gson.fromJson(String.valueOf(result), ResponseModel.class);
                DoWork(responseModel.getUrl(), responseModel.getTitle());
            } catch (JsonParseException e) {
                Toast.makeText(this, "Our app only work with our chrome extension...", Toast.LENGTH_SHORT).show();
                finish();
            }

        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    private void Progress() {
        progressBar = findViewById(R.id.pb);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void DoWork(String url, String title) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().search(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    ResponseModel responseModel;
                    String json = null;
                    try { json=response.body().string();
                    } catch (IOException e) { e.printStackTrace(); }
                    responseModel = gson.fromJson(json, ResponseModel.class);
                    String id = responseModel.getId();
                    SaveData(id, url, title, getDate());
                    String streamUrl = responseModel.getUrl();
                    Intent intent = new Intent(Scan.this, Player.class);
                    intent.putExtra("url", streamUrl);
                    intent.putExtra("title", title);
                    startActivity(intent);
                    finish();
                } else if (response.code() == 404) {
                    Toast.makeText(Scan.this, "Desired content isn't swappable : (", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            private String getDate() {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM");
                Date date = new Date();
                return dateFormat.format(date);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Scan.this, "Server is down...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void SaveData(String id, String url, String title, String date) {
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}