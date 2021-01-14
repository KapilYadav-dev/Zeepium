package in.kay.zeepium.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;

import in.kay.zeepium.Api.RetrofitClient;
import in.kay.zeepium.Model.ResponseModel;
import in.kay.zeepium.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scan extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> Scan.this.runOnUiThread(() -> {
            Gson gson = new Gson();
            ResponseModel responseModel;
            try {
                responseModel = gson.fromJson(String.valueOf(result), ResponseModel.class);
                DoWork(responseModel.getUrl(),responseModel.getTitle(),responseModel.getDate());
            } catch (JsonParseException e) {
                Toast.makeText(this, "Our app only work with our chrome extension...", Toast.LENGTH_SHORT).show();
                finish();
            }

        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
    }

    private void DoWork(String url, String title, String date) {
        Call<ResponseBody> call= RetrofitClient.getInstance().getApi().search(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    SaveData(url,title,date);
                    Toast.makeText(Scan.this, "Response is "+response.body().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Scan.this, "Error is "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Scan.this, "Fail is "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SaveData(String url, String title, String date) {
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