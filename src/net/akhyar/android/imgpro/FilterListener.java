package net.akhyar.android.imgpro;

import android.graphics.Bitmap;

public interface FilterListener {
	public void onFilterStart();

	public void onFilterDone(Filter f, Bitmap b);
}
