package com.google.memorynotes;

import androidx.cardview.widget.CardView;

import com.google.memorynotes.model.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
