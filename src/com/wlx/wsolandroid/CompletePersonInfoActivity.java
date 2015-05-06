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
import android.view.inputmethod.InputMethodManager;
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
	private Button bt_loginOut;

	private ScrollView sv;

	private String facePath;
	private String faceUrl;
	private String faceName;

	private FinalBitmap finalBitmap;

	private NumberProgressBar numberProgressBar;
	private ProgressWheel progressWheel;

	public static final int FROM_REGIST = 0;
	public static final int FROM_MY_INFO = 1;
	public static final int FROM_PERSON_INFO = 2;
	private int fromWhere;

	private User user;

	/** 当fromWhere是FROM_MY_INFO时，判断当前是否可编辑状态 */
	private boolean isEditState = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_personinfo);

		fromWhere = getIntent().getIntExtra("fromWhere", FROM_REGIST);
		if (fromWhere == FROM_MY_INFO) {
			user = User.getCurrentUser(this, User.class);
		} else if (fromWhere == FROM_PERSON_INFO) {
			user = (User) getIntent().getSerializableExtra("user");
		}

		initActionBar();
		initView();

	}

	private void initActionBar() {
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		final MyActionBar actionBar = new MyActionBar(this);

		switch (fromWhere) {
		case FROM_REGIST:
			actionBar.setTitle("完善个人信息");
			actionBar.setLeftEnable(false);
			actionBar.setRightText("提交");
			actionBar.setRightClickListenner(new OnClickListener() {
				@Override
				public void onClick(View v) {
					submitTask();
				}
			});
			break;
		case FROM_MY_INFO:
			actionBar.setTitle(user.getNickName());
			actionBar.setLeftEnable(true);
			actionBar.setRightText("编辑");
			actionBar.setRightClickListenner(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isEditState) {
						isEditState = false;
						setEnableOrNot(false);
						actionBar.setRightText("编辑");
						submitTask();
					} else {
						isEditState = true;
						setEnableOrNot(true);
						actionBar.setRightText("完成");
					}
				}
			});
			break;
		case FROM_PERSON_INFO:
			actionBar.setTitle(user.getNickName());
			actionBar.setLeftEnable(true);
			break;
		}

		actionbar.addView(actionBar);

	}

	private void initView() {
		finalBitmap = FinalBitmap.create(this);

		sv = (ScrollView) findViewById(R.id.sv);
		Utils.setAppBackgroundColor(this, sv);
		et_nickName = (EditText) findViewById(R.id.et_nickName);
		et_gameName = (EditText) findViewById(R.id.et_gameName);
		et_qq = (EditText) findViewById(R.id.et_qq);
		et_tiebaName = (EditText) findViewById(R.id.et_tiebaName);
		et_city = (EditText) findViewById(R.id.et_city);
		iv_face = (ImageView) findViewById(R.id.iv_face);
		iv_face.setOnClickListener(this);
		bt_loginOut = (Button) findViewById(R.id.bt_loginout);
		bt_loginOut.setOnClickListener(this);
		if (fromWhere == FROM_MY_INFO) {
			bt_loginOut.setVisibility(View.VISIBLE);
		}

		numberProgressBar = (NumberProgressBar) findViewById(R.id.numberbar);
		progressWheel = (ProgressWheel) findViewById(R.id.progressWheel);
		progressWheel.spin();

		if (fromWhere == FROM_MY_INFO || fromWhere == FROM_PERSON_INFO) {
			setEnableOrNot(false);
			// 设置头像
			String trueFaceUrl = BmobProFile.getInstance(this).signURL(
					user.getFaceName(), user.getFaceUrl(),
					Constant.BMOB_ACCESSKEY, 0, null);
			finalBitmap.display(iv_face, trueFaceUrl);
			// 设置昵称
			et_nickName.setText(user.getNickName());
			// 设置游戏名称
			if (!TextUtils.isEmpty(user.getGameName())) {

				et_gameName.setText(user.getGameName());
			}
			// 设置QQ
			if (!TextUtils.isEmpty(user.getQq())) {

				et_qq.setText(user.getQq());
			}
			// 设置贴吧名称
			if (!TextUtils.isEmpty(user.getTiebaName())) {

				et_tiebaName.setText(user.getTiebaName());
			}
			// 设置所在城市
			if (!TextUtils.isEmpty(user.getCity())) {
				et_city.setText(user.getCity());
			}
		}
	}

	/** 设置控件是否可点击 */
	public void setEnableOrNot(boolean isEnable) {
		iv_face.setEnabled(isEnable);
		et_nickName.setEnabled(isEnable);
		et_gameName.setEnabled(isEnable);
		et_qq.setEnabled(isEnable);
		et_tiebaName.setEnabled(isEnable);
		et_city.setEnabled(isEnable);

	}

	@Override
	public void onClick(View v) {
		if (v == iv_face) {
			takePhoto();
		} else if (v == bt_loginOut) {
			BmobUser.logOut(this); // 清除缓存用户对象
			finish();
			startActivity(new Intent(CompletePersonInfoActivity.this,LoginActivity.class));
			Intent mIntent = new Intent(
					MainActivity.ACTION_FINISH_MAIN_ACTIVITY);
			// 发送广播,把MainActivity关闭
			sendBroadcast(mIntent);
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
						CompletePersonInfoActivity.this).signURL(faceName,
						faceUrl, Constant.BMOB_ACCESSKEY, 0, null);
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
		newUser.setQq(et_qq.getText().toString());
		newUser.setTiebaName(et_tiebaName.getText().toString());
		newUser.setCity(et_city.getText().toString());
		newUser.setGameName(et_gameName.getText().toString());

		BmobUser bmobUser = BmobUser.getCurrentUser(this);
		newUser.update(this, bmobUser.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				progressWheel.setVisibility(View.GONE);
				Toast.makeText(CompletePersonInfoActivity.this, "提交个人信息成功",
						Toast.LENGTH_LONG).show();
				if (fromWhere == FROM_REGIST) {
					startActivity(new Intent(CompletePersonInfoActivity.this,
							MainActivity.class));
					finish();
				}
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
