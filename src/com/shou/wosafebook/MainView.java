package com.shou.wosafebook;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.shou.wosafebook.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainView extends Activity {
	
	public void getId(int i){
		
	}
	

	private boolean startType = true;
	private ViewPager viewpager;
	private View view1, view2;
	private View[] view;
	private List<View> listview;
	private PagerAdapter adapter;
	private int[] viewId = { R.layout.view5, R.layout.view6, R.layout.view7,
			R.layout.view8, R.layout.view9, R.layout.view10, R.layout.view11,
			R.layout.view12, R.layout.view13, R.layout.view14, R.layout.view15,
			R.layout.view16, R.layout.view17, R.layout.view18, R.layout.view19,
			R.layout.view20, R.layout.view21, R.layout.view22, R.layout.view23,
			R.layout.view24, R.layout.view25, R.layout.view26, R.layout.view27,
			R.layout.view28, R.layout.view29, R.layout.view30, R.layout.view31,
			R.layout.view32, R.layout.view33, R.layout.view34, R.layout.view35 };
	private int[] btnId = { R.id.btn_a5, R.id.btn_a6, R.id.btn_a7, R.id.btn_a8,
			R.id.btn_a9, R.id.btn_a10, R.id.btn_a11, R.id.btn_a12,
			R.id.btn_a13, R.id.btn_a14, R.id.btn_a15, R.id.btn_a16,
			R.id.btn_a17, R.id.btn_a18, R.id.btn_a19, R.id.btn_a20,
			R.id.btn_a21, R.id.btn_a22, R.id.btn_a23, R.id.btn_a24,
			R.id.btn_a25, R.id.btn_a26, R.id.btn_a27, R.id.btn_a28,
			R.id.btn_a29, R.id.btn_a30, R.id.btn_a31, R.id.btn_a32,
			R.id.btn_a33, R.id.btn_a34, R.id.btn_a35 };

	private ImageButton[] btn;
	private String[] path;
	private int[] flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainview);
		Toast.makeText(this, "一秒加载。。。", Toast.LENGTH_LONG).show();
		Intent intent = this.getIntent();

		new ReadRefresh().start();
		startType = intent.getBooleanExtra("type", false);

		viewpager = (ViewPager) findViewById(R.id.viewpager1);

		LayoutInflater inflater = getLayoutInflater();

		view1 = inflater.inflate(R.layout.view1, null);
		view2 = inflater.inflate(R.layout.view2, null);
		// view3 = inflater.inflate(R.layout.view3, null);
		listview = new ArrayList<View>();
		listview.add(view1);
		listview.add(view2);
		view = new View[31];
		btn = new ImageButton[31];
		for (int i = 0; i < 31; i++) {
			view[i] = inflater.inflate(viewId[i], null);
			btn[i] = (ImageButton) view[i].findViewById(btnId[i]);
			int inde = i + 5;
			String url = "http://120.27.54.210:8091/XFirWosafebook/images/a"
					+ inde + "_02.jpg";
			new MyAsnyTask(i).execute(url);
			btn[i].setOnClickListener(new MyOnClickListener());

			listview.add(view[i]);
		}

		adapter = new MyPagerAdapter(listview);
		viewpager.setAdapter(adapter);
		if (!startType) {
			viewpager.setCurrentItem(1);
		}

	}

	class MyOnClickListener implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			if (path == null) {
				Toast.makeText(MainView.this, "正在加载！", Toast.LENGTH_LONG)
						.show();
				return;
			}
			int which = -1;
			String url = "http://";
			switch (v.getId()) {
			case R.id.btn_a5:
				url = url + path[0];
				which = 1;
				break;
			case R.id.btn_a6:
				url = url + path[1];
				which = 2;
				break;
			case R.id.btn_a7:
				url = url + path[2];
				which = 3;
				break;
			case R.id.btn_a8:
				url = url + path[3];
				which = 4;
				break;
			case R.id.btn_a9:
				url = url + path[4];
				which = 5;
				break;
			case R.id.btn_a10:
				url = url + path[5];
				which = 6;
				break;
			case R.id.btn_a11:
				url = url + path[6];
				which = 7;
				break;
			case R.id.btn_a12:
				url = url + path[7];
				which = 8;
				break;
			case R.id.btn_a13:
				url = url + path[8];
				which = 9;
				break;
			case R.id.btn_a14:
				url = url + path[9];
				which = 10;
				break;
			case R.id.btn_a15:
				url = url + path[10];
				which = 11;
				break;
			case R.id.btn_a16:
				url = url + path[11];
				which = 12;
				break;
			case R.id.btn_a17:
				url = url + path[12];
				which = 13;
				break;
			case R.id.btn_a18:
				url = url + path[13];
				which = 14;
				break;
			case R.id.btn_a19:
				url = url + path[14];
				which = 15;
				break;
			case R.id.btn_a20:
				url = url + path[15];
				which = 16;
				break;
			case R.id.btn_a21:
				url = url + path[16];
				which = 17;
				break;
			case R.id.btn_a22:
				url = url + path[17];
				which = 18;
				break;
			case R.id.btn_a23:
				url = url + path[18];
				which = 19;
				break;
			case R.id.btn_a24:
				url = url + path[19];
				which = 20;
				break;
			case R.id.btn_a25:
				url = url + path[20];
				which = 21;
				break;
			case R.id.btn_a26:
				url = url + path[21];
				which = 22;
				break;
			case R.id.btn_a27:
				url = url + path[22];
				which = 23;
				break;
			case R.id.btn_a28:
				url = url + path[23];
				which = 24;
				break;
			case R.id.btn_a29:
				url = url + path[24];
				which = 25;
				break;
			case R.id.btn_a30:
				url = url + path[25];
				which = 26;
				break;
			case R.id.btn_a31:
				url = url + path[26];
				which = 27;
				break;
			case R.id.btn_a32:
				url = url + path[27];
				which = 28;
				break;
			case R.id.btn_a33:
				url = url + path[28];
				which = 29;
				break;
			case R.id.btn_a34:
				url = url + path[29];
				which = 30;
				break;
			case R.id.btn_a35:
				url = url + path[30];
				which = 31;
				break;
			}
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(url);
			intent.setData(content_url);
			startActivity(intent);
			if (which != -1) {
				new AddTime(String.valueOf(which)).start();
			}

		}

	}

	private class MyAsnyTask extends AsyncTask<String, Void, Bitmap> {
		private int flag;

		public MyAsnyTask(int flag) {
			this.flag = flag;
		}

		public MyAsnyTask() {

		}

		@Override
		protected Bitmap doInBackground(String... params) {

			return ImageUtil.getBitmapFromWeb(params[0]);

		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// btn[flag].setImageBitmap(result);

			// /上面注释的方法会存在无法填充屏幕的问题
			Drawable drawable = new BitmapDrawable(
					MainView.this.getResources(), result);
			btn[flag].setBackgroundDrawable(drawable);
		}
	}

	class AddTime extends Thread {
		private String which;

		public AddTime(String which) {
			this.which = which;
		}

		@Override
		public void run() {
			SoapObject request = new SoapObject("http://ibook.wosafe.com/",
					"addTime");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			request.addProperty("in0", which);

			envelope.bodyOut = request;
			envelope.dotNet = false;
			// envelope.setOutputSoapObject(request);

			try {
				HttpTransportSE ht = new HttpTransportSE(
						"http://120.27.54.210:8091/XFirWosafebook/services/UserService");
				ht.debug = true;
				ht.call(null, envelope);
				String result = envelope.getResponse().toString().trim();
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
	}

	class ReadRefresh extends Thread {

		@Override
		public void run() {
			SoapObject request = new SoapObject("http://ibook.wosafe.com/",
					"getData");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

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
				JSONArray jsa = new JSONArray(result);
				flag = new int[31];
				path = new String[31];
				for (int i = 0; i < jsa.length(); i++) {
					JSONObject jso = jsa.getJSONObject(i);
					flag[i] = Integer.parseInt(jso.getString("flag"));
					path[i] = jso.getString("path");
					System.out.println(flag[i] + path[i]);
				}

			} catch (Exception e) {
				e.printStackTrace();

			}

		}

	}

}
