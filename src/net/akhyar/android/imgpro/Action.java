package net.akhyar.android.imgpro;

import android.graphics.Bitmap;

public abstract class Action {
	protected abstract void adjustPixels(int[] colors);

	public void adjustBitmap(Bitmap src) {
		int width = src.getWidth();
		int height = src.getHeight();

		int[] pixels = new int[src.getWidth() * src.getHeight()];
		src.getPixels(pixels, 0, width, 0, 0, width, height);

		adjustPixels(pixels);

		src.setPixels(pixels, 0, width, 0, 0, width, height);

		pixels = null;
	}

	protected int clamp(float ch) {
		if (ch > 255)
			return 255;
		if (ch < 0)
			return 0;
		return (int) ch;
	}

	protected int clamp(int ch) {
		if (ch > 255)
			return 255;
		if (ch < 0)
			return 0;
		return ch;
	}

}
