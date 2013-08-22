package net.akhyar.android.imgpro;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class FilterTask extends AsyncTask<Filter, Void, Bitmap> {

	Filter filter;

	@Override
	protected Bitmap doInBackground(Filter... args) {

		if (args.length != 1 || args[0] == null)
			throw new IllegalArgumentException(
					"Background task requires a Filter parameter");

		filter = args[0];

		return filter.apply();
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (filter.getFilterListener() != null)
			filter.getFilterListener().onFilterDone(filter, bitmap);
	}
}
