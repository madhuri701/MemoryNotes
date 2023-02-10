package com.google.memorynotes;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.memorynotes.adapter.NotesAdapter;
import com.google.memorynotes.database.RoomDB;
import com.google.memorynotes.model.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
RecyclerView recyclerView;
NotesAdapter notesAdapter;
List<Notes> notes = new ArrayList<>();
RoomDB database;
FloatingActionButton fab_add;
SearchView searchView_home;
Notes selectedNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

recyclerView = findViewById(R.id.idRecyclerView);
fab_add = findViewById(R.id.idFab);
searchView_home  = findViewById(R.id.idSearchNotes);
database = RoomDB.getInstance(this);
notes = database.mainDAO().getAll();

updateRecycler(notes);

fab_add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this,WriteNotesActivity.class);
        startActivityForResult(intent,101);
    }
});

searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return true;
    }
});
    }

    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for(Notes singleNote: notes)
        {
            if(singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase()))
            {
                filteredList.add(singleNote);
            }
        }
        notesAdapter.filterList(filteredList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                Notes new_notes = (Notes)data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesAdapter.notifyDataSetChanged();
            }
        }

        else if(requestCode==102)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().update(new_notes.getId(),new_notes.getTitle(),new_notes.getNotes());
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(MainActivity.this,notes,notesClickListener);
        recyclerView.setAdapter(notesAdapter);
    }


private final NotesClickListener notesClickListener = new NotesClickListener() {
    @Override
    public void onClick(Notes notes) {
        Intent intent = new Intent(MainActivity.this,WriteNotesActivity.class);
        intent.putExtra("old_note",notes);
        startActivityForResult(intent,102);

    }

    @Override
    public void onLongClick(Notes notes, CardView cardView) {
        selectedNotes  = new Notes();
        selectedNotes =notes;
        showPopup(cardView);


    }
};

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.pin:
                if(selectedNotes.isPinned())
                {
database.mainDAO().pin(selectedNotes.getId(),false);
                    Toast.makeText(MainActivity.this,"Unpinned",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.mainDAO().pin(selectedNotes.getId(),true);
                    Toast.makeText(MainActivity.this,"Pinned",Toast.LENGTH_SHORT).show();

                }

                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.mainDAO().delete(selectedNotes);
                notes.remove(selectedNotes);
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Pinned",Toast.LENGTH_SHORT).show();
                 return true;
            default:
                return false;

        }


    }
}