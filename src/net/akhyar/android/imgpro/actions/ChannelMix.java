package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;
import android.graphics.ColorMatrix;

public class ChannelMix extends Action {

	protected float[] m = new float[20];

	protected ChannelMix() {
	}

	public ChannelMix(float[] matrix) {
		this.m = matrix;
	}

	public ChannelMix(ColorMatrix matrix) {
		this.m = matrix.getArray();
	}

	@Override
	protected void adjustPixels(int[] colors) {

		int color, A1, R1, G1, B1, A2, R2, G2, B2;
		for (int x = 0; x < colors.length; x++) {
			color = colors[x];

			A1 = 0xff & (color >> 24);
			R1 = 0xff & (color >> 16);
			G1 = 0xff & (color >> 8);
			B1 = 0xff & color;

			R2 = clamp(m[0] * R1 + m[1] * G1 + m[2] * B1 + m[3] * A1 + m[4]);
			G2 = clamp(m[5] * R1 + m[6] * G1 + m[7] * B1 + m[8] * A1 + m[9]);
			B2 = clamp(m[10] * R1 + m[11] * G1 + m[12] * B1 + m[13] * A1
					+ m[14]);
			A2 = clamp(m[15] * R1 + m[16] * G1 + m[17] * B1 + m[18] * A1
					+ m[19]);

			colors[x] = (A2 << 24) | (R2 << 16) | (G2 << 8) | B2;
		}
	}

	public static final ChannelMix SEPIA = new ChannelMix(new float[] {
			.393f, .769f, .189f, 0, 0,
			.349f, .686f, .168f, 0, 0,
			.272f, .534f, .131f, 0, 0,
			0, 0, 0, 1, 0
	});

	public static final ChannelMix GRAYSCALE = new ChannelMix(new float[] {
			.3f, .59f, .11f, 0, 0,
			.3f, .59f, .11f, 0, 0,
			.3f, .59f, .11f, 0, 0,
			0, 0, 0, 1, 0
	});
}
