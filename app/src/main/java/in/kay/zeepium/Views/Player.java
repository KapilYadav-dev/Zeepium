package in.kay.zeepium.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import in.kay.zeepium.R;

public class Player extends AppCompatActivity {
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }
}