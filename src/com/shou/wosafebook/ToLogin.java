package com.shou.wosafebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.shou.wosafebook.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class ToLogin extends Activity {
	private ViewPager viewpager;
	private View view1, view2, view3;
	private List<View> listview;
	private PagerAdapter adapter;
	private ImageButton btn_login;

	private static final String[] AVATARS = {
			"http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
			"http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
			"http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
			"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
			"http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
			"http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
			"http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
			"http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
			"http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
			"http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
			"http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
			"http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg" };

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			String phone = (String) msg.obj;
			SharedPreferences share = getSharedPreferences("user",
					Activity.MODE_PRIVATE);
			// 获取Acitvity的 SharedPreferences
			// 对象（文件名，模式）

			SharedPreferences.Editor edit = share.edit();
			// 获得对应Sharedpreferences 的 edit

			edit.putString("imei", phone);
			// 通过edit 存入数据

			edit.commit(); // 提交

			Intent intent = new Intent();
			intent.setClass(ToLogin.this, MainView.class);
			intent.putExtra("type", false);
			ToLogin.this.startActivity(intent);
			ToLogin.this.finish();
		}

	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tologin);

		viewpager = (ViewPager) findViewById(R.id.viewpager2);

		LayoutInflater inflater = getLayoutInflater();

		view1 = inflater.inflate(R.layout.view1, null);
		view2 = inflater.inflate(R.layout.view2, null);
		view3 = inflater.inflate(R.layout.view3, null);
		btn_login = (ImageButton) view3.findViewById(R.id.login_btn);

		listview = new ArrayList<View>();
		listview.add(view1);
		listview.add(view2);
		listview.add(view3);
		adapter = new MyPagerAdapter(listview);

		viewpager.setAdapter(adapter);

		btn_login.setOnClickListener(new MyOnClickListener());
	}

	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.login_btn) {
				System.out.println("121212121212121212");

				SMSSDK.initSDK(ToLogin.this, "7746768bc92b",
						"d21653b137f6c784131e3a305a19a16c");
				// 打开注册页面

				System.out.println("121212121212121212");

				RegisterPage registerPage = new RegisterPage();

				System.out.println("121212121212121212");

				registerPage.setRegisterCallback(new EventHandler() {
					public void afterEvent(int event, int result, Object data) {
						// 解析注册结果
						if (result == SMSSDK.RESULT_COMPLETE) {
							@SuppressWarnings("unchecked")
							HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
							String country = (String) phoneMap.get("country");
							String phone = (String) phoneMap.get("phone");
							// 提交用户信息
							registerUser(country, phone);
							System.out.println("dsjfksdfjkasdhfgas" + phone);

							// ////////////////
							TelephonyManager tm = (TelephonyManager) ToLogin.this
									.getSystemService(Context.TELEPHONY_SERVICE);
							String imei = tm.getSimSerialNumber();// 获得SIM卡的序号
							// /////////////////////

							new MyThread(phone, imei).start();

						}
					}
				});
				registerPage.show(ToLogin.this);
			}

		}

	}

	// 提交用户信息
	private void registerUser(String country, String phone) {
		Random rnd = new Random();
		int id = Math.abs(rnd.nextInt());
		String uid = String.valueOf(id);
		String nickName = "SmsSDK_User_" + uid;
		String avatar = AVATARS[id % 12];
		SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
	}

	class MyThread extends Thread {
		private String phone, imei;

		public MyThread(String phone, String imei) {
			this.phone = phone;
			this.imei = imei;
		}

		@Override
		public void run() {
			SoapObject request = new SoapObject("http://ibook.wosafe.com/",
					"loginUser");
			request.addProperty("in0", phone);
			request.addProperty("in1", imei);
			request.addProperty("in2", null);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = false;
			envelope.bodyOut = request;
			String url = "http://120.27.54.210:8091/XFirWosafebook/services/UserService";
			HttpTransportSE ht = new HttpTransportSE(url);
			try {
				ht.call(null, envelope);
				if (envelope.getResponse() != null) {
					// 第6步：使用getResponse方法获得WebService方法的返回结果
					Object object = (Object) envelope.getResponse();

					// 通过getProperty方法获得Product对象的属性值
					String result1 = object.toString();
					if (result1.trim().equals("success")) {

						Message msg = new Message();
						msg.obj = imei;
						handler.handleMessage(msg);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
