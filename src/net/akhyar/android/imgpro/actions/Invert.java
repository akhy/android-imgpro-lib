package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;

public class Invert extends Action {

	@Override
	protected void adjustPixels(int[] pixels) {
		int size = pixels.length;

		int[] cache = new int[256];
		for (int i = 0; i < 256; i++) {
			cache[i] = 255 - i;
		}

		int a, r, g, b;
		for (int i = 0; i < size; i++) {
			a = pixels[i] & 0xff000000;

			r = cache[0xff & (pixels[i] >> 16)];
			g = cache[0xff & (pixels[i] >> 8)];
			b = cache[0xff & pixels[i]];

			pixels[i] = a + (r << 16) + (g << 8) + b;
		}
	}
}
