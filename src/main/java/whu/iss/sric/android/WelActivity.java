package whu.iss.sric.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yongchun.library.view.ImageSelectorActivity;

import java.util.ArrayList;

import whu.iss.sric.view.GameView;
import whu.iss.sric.view.OnStateListener;
import whu.iss.sric.view.OnTimerListener;
import whu.iss.sric.view.OnToolsChangeListener;

public class WelActivity extends Activity
	implements OnClickListener,OnTimerListener,OnStateListener,OnToolsChangeListener{
	
	private ImageButton btnPlay;
	private ImageButton btnRefresh;
	private ImageButton btnTip;
	private ImageView imgTitle;
	private GameView gameView;
	private SeekBar progress;
	private MyDialog dialog;
	private TextView textRefreshNum;
	private TextView textTipNum;
	
	private MediaPlayer player;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				dialog = new MyDialog(WelActivity.this,gameView,"You Win!",gameView.getTotalTime() - progress.getProgress());
				dialog.show();
				break;
			case 1:
				dialog = new MyDialog(WelActivity.this,gameView,"You Lose!",gameView.getTotalTime() - progress.getProgress());
				dialog.show();
			}
		}
	};

	@SuppressLint("NewApi")
	private void requestReadExternalPermission() {
		if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {

			if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

			} else {
				// 0 是自己定义的请求code
				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
			}
		} else {
		}
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        btnPlay = (ImageButton) findViewById(R.id.play_btn);
        btnRefresh = (ImageButton) findViewById(R.id.refresh_btn);
        btnTip = (ImageButton) findViewById(R.id.tip_btn);
        imgTitle = (ImageView) findViewById(R.id.title_img);
        gameView = (GameView) findViewById(R.id.game_view);
        progress = (SeekBar) findViewById(R.id.timer);
        textRefreshNum = (TextView) findViewById(R.id.text_refresh_num);
        textTipNum = (TextView) findViewById(R.id.text_tip_num);

        progress.setMax(gameView.getTotalTime());
        
        btnPlay.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        btnTip.setOnClickListener(this);
        gameView.setOnTimerListener(this);
        gameView.setOnStateListener(this);
        gameView.setOnToolsChangedListener(this);
        GameView.initSound(this);
        
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale_anim);
        imgTitle.startAnimation(scale);
        btnPlay.startAnimation(scale);
        
        player = MediaPlayer.create(this, R.raw.bg);
        player.setLooping(true);
        player.start();
        
//        GameView.soundPlay.play(GameView.ID_SOUND_BACK2BG, -1);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)  //判断数据是否为空，就可以解决这个问题
			return;
		if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
			ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
			gameView.setImages(images);
			Animation scaleOut = AnimationUtils.loadAnimation(this,R.anim.scale_anim_out);
			Animation transIn = AnimationUtils.loadAnimation(this,R.anim.trans_in);

			btnPlay.startAnimation(scaleOut);
			btnPlay.setVisibility(View.GONE);
			imgTitle.setVisibility(View.GONE);
			gameView.setVisibility(View.VISIBLE);

			btnRefresh.setVisibility(View.VISIBLE);
			btnTip.setVisibility(View.VISIBLE);
			progress.setVisibility(View.VISIBLE);
			textRefreshNum.setVisibility(View.VISIBLE);
			textTipNum.setVisibility(View.VISIBLE);

			btnRefresh.startAnimation(transIn);
			btnTip.startAnimation(transIn);
			gameView.startAnimation(transIn);
			player.pause();
			gameView.startPlay();
		}
	}

    @Override
    protected void onPause() {
    	super.onPause();
    	gameView.setMode(GameView.PAUSE);
    }
    
    @Override
	protected void onDestroy() {
    	super.onDestroy();
    	gameView.setMode(GameView.QUIT);
	}

	@Override
	public void onClick(View v) {
    	
    	switch(v.getId()){
    	case R.id.play_btn:
			ImageSelectorActivity.start(WelActivity.this, 18, ImageSelectorActivity.MODE_MULTIPLE, true,true,false);
    		break;
    	case R.id.refresh_btn:
    		Animation shake01 = AnimationUtils.loadAnimation(this,R.anim.shake);
    		btnRefresh.startAnimation(shake01);
    		gameView.refreshChange();
    		break;
    	case R.id.tip_btn:
    		Animation shake02 = AnimationUtils.loadAnimation(this,R.anim.shake);
    		btnTip.startAnimation(shake02);
    		gameView.autoClear();
    		break;
    	}
	}

	@Override
	public void onTimer(int leftTime) {
		Log.i("onTimer", leftTime+"");
		progress.setProgress(leftTime);
	}

	@Override
	public void OnStateChanged(int StateMode) {
		switch(StateMode){
		case GameView.WIN:
			handler.sendEmptyMessage(0);
			break;
		case GameView.LOSE:
			handler.sendEmptyMessage(1);
			break;
		case GameView.PAUSE:
			player.stop();
	    	gameView.player.stop();
	    	gameView.stopTimer();
			break;
		case GameView.QUIT:
			player.release();
	    	gameView.player.release();
	    	gameView.stopTimer();
	    	break;
		}
	}

	@Override
	public void onRefreshChanged(int count) {
		textRefreshNum.setText(""+gameView.getRefreshNum());
	}

	@Override
	public void onTipChanged(int count) {
		textTipNum.setText(""+gameView.getTipNum());
	}
	
	public void quit(){
		this.finish();
	}
}