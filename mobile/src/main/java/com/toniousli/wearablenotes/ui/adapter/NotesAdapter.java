/**
 aReminder - an Android + Google wear application test for I/O 2014

 Copyright (C) 2014  Toni Martinez / Adam Doan Kim

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package com.toniousli.wearablenotes.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.toniousli.wearablenotes.NoteService;
import com.toniousli.wearablenotes.R;
import com.toniousli.wearablenotes.data.Note;

import java.util.List;

/**
 * Created by toni on 5/07/14.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    private List<Note> mNotes;
    private View mWearable;
    private Note mNote;

    public NotesAdapter(Context context, List<Note> values) {
        super(context, R.layout.note_item, values);
        this.mNotes = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.note_item, parent, false);
        }

        ((TextView)rowView.findViewById(R.id.title)).setText(this.mNotes.get(position).getTitle());
        this.mWearable = rowView.findViewById(R.id.show_wearable);

        initEvents(this.mNotes.get(position));

        return rowView;
    }

    private void initEvents(final Note note) {
        this.mWearable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sdfsdf", note.toString());
                Intent intent = new Intent(getContext(), NoteService.class);
                intent.setAction(Note.ACTION_SHOW);
                intent.putExtra(Note.EXTRA_NOTE, note.toBundle());
                getContext().startService(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return this.mNotes.get(position).getId();
    }
}
