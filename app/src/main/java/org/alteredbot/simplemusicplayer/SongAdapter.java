package org.alteredbot.simplemusicplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Song item);
    }

    private List<Song> songList;
    private OnItemClickListener listener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView composer;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            composer = (TextView) view.findViewById(R.id.composer);
        }

        public void bind(final Song item, final OnItemClickListener listener) {

            title.setText(item.title);
            composer.setText(item.composer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public SongAdapter(List<Song> songList, OnItemClickListener listener) {
        this.songList = songList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.bind(song,listener);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}