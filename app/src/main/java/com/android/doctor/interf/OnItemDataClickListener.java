package com.android.doctor.interf;

import com.android.doctor.model.Comment;


/**
 *
 */
public interface OnItemDataClickListener {

	public void onExpandChildren(Comment itemData);

	public void onHideChildren(Comment itemData);

}
