/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.hugs;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atte.kramamanda.R;

/**
 * Shows the Hug list view.
 */
public class HugListFragment extends Fragment implements OnHugItemClickedListener {
    /**
     * Called to create the view for this fragment.
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hug_list, container, false);
        ((HugListView)view.findViewById(R.id.hug_list_view)).init(this);
        return view;
    }

    /**
     * Called when the item with the given hug is clicked.
     */
    @Override
    public void onHugClicked(Hug hug) {
        HugDialog.newInstance(hug).show(getFragmentManager(), "hugDialog");
    }
}