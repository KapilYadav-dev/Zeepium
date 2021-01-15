package in.kay.zeepium.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import in.kay.zeepium.R;

public class Player extends AppCompatActivity {
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String url,title;
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        tvTitle=findViewById(R.id.title);
        tvTitle.setText(title);
        exoPlayerView = findViewById(R.id.exo_player_view);
        hideSystemUI();
        ExoPlayerLogic();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void ExoPlayerLogic() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        Uri videoURI = Uri.parse(url);
        String userAgent= Util.getUserAgent(this, this.getString(R.string.app_name));
        MediaSource mediaSource = new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(videoURI);
        exoPlayerView.setPlayer(exoPlayer);
        exoPlayerView.setKeepScreenOn(true);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_ENDED) {
                    Toast.makeText(Player.this, "Content has been ended. Hope you enjoyed well : )", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                } else if (playbackState == ExoPlayer.STATE_BUFFERING) {
                    findViewById(R.id.exo_player_progress_bar).setVisibility(View.VISIBLE);
                    findViewById(R.id.rl).setVisibility(View.GONE);

                } else if (!(playbackState == ExoPlayer.STATE_BUFFERING)) {
                    findViewById(R.id.exo_player_progress_bar).setVisibility(View.GONE);
                    findViewById(R.id.rl).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Toast.makeText(Player.this, "Error occurred : "+error, Toast.LENGTH_SHORT).show();
                Log.d("ERROREXO", "onPlayerError: "+error);
                onBackPressed();

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
    }
    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        releasePlayer();
        this.finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void Back(View view) {
        onBackPressed();
    }
}