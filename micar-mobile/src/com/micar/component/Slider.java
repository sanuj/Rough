package com.micar.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micar.activity.R;

public class Slider {

	private LinearLayout linearLytSlider, linearLytSliderTime;
	private Context context;
	private int boxWidth, boxHeight;
	private LayoutInflater mInflater;

	public Slider(LinearLayout linearLytSlider,
			LinearLayout linearLytSliderTime, Context context) {
		this.linearLytSlider = linearLytSlider;
		this.linearLytSliderTime = linearLytSliderTime;
		this.context = context;

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		boxWidth = width / 30;
		boxHeight = width / 30;
		linearLytSliderTime.removeAllViews();
		linearLytSlider.removeAllViews();
	

	}

	public void CreateSlider() {
		addDivider();
		for (int i = 0; i < 6; i++) {
			createBox(Color.GRAY, Color.BLACK);
		}

		addDivider();
		for (int i = 0; i < 6; i++) {
			createBox(Color.WHITE, Color.BLACK);
		}
		addDivider();
		for (int i = 0; i < 6; i++) {
			if (i == 0 || i == 1) {
				createBox(Color.YELLOW, Color.BLACK);
			} else {
				createBox(Color.WHITE, Color.BLACK);
			}
		}
		addDivider();
		for (int i = 0; i < 6; i++) {
			createBox(Color.GRAY, Color.BLACK);
		}
		// addDivider();
	}

	private void createBox(int boxColor, int borderColor) {
		// We'll be creating an image that is 50 pixels wide and 50 pixels tall.
		// Create a bitmap with the dimensions we defined above, and with a
		// 16-bit pixel format. We'll
		// get a little more in depth with pixel formats in a later post.
		Bitmap bitmap = Bitmap
				.createBitmap(boxWidth, boxHeight, Config.RGB_565);

		// Create a paint object for us to draw with, and set our drawing color
		// to blue.
		Paint paint = new Paint();
		paint.setColor(boxColor);

		// Create a new canvas to draw on, and link it to the bitmap that we
		// created above. Any drawing
		// operations performed on the canvas will have an immediate effect on
		// the pixel data of the
		// bitmap.
		Canvas canvas = new Canvas(bitmap);

		// Fill the entire canvas with a border color.
		canvas.drawColor(borderColor);

		// Draw a rectangle inside our image using the paint object we defined
		// above. The rectangle's
		// upper left corner will be at (1,1), and the lower left corner will be
		// at ( width - 1,height - 1). Since we set
		// the paint object's color above, this rectangle will be blue.
		canvas.drawRect(1, 1, boxWidth - 1, boxHeight - 1, paint);

		// In order to display this image in our activity, we need to create a
		// new ImageView that we
		// can display.
		ImageView imageView = new ImageView(context);

		// Set this ImageView's bitmap to the one we have drawn to.
		imageView.setImageBitmap(bitmap);

		linearLytSlider.addView(imageView);

	}

	private void addDivider() {

		View view = mInflater
				.inflate(R.layout.slider_divider_time, null, false);
		view.setLayoutParams(new LinearLayout.LayoutParams(boxWidth * 6,
				LayoutParams.WRAP_CONTENT));

		linearLytSliderTime.addView(view);

	}
}
