package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;
import android.graphics.Bitmap;

public class Blur extends Action {

	int radius;
	int width, height;
	int[] pixels;

	public Blur(int radius) {
		this.radius = radius;
	}

	@Override
	public void adjustBitmap(Bitmap src) {
		this.width = src.getWidth();
		this.height = src.getHeight();
		
		super.adjustBitmap(src);
	}

	@Override
	protected void adjustPixels(int[] color) {

		for (int r = radius; r >= 1; r /= 2) {
			for (int i = r; i < height - r; i++) {
				for (int j = r; j < width - r; j++) {
					int tl = color[(i - r) * width + j - r];
					int tr = color[(i - r) * width + j + r];
					int tc = color[(i - r) * width + j];
					int bl = color[(i + r) * width + j - r];
					int br = color[(i + r) * width + j + r];
					int bc = color[(i + r) * width + j];
					int cl = color[i * width + j - r];
					int cr = color[i * width + j + r];

					color[(i * width) + j] = 0xFF000000
							| (((tl & 0xFF) + (tr & 0xFF) + (tc & 0xFF)
									+ (bl & 0xFF) + (br & 0xFF) + (bc & 0xFF)
									+ (cl & 0xFF) + (cr & 0xFF)) >> 3)
							& 0xFF
							| (((tl & 0xFF00) + (tr & 0xFF00) + (tc & 0xFF00)
									+ (bl & 0xFF00) + (br & 0xFF00)
									+ (bc & 0xFF00) + (cl & 0xFF00) + (cr & 0xFF00)) >> 3)
							& 0xFF00
							| (((tl & 0xFF0000) + (tr & 0xFF0000)
									+ (tc & 0xFF0000) + (bl & 0xFF0000)
									+ (br & 0xFF0000) + (bc & 0xFF0000)
									+ (cl & 0xFF0000) + (cr & 0xFF0000)) >> 3)
							& 0xFF0000;
				}
			}
		}
	}
}
