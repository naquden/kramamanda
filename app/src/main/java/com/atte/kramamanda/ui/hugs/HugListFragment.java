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
import com.atte.kramamanda.backend.DataChangedListener;
import com.atte.kramamanda.backend.HugDatabaseHelper;
import com.atte.kramamanda.ui.ParticleSystemWrapper;
import com.atte.kramamanda.ui.dialogs.HugItemContextDialog;

/**
 * Shows the Hug list view.
 */
public class HugListFragment extends Fragment
        implements OnHugItemClickedListener, DataChangedListener {
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
     * Called when this view is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        HugDatabaseHelper.addDataChangedListener(this);
        refreshList();
    }

    /**
     * Called when this view is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        HugDatabaseHelper.removeDataChangedListener(this);
    }

    /**
     * Called when the item with the given hug is clicked.
     */
    @Override
    public void onHugClicked(Hug hug) {
        HugDialog.newInstance(hug).show(getFragmentManager(), "hugDialog");
        ParticleSystemWrapper.explodeFromView(getActivity(), getView());
    }

    /**
     * Called when the item with the given hug is long clicked.
     * TODO: Replace showing the dialog with a swipe gesture instead.
     */
    @Override
    public void onHugLongClicked(Hug hug) {
        HugItemContextDialog.newInstance(hug).show(getFragmentManager(), "hugMenu");
    }

    /**
     * Causes the list to refresh.
     */
    public void refreshList() {
        View view = getView();
        if (view != null) {
            ((HugListView)view.findViewById(R.id.hug_list_view)).refresh();
        }
    }

    /**
     * Called when the data in the database has changed somehow.
     */
    @Override
    public void onDataChanged() {
        refreshList();
    }
}