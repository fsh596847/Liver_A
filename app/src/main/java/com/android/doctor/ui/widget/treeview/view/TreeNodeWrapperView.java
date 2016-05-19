package com.android.doctor.ui.widget.treeview.view;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.doctor.R;


/**
 * Created by Bogdan Melnychuk on 2/10/15.
 */
public class TreeNodeWrapperView extends LinearLayout {
    private LinearLayout nodeItemsContainer;
    private ViewGroup nodeContainer;
    private final int containerStyle;

    public TreeNodeWrapperView(Context context, int containerStyle) {
        super(context);
        this.containerStyle = containerStyle;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);

        nodeContainer = new RelativeLayout(getContext());
        nodeContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        nodeContainer.setId(R.id.node_header);

        ContextThemeWrapper newContext = new ContextThemeWrapper(getContext(), containerStyle);
        nodeItemsContainer = new LinearLayout(newContext, null, containerStyle);
        nodeItemsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        nodeItemsContainer.setId(R.id.node_items);
        nodeItemsContainer.setOrientation(LinearLayout.VERTICAL);
        nodeItemsContainer.setVisibility(View.GONE);

        LinearLayout subItemsContainer = new LinearLayout(newContext, null, containerStyle);
        subItemsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        subItemsContainer.setOrientation(LinearLayout.HORIZONTAL);
        View view = new View(getContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setBackgroundColor(getResources().getColor(R.color.divider_color));
        subItemsContainer.addView(view);
        subItemsContainer.addView(nodeItemsContainer);

        addView(nodeContainer);
        addView(subItemsContainer);
    }


    public void insertNodeView(View nodeView) {
        nodeContainer.addView(nodeView);
    }

    public ViewGroup getNodeContainer() {
        return nodeContainer;
    }
}
