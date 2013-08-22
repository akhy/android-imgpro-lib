package net.akhyar.android.imgpro;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.util.Log;

public abstract class Filter {
	protected ArrayList<Action> actions = new ArrayList<Action>();
	private Bitmap bitmap = null;
	private FilterListener filterListener = null;
	protected boolean isDefault = false;

	public abstract String getName();

	/**
	 * Will be fired after setBitmap()
	 * 
	 * @param bitmap
	 */
	public abstract void onBitmapSet(Bitmap bitmap);

	public Filter() {
	}

	public Filter(Bitmap bitmap) {
		this.setBitmap(bitmap);
	}

	public Filter(Bitmap bitmap, boolean isDefault) {
		this(bitmap);
		this.isDefault = isDefault;
	}

	protected void clearActions() {
		actions.clear();
	}

	protected void addAction(Action action) {
		actions.add(action);
	}

	protected void removeAction(int index) {
		actions.remove(index);
	}

	protected void removeAction(Action action) {
		actions.remove(action);
	}

	public final Bitmap apply() {

		if (getBitmap().isRecycled()) {
			Log.e("Filter", "Source bitmap is prematurely recycled");
			return null;
		}

		Bitmap bmp = getBitmap().copy(Bitmap.Config.ARGB_8888, true);

		if (!isDefault) {
			for (Action action : actions) {
				action.adjustBitmap(bmp);
			}
		}

		return bmp;
	}

	public final void applyInBackground() {
		FilterTask task = new FilterTask();
		task.execute(this);
	}

	public final void applyInBackground(FilterListener l) {
		setFilterListener(l);
		applyInBackground();
	}

	public FilterListener getFilterListener() {
		return filterListener;
	}

	public void setFilterListener(FilterListener filterListener) {
		this.filterListener = filterListener;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		onBitmapSet(bitmap);
	}
}
