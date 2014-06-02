package com.micar.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewCustom extends TextView {

	public static String FONT_NAME = "Roboto-Regular.ttf";   //"myriad_pro_regular.ttf"  ,"Roboto-Bold.ttf";

	public TextViewCustom(Context context) {
		super(context);
		Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/"
				+ FONT_NAME);
		this.setTypeface(face);

	}

	public TextViewCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
		Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/"
				+ FONT_NAME);
		this.setTypeface(face);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
