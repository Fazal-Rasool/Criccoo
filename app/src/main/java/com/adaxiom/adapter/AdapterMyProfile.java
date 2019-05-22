package com.adaxiom.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adaxiom.criccoo.R;
import com.adaxiom.model.response.RM_LeaderBoard;
import com.adaxiom.model.response.RM_UserResult;

import java.util.ArrayList;
import java.util.List;


public class AdapterMyProfile extends RecyclerView.Adapter<AdapterMyProfile.ViewHolder> {

    private Activity activity;
    public List<RM_UserResult> list;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewItemClickListener onItemClickListener;


    public interface RecyclerViewItemClickListener {

        void recyclerViewListClicked(View v, int position);
    }

    public AdapterMyProfile(Activity activity,
                            List<RM_UserResult> list,
                            RecyclerViewItemClickListener onItemClickListener) {

        this.list = list;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_myprofile, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;


    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.tvMatch.setText(list.get(position).match_id);
        viewHolder.tvOver.setText(list.get(position).match_over);
        viewHolder.tvScore.setText(list.get(position).score);


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }


    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvMatch, tvOver, tvScore;


        public ViewHolder(View view) {
            super(view);

            tvMatch = (TextView) view.findViewById(R.id.tvMatchId_MyProfile);
            tvOver = (TextView) view.findViewById(R.id.tvOverid_MyProfile);
            tvScore = (TextView) view.findViewById(R.id.tvTotalPoints_MyProfile);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            onItemClickListener.recyclerViewListClicked(v, this.getLayoutPosition());

        }
    }


//    public void filter(String text) {
//        list.clear();
//        if (text.isEmpty()) {
//            list.addAll(dummyList);
//        } else {
//            text = text.toLowerCase();
//            for (ModelLeaderBoard item : dummyList) {
//                if (item.title.toLowerCase().contains(text)) {
//                    list.add(item);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }


}
