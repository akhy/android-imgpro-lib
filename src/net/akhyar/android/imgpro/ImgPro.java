package net.akhyar.android.imgpro;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class ImgPro {
	public static final int CHANNEL_ALPHA = 8;
	public static final int CHANNEL_RED = 4;
	public static final int CHANNEL_GREEN = 2;
	public static final int CHANNEL_BLUE = 1;

	public static Bitmap resize(Bitmap src, int w2, int h2) {
		int w1 = src.getWidth();
		int h1 = src.getHeight();
		int[] pxSource = new int[w1 * h1];
		int[] pxResult = new int[w2 * h2];

		src.getPixels(pxSource, 0, w1, 0, 0, w1, h1);
		double x_ratio = w1 / (double) w2;
		double y_ratio = h1 / (double) h2;
		double px, py;
		for (int i = 0; i < h2; i++) {
			for (int j = 0; j < w2; j++) {
				px = Math.floor(j * x_ratio);
				py = Math.floor(i * y_ratio);
				pxResult[(i * w2) + j] = pxSource[(int) ((py * w1) + px)];
			}
		}

		return Bitmap.createBitmap(pxResult, w2, h2, Bitmap.Config.ARGB_8888);
	}

	public static Bitmap createSolidBitmap(int color, int width, int height) {
		Bitmap bmp = Bitmap.createBitmap(
				createSolidPixels(color, width * height), width, height,
				Bitmap.Config.ARGB_8888);

		return bmp;
	}

	public static int[] createSolidPixels(int color, int size) {
		int[] pixels = new int[size];
		for (int x = 0; x < size; x++) {
			pixels[x] = color;
		}

		return pixels;
	}

	// possibly unused
	public static Bitmap adjustBitmap(Bitmap src, Action adj) {

		int width = src.getWidth();
		int height = src.getHeight();

		Bitmap result = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);

		int[] pixels = new int[width * height];
		src.getPixels(pixels, 0, width, 0, 0, width, height);

		// pixels = adj.getAdjustedPixels(pixels);
		result.setPixels(pixels, 0, width, 0, 0, width, height);

		return result;
	}

	public static Bitmap createBitmapWithAlphaMask(Bitmap bmpSource,
			Bitmap bmpMask) {
		int width = bmpSource.getWidth();
		int height = bmpSource.getHeight();
		int size = width * height;

		if (width != bmpMask.getWidth() || height != bmpMask.getHeight())
			bmpMask = resize(bmpMask, width, height);

		int[] result = new int[size];
		int[] mask = new int[size];
		bmpSource.getPixels(result, 0, width, 0, 0, width, height);
		bmpMask.getPixels(mask, 0, width, 0, 0, width, height);

		int alphamask = 0xff000000;
		int colormask = 0x00ffffff;
		for (int i = 0; i < size; i++) {
			result[i] = (mask[i] & alphamask) | (result[i] & colormask);
		}

		// Ensuring the bitmap is mutable
		Bitmap bmpResult = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		bmpResult.setPixels(result, 0, width, 0, 0, width, height);

		return bmpResult;
	}
}
