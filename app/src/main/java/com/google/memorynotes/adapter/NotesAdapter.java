package com.google.memorynotes.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.memorynotes.NotesClickListener;
import com.google.memorynotes.R;
import com.google.memorynotes.model.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
Context context;
List<Notes> list;
NotesClickListener listener;

    public NotesAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes,parent,false);
        return new NotesAdapter.NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
       holder.titleTV.setText(list.get(position).getTitle());

       //for horizontal scrolling effect
       holder.titleTV.setSelected(true);

       holder.NotesTV.setText(list.get(position).getNotes());

       holder.dateTV.setText(list.get(position).getDate());
       holder.dateTV.setSelected(true);


       if(list.get(position).isPinned())
       {
           holder.imageTV.setImageResource(R.drawable.ic_baseline_push_pin_24);
       }
       else
       {
               holder.imageTV.setImageResource(0);
       }
holder.containerCV.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        listener.onClick(list.get(holder.getAdapterPosition()));
    }
});
       holder. containerCV.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               listener.onLongClick(list.get(holder.getAdapterPosition()), holder.containerCV);
               return true;
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes>filteredList)
    {
        list=filteredList;
        notifyDataSetChanged();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder
    {
        CardView containerCV;
         TextView titleTV,NotesTV,dateTV;
         ImageView imageTV;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            containerCV=itemView.findViewById(R.id.notes_container);
            titleTV=itemView.findViewById(R.id.textView_title);
            NotesTV=itemView.findViewById(R.id.textView_notes);
            dateTV = itemView.findViewById(R.id.textView_date);
            imageTV=itemView.findViewById(R.id.ImageId);
        }
    }
}
