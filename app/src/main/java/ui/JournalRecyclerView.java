package ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.self.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Journal;

public class JournalRecyclerView extends RecyclerView.Adapter<JournalRecyclerView.ViewHolder>{
    @NonNull
    private Context context;
    private List<Journal> journalList;

    public JournalRecyclerView(@NonNull Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    public JournalRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.journal_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecyclerView.ViewHolder holder, int position) {
        Journal journal = journalList.get(position);
        String imageURL;
        imageURL = journal.getImageURL();
        holder.title.setText(journal.getTitle());
        holder.thoughts.setText(journal.getThought());
        holder.name.setText(journal.getUsername());

        Picasso.get().load(imageURL).placeholder(R.drawable.image_one).fit().into(holder.image);

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(journal.
                getTimestamp().
                getSeconds() * 1000);
        holder.dateAdded.setText(timeAgo);

    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,thoughts,dateAdded,name;
        private ImageView image;
        private ImageButton share;
        private String username,userID;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            title = itemView.findViewById(R.id.title_row_txt);
            thoughts = itemView.findViewById(R.id.thought_row_txt);
            dateAdded = itemView.findViewById(R.id.dateTime_row_txt);
            image = itemView.findViewById(R.id.image_row_back);
            name = itemView.findViewById(R.id.journal_row_username);
            share = itemView.findViewById(R.id.journal_row_share_button);

        }
    }
}
