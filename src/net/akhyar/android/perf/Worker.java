package net.akhyar.android.perf;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;

public abstract class Worker {

	public abstract Bitmap process(Bitmap... bitmap);

	public static class FileDecoder extends Worker {

		private String path;
		private boolean isMutable = false;

		public FileDecoder(String path, boolean isMutable) {
			this.path = path;
			this.isMutable = isMutable;
		}

		public FileDecoder(String path) {
			this.path = path;
		}

		@Override
		public Bitmap process(Bitmap... bitmap) {
			Bitmap result = BitmapFactory.decodeFile(path);
			if (isMutable) {
				Log.i("HAE", "Bitmap copy >.<");
				result = result.copy(Config.ARGB_8888, true);
			}

			return result;
		}

	}

	public static class AssetDecoder extends Worker {

		private Context context;
		private String path;
		private boolean isMutable = false;

		public AssetDecoder(Context context, String path, boolean isMutable) {
			this.context = context;
			this.path = path;
			this.isMutable = isMutable;
		}

		public AssetDecoder(Context context, String path) {
			this.context = context;
			this.path = path;
		}

		@Override
		public Bitmap process(Bitmap... bitmap) {
			Bitmap result = null;
			try {
				result = BitmapFactory.decodeStream(context.getAssets().open(
						path));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Make mutable as needed
			if (result != null && isMutable) {
				Log.i("HAE", "Bitmap copy >.<");
				result = result.copy(Config.ARGB_8888, true);
			}

			return result;
		}

	}

}
