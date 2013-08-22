package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;

public class Brightness extends Action {

	private int offset;

	public Brightness(int offset) {
		this.offset = offset;
	}

	@Override
	protected void adjustPixels(int[] pixels) {
		int r, g, b;

		int[] cache = new int[256];
		for (int i = 0; i < cache.length; i++) {
			cache[i] = trunc(i + offset);
		}

		for (int i = 0; i < pixels.length; i++) {
			r = cache[0xff & (pixels[i] >> 16)];
			g = cache[0xff & (pixels[i] >> 8)];
			b = cache[0xff & (pixels[i])];

			pixels[i] = 0xff000000 + (r << 16) + (g << 8) + b;
		}
	}

	public float getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
