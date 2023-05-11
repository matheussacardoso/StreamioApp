package br.com.senaicimatec.streamio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<PlaylistModel> playlistModelArrayList;
    private ItemClickListener itemClickListener;

    //Construtor
    public HomeAdapter(Context context, ArrayList<PlaylistModel> playlistModelArrayList, ItemClickListener itemClickListener){
        this.context = context;
        this.playlistModelArrayList = playlistModelArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout

        PlaylistModel model = playlistModelArrayList.get(position);
        holder.playlistName.setText(model.getNome());
        holder.playlistRating.setText(model.getLikes());

        // posicao do item na reclyclerview
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onClickListener(playlistModelArrayList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return playlistModelArrayList.size();
    }

    public interface ItemClickListener{
        void onClickListener(PlaylistModel playlistModel);
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView playlistName;
        private final TextView playlistRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.playlistNameTv);
            playlistRating = itemView.findViewById(R.id.playlistRatingTV);
        }

    }
}
