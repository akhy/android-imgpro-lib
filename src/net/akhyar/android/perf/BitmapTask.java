package net.akhyar.android.perf;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapTask extends AsyncTask<Bitmap, Void, Bitmap> {
	private Worker worker;
	private BitmapTask nextTask = null;
	protected Bitmap bitmap = null;

	protected BitmapTask(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	protected BitmapTask(Worker worker, BitmapTask nextTask) {
		this.worker = worker;
		this.nextTask = nextTask;
	}

	public BitmapTask(Worker worker) {
		this.worker = worker;
	}

	@Override
	protected Bitmap doInBackground(Bitmap... args) {
		if (bitmap != null)
			return bitmap;
		else if (worker != null)
			return worker.process(args);
		else if (args.length > 0)
			return args[0];
		else
			return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (nextTask != null)
			nextTask.execute(result);
	}

	public BitmapTask getNextTask() {
		return nextTask;
	}

	public void setNextTask(BitmapTask nextTask) {
		this.nextTask = nextTask;
	}

	public static class Builder {
		private LinkedList<BitmapTask> tasks = new LinkedList<BitmapTask>();

		public Builder setBitmap(Bitmap bitmap, boolean copy) {
			if (copy)
				bitmap = bitmap.copy(Config.ARGB_8888, true);

			tasks.addLast(new BitmapTask(bitmap));

			return this;
		}

		public Builder setNewBitmap(Bitmap bitmap) {
			tasks.addLast(new BitmapCreator(bitmap));

			return this;
		}

		public Builder cloneBitmap() {
			tasks.addLast(new BitmapCloner(null));

			return this;
		}
		
		public Builder setResultToImageView(ImageView iv){
			tasks.addLast(new SetImageViewBitmapTask(iv));
			
			return this;
		}

		public Builder addTask(Worker worker) {
			addTask(new BitmapTask(worker));
			return this;
		}

		public Builder addTask(BitmapTask task) {
			tasks.addLast(task);
			return this;
		}

		public BitmapTask get() {
			for (int i = 0; i < tasks.size() - 1; i++) {
				tasks.get(i).setNextTask(tasks.get(i + 1));
			}

			return tasks.getFirst();
		}

		public void clear() {
			tasks.clear();
		}
	}

	private static class BitmapCloner extends BitmapTask {

		protected BitmapCloner(Bitmap bitmap) {
			super(bitmap);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap.copy(Config.ARGB_8888, true));
		}

	}

	private static class BitmapCreator extends BitmapTask {

		protected BitmapCreator(Bitmap bitmap) {
			super(bitmap);
		}

		@Override
		protected Bitmap doInBackground(Bitmap... args) {
			return bitmap;
		}

	}

}