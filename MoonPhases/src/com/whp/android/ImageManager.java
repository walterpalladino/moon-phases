package com.whp.android;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.widget.ImageView;

public class ImageManager {

	private static final String TAG = ImageManager.class.getCanonicalName();

	public static Bitmap getBitmapFromAssets(String fileName) throws IOException {
		Log.d(TAG, "ImageManager.getBitmapFromAssets : " + fileName);

		Log.d(TAG, "ImageManager.getBitmapFromAssets CustomAplication.getAppContext() : " + BasicApplication.getAppContext());

		Log.d(TAG, "ImageManager.getBitmapFromAssets CustomAplication.getAppContext().getAssets() : " + BasicApplication.getAppContext().getAssets());

	    AssetManager assetManager = BasicApplication.getAppContext().getAssets();
		Log.d(TAG, "ImageManager.getBitmapFromAssets Marca 1");

	    InputStream istr = assetManager.open(fileName);
		Log.d(TAG, "ImageManager.getBitmapFromAssets Marca 2");
	    Bitmap bitmap = BitmapFactory.decodeStream(istr);
		Log.d(TAG, "ImageManager.getBitmapFromAssets Marca 3");

	    return bitmap;
	}
	
	/*
	 imageView = (ImageView) findViewById(R.id.imageView);
Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.image);

Matrix matrix = new Matrix();
matrix.postRotate(30);

Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
   matrix, true);

imageView.setImageBitmap(rotated);
	 */
	public static void showImage(ImageView	imageView, String imageName) throws IOException {
		Log.d(TAG, "showImage");
	
		Bitmap bm;
		bm = ImageManager.getBitmapFromAssets(imageName);
		imageView.setImageBitmap(bm);

	}
	
	public static void showImage(ImageView	imageView, String imageName, float angle) throws IOException {
		Log.d(TAG, "showImage");
	
		Bitmap bm;
		bm = ImageManager.getBitmapFromAssets(imageName);
		
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		
		Bitmap rotated = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
				   matrix, true);
		
		imageView.setImageBitmap(rotated);

	}
	
}
