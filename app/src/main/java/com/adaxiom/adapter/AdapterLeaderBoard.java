package com.adaxiom.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.adaxiom.criccoo.R;
import com.adaxiom.model.ModelLeaderBoard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AdapterLeaderBoard extends RecyclerView.Adapter<AdapterLeaderBoard.ViewHolder> {

    private Activity activity;
    public List<ModelLeaderBoard> list;
    public List<ModelLeaderBoard> dummyList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewItemClickListener onItemClickListener;


    public interface RecyclerViewItemClickListener {

        void recyclerViewListClicked(View v, int position);
    }

    public AdapterLeaderBoard(Activity activity,
                              List<ModelLeaderBoard> list,
                              RecyclerViewItemClickListener onItemClickListener) {

        this.list = list;
        dummyList = new ArrayList<>();
        this.dummyList.addAll(list);
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_leaderboard, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;


    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

//        viewHolder.tvTitle.setText(list.get(position).title);
//        viewHolder.tvPrice.setText("£" + list.get(position).price);
//        viewHolder.tvHospitalName.setText(list.get(position).hospital_name);
//        viewHolder.tvDep.setText(list.get(position).department);


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

        TextView tvTitle, tvPrice, tvHospitalName, tvDep, tvDate, tvDay, tvTime;


        public ViewHolder(View view) {
            super(view);

//            tvTitle = (TextView) view.findViewById(R.id.tvRowJobsTitle);
//            tvPrice = (TextView) view.findViewById(R.id.tvRowJobsPrice);
//            tvHospitalName = (TextView) view.findViewById(R.id.tvRowJobsHospitalName);
//            tvDep = (TextView) view.findViewById(R.id.tvRowJobsDep);
//            tvDate = (TextView) view.findViewById(R.id.tvRowJobsDate);
//            tvDay = (TextView) view.findViewById(R.id.tvRowJobsDay);
//            tvTime = (TextView) view.findViewById(R.id.tvRowJobsTime);
//
//            view.setOnClickListener(this);

//            rating =(RatingBar) view.findViewById(R.id.chefRating);
//            tvTitle = (TextView) view.findViewById(R.id.tv_chef_name);
//            ivCheff = (ImageView) view.findViewById(R.id.chefImageView);


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
