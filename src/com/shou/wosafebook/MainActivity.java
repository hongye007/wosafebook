package com.shou.wosafebook;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.shou.wosafebook.R;

public class MainActivity extends Activity {

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Intent intent = new Intent();
			System.out.println("11222222236qwewewew2222" + msg.what);
			switch (msg.what) {
			case 1:
				System.out.println("1111122222222222222");
				intent.putExtra("type", true);
				intent.setClass(MainActivity.this, MainView.class);

				break;
			case 2:
				intent.setClass(MainActivity.this, ToLogin.class);
				break;

			}
			MainActivity.this.startActivity(intent);
			MainActivity.this.finish();
		}

	};

	private void netState() {
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {

			TelephonyManager tm = (TelephonyManager) MainActivity.this
					.getSystemService(Context.TELEPHONY_SERVICE);
			String tel = tm.getLine1Number().replaceAll("//+86", " ").trim();// 获取本机号码
			String imei = tm.getSimSerialNumber();// 获得SIM卡的序号

			SharedPreferences share = MainActivity.this.getSharedPreferences(
					"user", Activity.MODE_PRIVATE);
			String str = share.getString("imei", "no_login");
			System.out.println(str);

			if (str.equals("no_login")) {
				new MyThread(tel, imei).start();
			} else if (imei.equals(str)) {
				Intent intent = new Intent(MainActivity.this, MainView.class);
				intent.putExtra("type", true);
				this.startActivity(intent);
				this.finish();
			} else {
				Toast.makeText(this, "验证用户信息出错", Toast.LENGTH_LONG).show();
			}

		} else {
			new AlertDialog.Builder(MainActivity.this).setTitle("网络出问题了")
					.setMessage("请连接网络!")
					.setPositiveButton("重试", new Dialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							netState();

						}

					}).setNegativeButton("取消", new Dialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							MainActivity.this.finish();

						}

					}).show();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 读取网络问题
		netState();

	}

	class MyThread extends Thread {
		private String phone, imei;

		public MyThread(String phone, String imei) {
			this.imei = imei;
			this.phone = phone;
		}

		@Override
		public void run() {

			SoapObject request = new SoapObject("http://ibook.wosafe.com/",
					"verifyUser");
			request.addProperty("in0", phone);
			request.addProperty("in1", imei);
			// request.addProperty("in2", "411625");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			// envelope.implicitTypes = true;

			envelope.bodyOut = request;
			envelope.dotNet = false;
			// envelope.setOutputSoapObject(request);

			try {
				HttpTransportSE ht = new HttpTransportSE(
						"http://120.27.54.210:8091/XFirWosafebook/services/UserService");
				ht.debug = true;
				System.out.println("1111111111111111");
				ht.call(null, envelope);
				String result = envelope.getResponse().toString().trim();

				System.out.println(result);
				Message msg = new Message();

				if (result.equals("success")) {
					msg.what = 1;

				} else if (result.equals("false")) {
					msg.what = 2;

				}
				handler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

	}

}
