package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;
import android.graphics.Color;

public class Contrast extends Action {

	private int value;
	private double contrast;

	public Contrast(int value) {
		this.value = value;
		this.contrast = Math.pow((100 + value) / 100, 2);
	}

	@Override
	protected void adjustPixels(int[] pixels) {

		int A, R, G, B;
		for (int i = 0; i < pixels.length; i++) {
			A = Color.alpha(pixels[i]);

			R = Color.red(pixels[i]);
			R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);

			G = Color.green(pixels[i]);
			G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);

			B = Color.blue(pixels[i]);
			B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
			
			R = trunc(R);
			G = trunc(G);
			B = trunc(B);
			
			pixels[i] = Color.argb(A, R, G, B);
		}
	}

	public float getAmount() {
		return value;
	}

	public void setAmount(int amount) {
		this.value = amount;
	}

}
