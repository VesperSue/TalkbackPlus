package pcg.talkbackplus.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

//import com.tencent.bugly.crashreport.//CrashReport;

import java.io.File;

import pcg.talkbackplus.R;


public class MainActivity extends Activity {

	public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;
	public static DisplayMetrics display_metrics;

	@TargetApi(23)
	public void testOverlayPermission() {
		if (!Settings.canDrawOverlays(this)) {
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					Uri.parse("package:" + getPackageName()));
			startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
		}
	}

	@TargetApi(23)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
			if (Settings.canDrawOverlays(this)) {
				System.out.println("WowICan");
			}
		} else {
			System.out.println("Intent");
			display_metrics = new DisplayMetrics();

			final Context context = this;
			final View upper_view = new View(context);
			final View bottom_view = new View(context);

		}
	}

	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			"android.permission.READ_EXTERNAL_STORAGE",
			"android.permission.WRITE_EXTERNAL_STORAGE" };


	public static void verifyStoragePermissions(Activity activity) {

		try {
			//检测是否有写的权限
			int permission = ActivityCompat.checkSelfPermission(activity,
					"android.permission.WRITE_EXTERNAL_STORAGE");
			if (permission != PackageManager.PERMISSION_GRANTED) {
				// 没有写的权限，去申请写的权限，会弹出对话框
				ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//CrashReport.postCatchedException(e);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		verifyStoragePermissions(this);
		// SophixManager.getInstan		// ce().queryAndLoadNewPatch();
		setContentView(R.layout.activity_main);

		if (Build.VERSION.SDK_INT >= 23){
			testOverlayPermission();
		}

		this.findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO upload
			}
		});

		this.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
			}
		});
		String dir;
		File externalFilesDir = getExternalFilesDir(null);
		if (externalFilesDir != null)
		{
			dir = externalFilesDir.getAbsolutePath() + "/screenshots/";
			((TextView) this.findViewById(R.id.path)).setText(dir);
			final File file = new File(dir);
			if(file.listFiles()!=null)
				((TextView) this.findViewById(R.id.recordNumber)).setText(String.valueOf(file.listFiles().length));
			else
			    ((TextView) this.findViewById(R.id.recordNumber)).setText(String.valueOf(0));
		}
	}

    @Override
    protected void onResume() {
		super.onResume();
		this.findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO upload
			}
		});

		this.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
			}
		});

		String dir;
		File externalFilesDir = getExternalFilesDir(null);
		if (externalFilesDir != null)
		{
			dir = externalFilesDir.getAbsolutePath() + "/screenshots/";
			((TextView) this.findViewById(R.id.path)).setText(dir);
			final File file = new File(dir);
            if(file.listFiles()!=null)
                ((TextView) this.findViewById(R.id.recordNumber)).setText(String.valueOf(file.listFiles().length));
            else
                ((TextView) this.findViewById(R.id.recordNumber)).setText(String.valueOf(0));

		}
	}

}
