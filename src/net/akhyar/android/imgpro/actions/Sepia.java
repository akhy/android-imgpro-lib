package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;
import android.graphics.Color;

public class Sepia extends Action {

	final double GS_RED = 0.3;
	final double GS_GREEN = 0.59;
	final double GS_BLUE = 0.11;

	@Override
	protected void adjustPixels(int[] pixels) {

		int pixel, A, R, G, B;
		for (int i = 0; i < pixels.length; i++) {
			pixel = pixels[i];

			A = Color.alpha(pixel);
			R = Color.red(pixel);
			G = Color.green(pixel);
			B = Color.blue(pixel);

			B = G = R = (int) (R + G + B) / 3;

			R += GS_RED * R;
			G += GS_GREEN * G;
			B += GS_BLUE * B;

			pixels[i] = Color.argb(A, R, G, B);
		}
	}

}
