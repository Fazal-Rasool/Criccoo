package com.salamlahore.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salamlahore.criccoo.R;
import com.salamlahore.model.response.RM_FullCommentry;
import com.salamlahore.model.response.RM_UserResult;

import java.util.List;


public class AdapterCommentry extends RecyclerView.Adapter<AdapterCommentry.ViewHolder> {

    private Activity activity;
    public List<RM_FullCommentry> list;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewItemClickListener onItemClickListener;


    public interface RecyclerViewItemClickListener {

        void recyclerViewListClicked(View v, int position);
    }

    public AdapterCommentry(Activity activity,
                            List<RM_FullCommentry> list,
                            RecyclerViewItemClickListener onItemClickListener) {

        this.list = list;
        this.activity = activity;
        this.onItemClickListener = onItemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.row_full_commentry, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;


    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.tvOver.setText(list.get(position).over+"."+list.get(position).ball);
        viewHolder.tvScore.setText(list.get(position).runs);
        viewHolder.tvCommentry.setText(list.get(position).commentry);


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

        TextView tvOver, tvScore, tvCommentry;


        public ViewHolder(View view) {
            super(view);

            tvOver = view.findViewById(R.id.tvOverBall);
            tvScore = view.findViewById(R.id.tvScore);
            tvCommentry = view.findViewById(R.id.tvCommentry);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            onItemClickListener.recyclerViewListClicked(v, this.getLayoutPosition());

        }
    }


}
