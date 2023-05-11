package br.com.senaicimatec.streamio;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class MovieListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView movieList = view.findViewById(R.id.movieListLV);
        ArrayList<PlaylistModel> playlistModelArrayList = new ArrayList<>();
        /*
        Criar metodos para fetchar a playlistModelArrayList com os dados do banco,
        utilizando o link vindo do OnClik sobre o card da homepage (puxar nome da playlist e autor para fazer a busca)
         */


        MovieListAdapter movieListAdapter = new MovieListAdapter(getContext(), playlistModelArrayList);
        movieList.setAdapter(movieListAdapter);
    }
}