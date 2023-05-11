package br.com.senaicimatec.streamio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieListAdapter extends ArrayAdapter<PlaylistModel>{
    // construtor
    public MovieListAdapter(@NonNull Context context, ArrayList<PlaylistModel> playlistModelArrayList) {
        super(context, R.layout.movie_item_card, playlistModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        PlaylistModel playlistModel = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.movie_item_card, parent, false);
        }

        TextView movieTitleTV = view.findViewById(R.id.movieTitleTV);
        TextView movieYearTV = view.findViewById(R.id.movieYearTV);

        //Ajustar os setText para os gets certos, criar metodo para organizar os gets para cada um dos 4 filmes.
        movieTitleTV.setText(playlistModel.getNome());
        movieYearTV.setText(playlistModel.getAutor());

        return view;
    }
}
