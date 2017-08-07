package com.why.project.confirmcanceldialogdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.why.project.confirmcanceldialogdemo.dialog.ConfirmCancelDialog;

import static android.view.View.GONE;

public class MainActivity extends FragmentActivity {

	private Button btn_confirm;
	private Button btn_confirmcancel;
	private Button btn_confirmcancelbetween;
	private Button btn_notitle;
	private Button btn_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initEvents();

	}

	private void initViews() {
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_confirmcancel = (Button) findViewById(R.id.btn_confirmcancel);
		btn_confirmcancelbetween = (Button) findViewById(R.id.btn_confirmcancelbetween);
		btn_notitle = (Button) findViewById(R.id.btn_notitle);
		btn_message = (Button) findViewById(R.id.btn_message);
	}

	private void initEvents() {
		btn_confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConfirmCancelDialog dialog = ConfirmCancelDialog.getInstance(MainActivity.this,new ConfirmCancelDialog.DialogSetListener() {

					public void setDialog(TextView title, TextView message, Button leftBtn, Button betweenBtn, Button rightBtn) {
						message.setText("请检查用户名");
						leftBtn.setVisibility(GONE);
						betweenBtn.setVisibility(GONE);
					}
				});
				dialog.setDialogClickListener(new ConfirmCancelDialog.DialogClickListener() {

					@Override
					public void betweenClickListener() {
						// TODO Auto-generated method stub
					}

					@Override
					public void cancelClickListener() {
						// TODO Auto-generated method stub
					}

					@Override
					public void confirmClickListener() {
						Toast.makeText(MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
					}

				});
				dialog.show(getSupportFragmentManager(), "confirmDialog");
			}
		});

		btn_confirmcancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConfirmCancelDialog dialog = ConfirmCancelDialog.getInstance(MainActivity.this,new ConfirmCancelDialog.DialogSetListener() {

					public void setDialog(TextView title, TextView message, Button leftBtn, Button betweenBtn, Button rightBtn) {
						message.setText("是否退出？");
						betweenBtn.setVisibility(GONE);
					}
				});
				dialog.setDialogClickListener(new ConfirmCancelDialog.DialogClickListener() {

					@Override
					public void betweenClickListener() {
						// TODO Auto-generated method stub
					}

					@Override
					public void cancelClickListener() {
						Toast.makeText(MainActivity.this,"取消",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void confirmClickListener() {
						Toast.makeText(MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
					}

				});
				dialog.show(getSupportFragmentManager(), "confirmCancelDialog");
			}
		});

		btn_confirmcancelbetween.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConfirmCancelDialog dialog = ConfirmCancelDialog.getInstance(MainActivity.this,new ConfirmCancelDialog.DialogSetListener() {

					public void setDialog(TextView title, TextView message, Button leftBtn, Button betweenBtn, Button rightBtn) {
						message.setText("是否保存？");
						betweenBtn.setText("不保存");
						rightBtn.setText("保存");
					}
				});
				dialog.setDialogClickListener(new ConfirmCancelDialog.DialogClickListener() {

					@Override
					public void betweenClickListener() {
						Toast.makeText(MainActivity.this,"不保存",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void cancelClickListener() {
						Toast.makeText(MainActivity.this,"取消",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void confirmClickListener() {
						Toast.makeText(MainActivity.this,"保存",Toast.LENGTH_SHORT).show();
					}

				});
				dialog.show(getSupportFragmentManager(), "confirmCancelBetweenDialog");
			}
		});

		btn_notitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConfirmCancelDialog dialog = ConfirmCancelDialog.getInstance(MainActivity.this,new ConfirmCancelDialog.DialogSetListener() {

					public void setDialog(TextView title, TextView message, Button leftBtn, Button betweenBtn, Button rightBtn) {
						message.setText("发现新版本，是否升级？");
						title.setVisibility(GONE);
						betweenBtn.setVisibility(GONE);
					}
				});
				dialog.setDialogClickListener(new ConfirmCancelDialog.DialogClickListener() {

					@Override
					public void betweenClickListener() {
						// TODO Auto-generated method stub
					}

					@Override
					public void cancelClickListener() {
						Toast.makeText(MainActivity.this,"取消",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void confirmClickListener() {
						Toast.makeText(MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
					}

				});
				dialog.show(getSupportFragmentManager(), "noTitleDialog");
			}
		});

		btn_message.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ConfirmCancelDialog dialog = ConfirmCancelDialog.getInstance(MainActivity.this,new ConfirmCancelDialog.DialogSetListener() {

					public void setDialog(TextView title, TextView message, Button leftBtn, Button betweenBtn, Button rightBtn) {
						message.setText("当内容文本很多的时候，需要手动设置居左【message.setGravity(Gravity.LEFT)】");
						message.setGravity(Gravity.LEFT);//如果内容很多的话，需要手动设置居左
						betweenBtn.setVisibility(GONE);
					}
				});
				dialog.setDialogClickListener(new ConfirmCancelDialog.DialogClickListener() {

					@Override
					public void betweenClickListener() {
						// TODO Auto-generated method stub
					}

					@Override
					public void cancelClickListener() {
						Toast.makeText(MainActivity.this,"取消",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void confirmClickListener() {
						Toast.makeText(MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
					}

				});
				dialog.show(getSupportFragmentManager(), "noMessageDialog");
			}
		});
	}
}
