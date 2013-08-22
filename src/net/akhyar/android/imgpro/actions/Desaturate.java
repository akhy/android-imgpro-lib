package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;
import android.graphics.Color;

public class Desaturate extends Action {

	final double GS_RED = 0.299;
	final double GS_GREEN = 0.587;
	final double GS_BLUE = 0.114;

	@Override
	protected void adjustPixels(int[] pixels) {

		int pixel, A, R, G, B;
		for (int i = 0; i < pixels.length; i++) {
			pixel = pixels[i];

			A = Color.alpha(pixel);
			R = Color.red(pixel);
			G = Color.green(pixel);
			B = Color.blue(pixel);

			R = G = B = (int) (R + G + B) / 3;

			pixels[i] = Color.argb(A, R, G, B);
		}
	}

}
