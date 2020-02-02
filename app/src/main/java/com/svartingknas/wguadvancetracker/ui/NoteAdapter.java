package com.svartingknas.wguadvancetracker.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svartingknas.wguadvancetracker.CourseDetail;
import com.svartingknas.wguadvancetracker.R;
import com.svartingknas.wguadvancetracker.entities.NoteEntity;


import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteCourseId;
        private final TextView noteTitle;
        private final TextView noteText;

        private NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteCourseId = itemView.findViewById(R.id.note_course_id);
            noteTitle = itemView.findViewById(R.id.note_name_tv);
            noteText = itemView.findViewById(R.id.note_text_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final NoteEntity current = mNotes.get(position);
                    Intent intent = new Intent(context, CourseDetail.class); //TODO create a thing to add a note and change the intent.
                    intent.putExtra("Id", current.getId());
                    intent.putExtra("noteTitle", current.getNoteTitle());
                    intent.putExtra("noteText", current.getNoteText());
                    intent.putExtra("position", position);
                    intent.putExtra("noteCourseId", current.getNoteCourseId());
                    context.startActivity(intent);

                }
            });
        }
    }
    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<NoteEntity> mNotes; //cached copy of notes

    public NoteAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.course_note_rv, parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (mNotes != null){
            NoteEntity current = mNotes.get(position);
            holder.noteCourseId.setText(Integer.toString(current.getNoteCourseId()));
            holder.noteTitle.setText(current.getNoteTitle());
            holder.noteText.setText(current.getNoteText());
        } else {
            holder.noteCourseId.setText("no data");
            holder.noteTitle.setText("no data");
            holder.noteText.setText("no data");
        }

    }

    public void setNotes(List<NoteEntity> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }


    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }


}
