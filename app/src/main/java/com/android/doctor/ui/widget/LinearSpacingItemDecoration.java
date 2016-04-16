package com.android.doctor.ui.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration {
	  private int left, top, bottom, right;
	  private int orientation;
	  
	  public LinearSpacingItemDecoration(int space, int orientation) {
	    this.left = space;
	    this.top = space;
	    this.bottom = space;
	    this.right = space;
	    this.orientation = orientation;
	  }
	  
	  public LinearSpacingItemDecoration(int leftSpace, int topSpace, 
			  int bottomSpace, int rightSpace, int orientation) {
		    this.left = leftSpace;
		    this.top = topSpace;
		    this.bottom = bottomSpace;
		    this.right = rightSpace;
		    this.orientation = orientation;
	  }
	  
	  @Override
	  public void getItemOffsets(Rect outRect, View view, 
	      RecyclerView parent, RecyclerView.State state) {
		    if (orientation == LinearLayout.HORIZONTAL) {
			    outRect.right = right;
			    //outRect.bottom = bottom;
			    //outRect.top = space;
			    // Add top margin only for the first item to avoid double space between items
			    if(parent.getChildAdapterPosition(view) == 0)
			    	outRect.left = left;
		    } else {
		    	outRect.left = left;
		    	outRect.right = right;
		    	outRect.top = top;
			    outRect.bottom = bottom;
		    }
	  }
}
