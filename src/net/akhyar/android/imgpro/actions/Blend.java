package net.akhyar.android.imgpro.actions;

import net.akhyar.android.imgpro.Action;
import android.graphics.Bitmap;
import android.graphics.Color;

public class Blend extends Action {

	private float opacity = 1;
	private Mode mode;
	private int[] layer = null;

	public Blend(int[] bPixels, float opacity, Mode mode) {
		this(opacity, mode);
		this.layer = bPixels;
	}

	public Blend(Bitmap against, float opacity, Mode mode) {
		this(opacity, mode);

		int[] layer = new int[against.getWidth() * against.getHeight()];
		against.getPixels(layer, 0, against.getWidth(), 0, 0,
				against.getWidth(), against.getHeight());
		this.layer = layer;
	}

	public Blend(float opacity, Mode mode) {
		this.opacity = opacity;
		this.mode = mode;
	}

	@Override
	protected void adjustPixels(int[] pixels) {

		if (layer == null) {
			layer = pixels.clone();
		}

		int[][] cache = new int[256][256];
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				cache[i][j] = apply(i, j, opacity, mode);
			}
		}

		int r1, g1, b1;
		int r2, g2, b2;
		for (int i = 0; i < pixels.length; i++) {
			r1 = Color.red(pixels[i]);
			r2 = Color.red(layer[i]);
			g1 = Color.green(pixels[i]);
			g2 = Color.green(layer[i]);
			b1 = Color.blue(pixels[i]);
			b2 = Color.blue(layer[i]);

			r1 = cache[r1][r2];
			g1 = cache[g1][g2];
			b1 = cache[b1][b2];

			pixels[i] = Color.rgb(r1, g1, b1);
		}
	}

	protected int applyWithOpacity(int A, int B, float opacity) {
		return trunc((int) (opacity * B + (1 - opacity) * A));
	}

	protected int apply(int A, int B, Mode mode) {
		return trunc(mode.apply(A, B));
	}

	protected int apply(int A, int B, float O, Mode mode) {
		return trunc(applyWithOpacity(A, mode.apply(A, B), O));
	}
	
	public static abstract class Mode {
		public abstract int apply(int A, int B);

		public static final Mode NORMAL = new Mode() {

			public int apply(int A, int B) {
				return B;
			}
		};

		public static final Mode OVERLAY = new Mode() {

			public int apply(int A, int B) {
				return ((B < 128) ? (2 * A * B / 255) : (255 - 2 * (255 - A)
						* (255 - B) / 255));
			}
		};

		public static final Mode LIGHTEN = new Mode() {

			public int apply(int A, int B) {
				return (B > A) ? B : A;
			}
		};

		public static final Mode DARKEN = new Mode() {

			public int apply(int A, int B) {
				return (B > A) ? A : B;
			}
		};

		public static final Mode MULTIPLY = new Mode() {

			public int apply(int A, int B) {
				return (A * B) / 255;
			}
		};

		public static final Mode AVERAGE = new Mode() {

			public int apply(int A, int B) {
				return (A + B) / 2;
			}
		};

		public static final Mode ADD = new Mode() {

			public int apply(int A, int B) {
				return Math.min(255, (A + B));
			}
		};

		public static final Mode SUBSTRACT = new Mode() {

			public int apply(int A, int B) {
				return (A + B < 255) ? 0 : (A + B - 255);
			}
		};

		public static final Mode DIFFERENCE = new Mode() {

			public int apply(int A, int B) {
				return Math.abs(A - B);
			}
		};

		public static final Mode NEGATION = new Mode() {

			public int apply(int A, int B) {
				return 255 - Math.abs(255 - A - B);
			}
		};

		public static final Mode SCREEN = new Mode() {

			public int apply(int A, int B) {
				return 255 - (((255 - A) * (255 - B)) >> 8);
			}
		};

		public static final Mode EXCLUSION = new Mode() {

			public int apply(int A, int B) {
				return (A + B - 2 * A * B / 255);
			}
		};

		public static final Mode SOFT_LIGHT = new Mode() {

			public int apply(int A, int B) {
				return (B < 128) ? (2 * ((A >> 1) + 64)) * (B / 255) : (255 - (2
						* (255 - ((A >> 1) + 64)) * (255 - B) / 255));
			}
		};

		public static final Mode HARD_LIGHT = new Mode() {

			public int apply(int A, int B) {
				return OVERLAY.apply(B, A);
			}
		};

		public static final Mode COLOR_DOGE = new Mode() {

			public int apply(int A, int B) {
				return (B == 255) ? B : Math.min(255, ((A << 8) / (255 - B)));
			}
		};

		public static final Mode COLOR_BURN = new Mode() {

			public int apply(int A, int B) {
				return (B == 0) ? B : Math.max(0, (255 - ((255 - A) << 8) / B));
			}
		};

		public static final Mode LINEAR_DODGE = new Mode() {

			public int apply(int A, int B) {
				return ADD.apply(A, B);
			}
		};

		public static final Mode LINEAR_BURN = new Mode() {

			public int apply(int A, int B) {
				return SUBSTRACT.apply(A, B);
			}
		};

		public static final Mode LINEAR_LIGHT = new Mode() {

			public int apply(int A, int B) {
				return (B < 128) ? LINEAR_BURN.apply(A, (2 * B)) : LINEAR_DODGE
						.apply(A, (2 * (B - 128)));
			}
		};

		public static final Mode VIVID_LIGHT = new Mode() {

			public int apply(int A, int B) {
				return (B < 128) ? COLOR_BURN.apply(A, (2 * B)) : COLOR_DOGE.apply(
						A, (2 * (B - 128)));
			}
		};

		public static final Mode PIN_LIGHT = new Mode() {

			public int apply(int A, int B) {
				return (B < 128) ? DARKEN.apply(A, (2 * B)) : LIGHTEN.apply(A,
						(2 * (B - 128)));
			}
		};

		public static final Mode HARD_MIX = new Mode() {

			public int apply(int A, int B) {
				return ((VIVID_LIGHT.apply(A, B) < 128) ? 0 : 255);
			}
		};

		public static final Mode REFLECT = new Mode() {

			public int apply(int A, int B) {
				return ((B == 255) ? B : Math.min(255, (A * A / (255 - B))));
			}
		};

		public static final Mode GLOW = new Mode() {

			public int apply(int A, int B) {
				return REFLECT.apply(B, A);
			}
		};

		public static final Mode PHOENIX = new Mode() {

			public int apply(int A, int B) {
				return Math.min(A, B) - Math.max(A, B) + 255;
			}
		};

	}
}
