package com.tabsdemo.fragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tabsdemo.R;
import com.tabsdemo.activity.Constants;

@SuppressLint("ValidFragment")
public class TopRatedFragment extends Fragment {

	private ImageView mImageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_top_rated,
				container, false);
		mImageView = (ImageView) rootView.findViewById(R.id.image_view);
		new DownloadImage().execute(Constants.PHOTO_URL);
		return rootView;
	}

	private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
			return loadImageFromNetwork(urls[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				mImageView.setImageBitmap(result);

			}

		}

		private Bitmap loadImageFromNetwork(String url) {
			URL imageUrl;
			try {
				imageUrl = new URL(url);
				return BitmapFactory.decodeStream(imageUrl.openConnection()
						.getInputStream());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

	}

}
