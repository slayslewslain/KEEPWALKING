package com.augmentis.ayp.keepwalking;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chayanit on 7/26/2016.
 */
public class KeepWalkingListFragment extends Fragment {

    protected static final String TAG = "KeepWalkingListFragment";

    private static final int REQUEST_UPDATE_KEEP_WALKING = 23340;

    private RecyclerView keepWalkingRecyclerView;
    private Button keepWalkingAddButton;
    private KeepWalkingAdapter adapter;
    private Integer[] keepWalkingPosition;
    private KeepWalking _keepWalking;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Resume List!!");
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_UPDATE_KEEP_WALKING) {
            if (resultCode == Activity.RESULT_OK) {
                keepWalkingPosition = (Integer[]) data.getExtras().get("position");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_keep_walking_list, container, false);

        keepWalkingAddButton = (Button) v.findViewById(R.id.keep_walking_add);
        keepWalkingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _keepWalking = new KeepWalking();
                Intent intent = KeepWalkingActivity.newIntent(getActivity(), _keepWalking.getUuid());
                startActivityForResult(intent, REQUEST_UPDATE_KEEP_WALKING);
            }
        });

        keepWalkingRecyclerView = (RecyclerView) v.findViewById(R.id.keep_walking_recycler_view);
        keepWalkingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;
    }

    private void updateUI() {
        KeepWalkingLab keepWalkingLab = KeepWalkingLab.getInstance(getActivity());
        List<KeepWalking> keepWalkings = keepWalkingLab.getKeepWalking();
        if (adapter == null) {
            adapter = new KeepWalkingAdapter(keepWalkings);
            keepWalkingRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private class KeepWalkingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView keepWalkingTitleTextView;
        public TextView keepWalkingDateTextView;

        KeepWalking _keepWalking;

        public KeepWalkingHolder(View itemView) {
            super(itemView);

            keepWalkingTitleTextView = (TextView) itemView.findViewById(R.id.list_item_keep_walking_title_text_view);
            keepWalkingDateTextView = (TextView) itemView.findViewById(R.id.list_item_keep_walking_date_text_view);

            itemView.setOnClickListener(this);
        }

        public void bind(KeepWalking keepWalking, int position) {
            _keepWalking = keepWalking;
            keepWalkingTitleTextView.setText(_keepWalking.getTitle());
            keepWalkingDateTextView.setText(_keepWalking.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = KeepWalkingActivity.newIntent(getActivity(), _keepWalking.getUuid());
            startActivityForResult(intent, REQUEST_UPDATE_KEEP_WALKING);
        }
    }

    private class KeepWalkingAdapter extends RecyclerView.Adapter<KeepWalkingHolder> {

        private List<KeepWalking> keepWalkings;

        public KeepWalkingAdapter(List<KeepWalking> keepWalkings) {
            this.keepWalkings = keepWalkings;
        }

        @Override
        public KeepWalkingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_keep_walking, parent, false);
            return new KeepWalkingHolder(v);
        }

        @Override
        public void onBindViewHolder(KeepWalkingHolder holder, int position) {
            KeepWalking keepWalking = this.keepWalkings.get(position);
            holder.bind(keepWalking, position);
        }

        @Override
        public int getItemCount() {
            return this.keepWalkings.size();
        }
    }
}
