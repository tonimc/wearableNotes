package com.toniousli.wearablenotes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.toniousli.wearablenotes.data.DatabaseHelper;
import com.toniousli.wearablenotes.data.DatabaseInstance;
import com.toniousli.wearablenotes.data.Note;

import java.sql.SQLException;
import java.util.List;


public class MainActivity extends Activity {

    private final String TAG = getClass().getSimpleName();

    DatabaseHelper db;
    NotesAdapter mNotesAdatper;

    ListView mListNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = DatabaseInstance.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLayout();
        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_about:
                Dialog aboutDialog = new Dialog(this);
                aboutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                aboutDialog.setContentView(R.layout.dialog_help);
                aboutDialog.setCancelable(true);
                aboutDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initLayout() {
        this.mListNotes = (ListView) findViewById(R.id.notesList);
    }

    private void setup() {
        initAdapter();
        this.mListNotes.setAdapter(this.mNotesAdatper);
        this.mListNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra(Note.EXTRA_NOTE, mNotesAdatper.getItem(position).toBundle());
                startActivity(intent);
            }
        });
    }

    private void initAdapter() {
        try {
            List<Note> notesList = this.db.getNotesDao().queryForAll();
            this.mNotesAdatper = new NotesAdapter(this, notesList);
        } catch (SQLException e) {
            Log.d(TAG, e.toString());
        }
    }
}
