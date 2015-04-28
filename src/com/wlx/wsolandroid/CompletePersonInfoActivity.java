package com.wlx.wsolandroid;

import java.io.File;
import java.util.UUID;

import net.tsz.afinal.FinalBitmap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.LocalThumbnailListener;
import com.bmob.btp.callback.UploadListener;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.User;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;
import com.wlx.wsolandroid.widget.NumberProgressBar;
import com.wlx.wsolandroid.widget.ProgressWheel;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * 完善个人信息
 * 
 * @author wanglixin
 * 
 */
public class CompletePersonInfoActivity extends Activity implements
		OnClickListener {
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final int ACTIVITY_PICTURE_SELECT = 1;
	public static final int ACTIVITY_PICTURE_CROP = 2;
	public static final int PHOTO_WIDTH = 180;
	public static final int PHOTO_HEIGHT = 180;
	private ImageView iv_face;
	private EditText et_nickName;
	private EditText et_qq;
	private EditText et_tiebaName;
	private EditText et_city;
	private EditText et_gameName;

	private ScrollView sv;

	private String facePath;
	private String faceUrl;
	private String faceName;

	private FinalBitmap finalBitmap;

	private NumberProgressBar numberProgressBar;
	private ProgressWheel progressWheel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_personinfo);

		initActionBar();
		initView();
		finalBitmap = FinalBitmap.create(this);
		
	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("完善个人信息");
		actionBar.setLeftEnable(false);
		actionBar.setRightText("提交");
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
		actionBar.setRightClickListenner(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submitTask();
			}
		});
	}

	private void initView() {
		sv = (ScrollView) findViewById(R.id.sv);
		Utils.setAppBackgroundColor(this, 1, sv);
		et_nickName = (EditText) findViewById(R.id.et_nickName);
		et_gameName = (EditText) findViewById(R.id.et_gameName);
		et_qq = (EditText) findViewById(R.id.et_qq);
		et_tiebaName = (EditText) findViewById(R.id.et_tiebaName);
		et_city = (EditText) findViewById(R.id.et_city);
		iv_face = (ImageView) findViewById(R.id.iv_face);
		iv_face.setOnClickListener(this);
		
		numberProgressBar = (NumberProgressBar) findViewById(R.id.numberbar);
		progressWheel = (ProgressWheel) findViewById(R.id.progressWheel);
		progressWheel.spin();
	}

	@Override
	public void onClick(View v) {
		if (v == iv_face) {
			takePhoto();
		}
	}

	private void submitTask() {
		if (TextUtils.isEmpty(et_nickName.getText().toString())) {
			Toast.makeText(this, "请输入昵称", Toast.LENGTH_LONG).show();
			return;
		}
		updateUser();
	}

	// 本地图片
	protected void takePhoto() {
		Intent intent_select_img = new Intent(Intent.ACTION_PICK);
		intent_select_img.setType(IMAGE_UNSPECIFIED);
		// intent_select_img.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent_select_img, ACTIVITY_PICTURE_SELECT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case ACTIVITY_PICTURE_SELECT:
				if (data != null) {
					Uri uri = data.getData();
					String path = Utils.selectImage(this, uri);
					File f = new File(path);
					if (!f.canRead()) {
						Toast.makeText(CompletePersonInfoActivity.this,
								"没有权限读取该图片", Toast.LENGTH_LONG).show();
						setResult(RESULT_CANCELED);
						finish();
						return;
					}
					// startPhotoZoom(Uri.fromFile(f));
					// //不裁剪，直接使用缩略图。
					// getThumbnail(Uri.fromFile(f));
					createThumbnailFace(path);
				}
				break;
			}
		}
	}

	/** 挑选图库中的图片后创建200*200的缩略图 */
	private void createThumbnailFace(String path) {
		BmobProFile.getInstance(CompletePersonInfoActivity.this)
				.getLocalThumbnail(path, 6, 200, 200,
						new LocalThumbnailListener() {
							@Override
							public void onError(int statuscode, String errormsg) {
								System.out.println("本地缩略图创建失败 :" + statuscode
										+ "," + errormsg);
							}

							@Override
							public void onSuccess(String thumbnailPath) {
								System.out.println("本地缩略图创建成功  :"
										+ thumbnailPath);
								facePath = thumbnailPath;
								upLoadFace();
							}
						});
	}

	/** 把本地生成的头像缩略图上传到服务器 */
	private void upLoadFace() {
		numberProgressBar.setVisibility(View.VISIBLE);
		numberProgressBar.setProgress(0);
		BTPFileResponse response = BmobProFile.getInstance(this).upload(
				facePath, new UploadListener() {

					@Override
					public void onSuccess(String fileName, String url) {
						faceUrl = url;
						faceName = fileName;
						updateUserFace(faceName, faceUrl);												
					}

					@Override
					public void onProgress(int ratio) {
						numberProgressBar.setProgress(ratio);

					}

					@Override
					public void onError(int statuscode, String errormsg) {
						numberProgressBar.setVisibility(View.GONE);
						Toast.makeText(CompletePersonInfoActivity.this,
								"上传头像出错：" + errormsg, Toast.LENGTH_LONG).show();
					}
				});
	}

	/** 更新用户表中的头像信息 */
	private void updateUserFace(final String faceName, final String faceUrl) {
		User newUser = new User();

		newUser.setFaceName(faceName);
		newUser.setFaceUrl(faceUrl);

		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		newUser.update(this, bmobUser.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(CompletePersonInfoActivity.this, "上传头像成功",
						Toast.LENGTH_LONG).show();
				String trueFaceUrl = BmobProFile.getInstance(
						CompletePersonInfoActivity.this).signURL(
						faceName, faceUrl, Constant.BMOB_ACCESSKEY, 0,
						null);
				finalBitmap.display(iv_face, trueFaceUrl);
				numberProgressBar.setVisibility(View.GONE);
			}

			@Override
			public void onFailure(int code, String msg) {
				Toast.makeText(CompletePersonInfoActivity.this,
						"上传头像失败：" + msg, Toast.LENGTH_LONG).show();
			}
		});
	}

	/** 更新用户信息 */
	private void updateUser() {
		progressWheel.setVisibility(View.VISIBLE);
		User newUser = new User();
		newUser.setNickName(et_nickName.getText().toString());		
		if (!TextUtils.isEmpty(et_qq.getText().toString())) {
			newUser.setQq(et_qq.getText().toString());
		}
		if (!TextUtils.isEmpty(et_tiebaName.getText().toString())) {
			newUser.setTiebaName(et_tiebaName.getText().toString());
		}
		if (!TextUtils.isEmpty(et_city.getText().toString())) {
			newUser.setCity(et_city.getText().toString());
		}
		if (!TextUtils.isEmpty(et_gameName.getText().toString())) {
			newUser.setGameName(et_gameName.getText().toString());
		}

		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		newUser.update(this, bmobUser.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				progressWheel.setVisibility(View.GONE);
				Toast.makeText(CompletePersonInfoActivity.this, "完善个人信息成功",
						Toast.LENGTH_LONG).show();
				startActivity(new Intent(CompletePersonInfoActivity.this,
						MainActivity.class));
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				progressWheel.setVisibility(View.GONE);
				Toast.makeText(CompletePersonInfoActivity.this,
						"提交个人信息失败：" + msg, Toast.LENGTH_LONG).show();
			}
		});
	}

}
