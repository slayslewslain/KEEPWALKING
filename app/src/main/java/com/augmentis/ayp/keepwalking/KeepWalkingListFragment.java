package com.augmentis.ayp.keepwalking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chayanit on 7/26/2016.
 */
public class KeepWalkingListFragment extends Fragment {

    protected static final String TAG = "KeepWalkingListFragment";
    private static final int REQUEST_UPDATE_CRIME = 23340;

    private RecyclerView keepWalkingRecycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_keep_walking_list, container, false);


        keepWalkingRecycleView = (RecyclerView) v.findViewById(R.id.keep_walking_recycler_view);
        keepWalkingRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
}