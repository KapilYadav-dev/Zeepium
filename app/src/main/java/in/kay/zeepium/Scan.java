package in.kay.zeepium;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

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
                Toast.makeText(this, "From is " + responseModel.from, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Player.class));
                finish();
            } catch (JsonParseException e) {
                Toast.makeText(this, "Our app only work with our chrome extension...", Toast.LENGTH_SHORT).show();
                finish();
            }

        }));
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
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