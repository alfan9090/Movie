package com.ahmadalfan.movieapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadalfan.movieapp.R;
import com.ahmadalfan.movieapp.data.model.TvResult;
import com.ahmadalfan.movieapp.ui.detail.DetailActivity;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {
    private Context context;
    private List<TvResult> list;
    private HomeAdapterListener listener;
    private String loadSize;

    public TvAdapter(Context context, List<TvResult> list, HomeAdapterListener listener, String loadSize) {
        this.context = context;
        this.listener = listener;
        this.list = list;
        this.loadSize = loadSize;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovie;
        TextView tvtitle, tvrate;
        CardView item_click;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovie = itemView.findViewById(R.id.iv_movie);
            tvtitle = itemView.findViewById(R.id.tv_title);
            tvrate = itemView.findViewById(R.id.tv_rate);
            item_click = itemView.findViewById(R.id.item_click);
            item_click.setOnClickListener(view1 -> {
                listener.onAdapteHomerSelect(list.get(getAdapterPosition()));
            });
        }
    }

    @NonNull
    @NotNull
    @Override
    public TvAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TvAdapter.MyViewHolder holder, int position) {
        final TvResult inv = list.get(position);
        holder.tvrate.setText((String.valueOf(list.get(position).getVote_average())));

        holder.tvtitle.setText(list.get(position).getName());
        Glide.with(context)
                .load(list.get(position).getPoster_path())
                .placeholder(R.drawable.ic_poster)
                .into(holder.ivMovie);

        holder.item_click.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", inv.getId());
            intent.putExtra("title", inv.getName());
            intent.putExtra("overview", inv.getOverview());
            intent.putExtra("original_language", inv.getOriginal_language());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (loadSize.equals("full")) {
            return list.size();
        } else {
            return Math.min(list.size(), 5);
        }
    }

    public interface HomeAdapterListener {
        void onAdapteHomerSelect(TvResult tvResult);
    }
}
