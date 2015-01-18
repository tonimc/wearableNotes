package com.toniousli.wearablenotes.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.toniousli.wearablenotes.R;
import com.toniousli.wearablenotes.data.DatabaseInstance;
import com.toniousli.wearablenotes.data.Note;


public class NoteActivity extends Activity {

    Note mNote;
    EditText mTitle;
    EditText mText;
    boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Bundle extra = getIntent().getBundleExtra(Note.EXTRA_NOTE);
        mNote = new Note();
        if(extra!=null) {
            mNote = Note.fromBundle(extra);
            isNew = false;
        }

        initLayout();
    }

    private void initLayout() {
        this.mTitle = ((EditText)findViewById(R.id.note_title));
        this.mText = ((EditText)findViewById(R.id.note_text));
        if(this.mNote!=null) {
            this.mTitle.setText(mNote.getTitle());
            this.mText.setText(mNote.getText());
        }
        ((Button)findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNote.setText(mText.getText().toString());
                mNote.setTitle(mTitle.getText().toString());
                try {
                    if(isNew) {
                        DatabaseInstance.getInstance(NoteActivity.this).getNotesDao().create(mNote);
                    }
                    else
                    {
                        DatabaseInstance.getInstance(NoteActivity.this).getNotesDao().update(mNote);
                    }
                } catch (Exception e) {
                }
                NoteActivity.this.finish();
            }
        });
    }
}
