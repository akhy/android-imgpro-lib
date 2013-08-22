package net.akhyar.android.imgpro.filters;

import net.akhyar.android.imgpro.Filter;
import android.graphics.Bitmap;

public class Default extends Filter {

	public Default(){
		isDefault = true;
	}

	public void onBitmapSet(Bitmap bitmap) {
	}

	@Override
	public String getName() {
		return "Default";
	}

}
