package com.micar.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonCustom extends Button {

	public static String FONT_NAME = "Roboto-Regular.ttf";

	public ButtonCustom(Context context) {
		super(context);
		  Typeface face = Typeface.createFromAsset(context.getAssets(),
					 "fonts/" + FONT_NAME); this.setTypeface(face);

	}

	public ButtonCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		  Typeface face = Typeface.createFromAsset(context.getAssets(),
		 "fonts/" + FONT_NAME); this.setTypeface(face);
		 
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
