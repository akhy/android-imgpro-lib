package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;
import android.graphics.Color;

public class Colorize extends Action {

	private int color;
	private float factor;

	public Colorize(float factor, int color) {
		this.color = color;
		this.factor = factor;
	}

	@Override
	protected void adjustPixels(int[] colors) {

		int r, g, b;
		int tR, tG, tB;
		r = Color.red(this.color);
		g = Color.green(this.color);
		b = Color.blue(this.color);

		Blend.Mode mode = Blend.Mode.LIGHTEN;
		int[][] cache = new int[256][256];
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				cache[i][j] = clamp(mode.apply(i, j) * factor);
			}
		}

		for (int i = 0; i < colors.length; i++) {
			tR = Color.red(colors[i]);
			tG = Color.green(colors[i]);
			tB = Color.blue(colors[i]);
			tR = tG = tB = (tR + tG + tB) / 3;

			tR = cache[tR][r];
			tG = cache[tG][g];
			tB = cache[tB][b];

			colors[i] = Color.rgb(tR, tG, tB);
		}
	}

}
