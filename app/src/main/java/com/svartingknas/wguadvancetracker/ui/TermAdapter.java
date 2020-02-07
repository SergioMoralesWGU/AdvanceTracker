package com.svartingknas.wguadvancetracker.ui;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svartingknas.wguadvancetracker.R;
import com.svartingknas.wguadvancetracker.TermDetail;
import com.svartingknas.wguadvancetracker.entities.TermEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termTitleView;
        private final TextView termStartDateView;
        private final TextView termEndDateView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termTitleView = itemView.findViewById(R.id.tv_term_name);
            termStartDateView = itemView.findViewById(R.id.tv_term_start);
            termEndDateView = itemView.findViewById(R.id.tv_term_end);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String pattern = "MM/dd/yyyy";
                    DateFormat dateFormat = new SimpleDateFormat(pattern);

                    int position = getAdapterPosition();
                    final TermEntity current = termList.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.putExtra("Id", current.getId());
                    intent.putExtra("termTitle", current.getTermName());
                    intent.putExtra("termStartDate", dateFormat.format(current.getTermStart()));
                    intent.putExtra("termEndDate", dateFormat.format(current.getTermEnd()));
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

        }
    }

    private final LayoutInflater termInflater;
    private final Context context;
    private List<TermEntity> termList; //cached copy of the terms

    public TermAdapter(Context context){
        termInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = termInflater.inflate(R.layout.term_list_rv, parent, false);

        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        if (termList != null){
            TermEntity current = termList.get(position);
            holder.termTitleView.setText(current.getTermName());
            holder.termStartDateView.setText(df.format(current.getTermStart()));
            holder.termEndDateView.setText((df.format(current.getTermEnd())));
        } else{
            holder.termTitleView.setText("no data");
            holder.termStartDateView.setText("no data");
            holder.termEndDateView.setText("no data");
        }
    }

    public void setTerms(List<TermEntity> terms){
        termList = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (termList != null)
            return termList.size();
        else return 0;
    }
}
