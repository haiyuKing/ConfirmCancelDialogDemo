package com.why.project.confirmcanceldialogdemo.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.why.project.confirmcanceldialogdemo.R;


/**
 * Used 确认取消对话框样式【含有标题、左中右三个按钮（同时适用于两个按钮、一个按钮）】
 */
public class ConfirmCancelDialog extends DialogFragment{
	
	private static final String TAG = ConfirmCancelDialog.class.getSimpleName();

	private Context mContext;
	/**View实例*/
	private View myView;
	/**标记：用来代表是从哪个界面打开的这个对话框*/
	private String mTag;

	private boolean locked;//点击空白区域是否隐藏对话框
	
	/**设置对话框内容和样式的监听器（标题、内容、按钮样式，包括控制隐藏）*/
	private DialogSetListener mDialogSetListener;
	/**三个按钮的点击事件监听器*/
	private DialogClickListener mDialogClickListener;

	public static ConfirmCancelDialog getInstance(Context mContext, DialogSetListener mDialogSetListener){
		return getInstance(mContext,false,mDialogSetListener);
	}

	public static ConfirmCancelDialog getInstance(Context mContext, boolean locked, DialogSetListener mDialogSetListener){
		ConfirmCancelDialog dialog = new ConfirmCancelDialog();
		dialog.mContext = mContext;
		dialog.locked = locked;
		dialog.mDialogSetListener = mDialogSetListener;

		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));//设置背景为透明，并且没有标题
		//getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));//设置背景为半透明，并且没有标题
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏

		myView = inflater.inflate(R.layout.dialog_confirm_cancel, container, false);

		if(locked) {
			this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						return true; // return true是中断事件，那么下面的就接受不到按键信息了
					} else {
						return false; //在return false的时候 才会事件继续向下传递。
					}
				}
			});
		}

		return myView;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        //初始化控件以及设置初始数据和监听事件
        initView();
    }
	
	/** 
	 * 设置宽度和高度值，以及打开的动画效果
	 */
	@Override
	public void onStart() {
		super.onStart();

		//设置对话框的宽高，必须在onStart中
		DisplayMetrics metrics = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Window window = this.getDialog().getWindow();
		if(locked){
			window.setLayout(metrics.widthPixels, metrics.heightPixels - getStatusBarHeight(mContext));//这样可以实现点击空白区域无法隐藏对话框的功能
		}else {
			window.setLayout(metrics.widthPixels, this.getDialog().getWindow().getAttributes().height);//这样才能实现点击空白区域自动隐藏对话框
		}
		window.setGravity(Gravity.CENTER);//设置在中间
		//打开的动画效果--缩放+渐隐
	}

	/**获取状态栏的高度*/
	private int getStatusBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		//根据需要执行
		if(mDialogClickListener != null){
			mDialogClickListener.cancelClickListener();
		}
	}
	
	/**实例化控件*/
	@SuppressWarnings("deprecation")
	private void initView() {
		TextView title = (TextView)myView.findViewById(R.id.title);//标题
		TextView titleDelever = (TextView)myView.findViewById(R.id.delever_up_message);//标题下划线
		
		TextView message = (TextView)myView.findViewById(R.id.message);//内容
		
		Button leftBtn = (Button)myView.findViewById(R.id.cancel);//左侧按钮
		View leftDeliver = (View)myView.findViewById(R.id.leftdeliver);//左侧按钮右侧的中竖线
		Button betweenBtn = (Button)myView.findViewById(R.id.between);//中间按钮
		Button rightBtn = (Button)myView.findViewById(R.id.confirm);//右侧按钮
		View rightDeliver = (View)myView.findViewById(R.id.rightdeliver);//右侧按钮左侧的中竖线
		
		
		//==========================初始展现==========================
		if(mDialogSetListener != null){
			mDialogSetListener.setDialog(title, message, leftBtn, betweenBtn, rightBtn);
		}
		
		//如果标题文字不存在，则隐藏标题中横线
		if(title.getVisibility() == View.GONE){
			titleDelever.setVisibility(View.GONE);
		}
		
		//如果内容文字不存在，则隐藏标题中横线
		if(message.getVisibility() == View.GONE){
			titleDelever.setVisibility(View.GONE);
		}

		//如果左侧按钮不存在，则隐藏左侧按钮旁边的中竖线，中间按钮的背景修改为原左侧按钮的背景
		if(leftBtn.getVisibility() == View.GONE) {
			leftDeliver.setVisibility(View.GONE);
			betweenBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_left_btn_bg));
			if(betweenBtn.getVisibility() == View.GONE) {
				rightDeliver.setVisibility(View.GONE);
				rightBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_btnlayout_bg));
			}else if(rightBtn.getVisibility() == View.GONE) {
				rightDeliver.setVisibility(View.GONE);
				betweenBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_btnlayout_bg));
			}
		}
		//如果中间按钮不存在，则隐藏左侧按钮旁边的中竖线
		if(betweenBtn.getVisibility() == View.GONE) {
			leftDeliver.setVisibility(View.GONE);
			if(leftBtn.getVisibility() == View.GONE) {
				rightDeliver.setVisibility(View.GONE);
				rightBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_btnlayout_bg));
			}else if(rightBtn.getVisibility() == View.GONE){
				rightDeliver.setVisibility(View.GONE);
				leftBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_btnlayout_bg));
			}
		}
		//如果右侧按钮不存在，则隐藏右侧按钮旁边的中竖线，中间按钮的背景修改为原右侧按钮的背景
		if(rightBtn.getVisibility() == View.GONE) {
			rightDeliver.setVisibility(View.GONE);
			betweenBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_right_btn_bg));
			if(betweenBtn.getVisibility() == View.GONE) {
				leftDeliver.setVisibility(View.GONE);
				leftBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_btnlayout_bg));
			}else if(leftBtn.getVisibility() == View.GONE) {
				leftDeliver.setVisibility(View.GONE);
				betweenBtn.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.dialog_confirm_cancel_btnlayout_bg));
			}
		}

		mTag = this.getTag();
		Log.e(TAG, "mTag="+mTag);
		
		//==========================初始化监听事件==========================
		leftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDialogClickListener != null){
					mDialogClickListener.cancelClickListener();
				}
				dismiss();
			}
		});
		betweenBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDialogClickListener != null){
					mDialogClickListener.betweenClickListener();
				}
				dismiss();
			}
		});
		rightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDialogClickListener != null){
					mDialogClickListener.confirmClickListener();
				}
				dismiss();
			}
		});
		
	}

	/**设置对话框内容和样式的监听器（标题、内容、按钮样式，包括控制隐藏）*/
	public static abstract interface DialogSetListener
	{
		/**设置标题、内容、按钮的文本以及按钮的显示隐藏
		 * @param title - 标题控件【默认“提示”】
		 * @param message - 内容控件【默认空白】
		 * @param leftBtn - 左侧按钮控件【默认“取消”】
		 * @param betweenBtn - 中间按钮控件【默认空白】
		 * @param rightBtn - 右侧按钮控件【默认“确定”】*/
		public abstract void setDialog(TextView title, TextView message, Button leftBtn, Button betweenBtn, Button rightBtn);
	}
	
	/**三个按钮的点击事件监听器*/
	public static abstract interface DialogClickListener
	{
		/**中间按钮*/
		public abstract void betweenClickListener();
		/**取消按钮*/
		public abstract void cancelClickListener();
		/**确定按钮*/
		public abstract void confirmClickListener();
	}

	public void setDialogClickListener(DialogClickListener dialogClickListener) {
		mDialogClickListener = dialogClickListener;
	}
	
}
