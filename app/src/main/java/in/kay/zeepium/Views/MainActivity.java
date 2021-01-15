package in.kay.zeepium.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import in.kay.zeepium.Adapter.MyAdapter;
import in.kay.zeepium.Model.ResponseModel;
import in.kay.zeepium.R;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    ConstraintLayout cl;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initz();
        ReadData();
    }

    private void Initz() {
        rv = findViewById(R.id.rv);
        cl = findViewById(R.id.cl);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void ReadData() {
        List<ResponseModel> list = Paper.book().read("History");
        if (list == null) empty();
        else {
            Show(list);
        }
    }

    private void Show(List<ResponseModel> list) {
        Collections.reverse(list);
        rv.setVisibility(View.VISIBLE);
        cl.setVisibility(View.GONE);
        adapter = new MyAdapter(list, this);
        rv.setAdapter(adapter);
    }

    private void empty() {
        rv.setVisibility(View.GONE);
        cl.setVisibility(View.VISIBLE);
    }

    public void GotoScan(View view) {
        startActivity(new Intent(this, Scan.class));
    }
}