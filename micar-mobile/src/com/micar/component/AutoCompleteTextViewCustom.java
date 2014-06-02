package com.micar.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class AutoCompleteTextViewCustom extends AutoCompleteTextView {
	public static String FONT_NAME = "myriad_pro_regular.ttf";

	public AutoCompleteTextViewCustom(Context context) {
		super(context);
		  Typeface face = Typeface.createFromAsset(context.getAssets(),
					 "fonts/" + FONT_NAME); this.setTypeface(face);

	}

	public AutoCompleteTextViewCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		  Typeface face = Typeface.createFromAsset(context.getAssets(),
		 "fonts/" + FONT_NAME); this.setTypeface(face);
		 
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}

