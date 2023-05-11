package br.com.senaicimatec.streamio;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private String id, playlistId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<PlaylistModel> playlistModelArrayList = new ArrayList<>();
        DatabaseReference usuario = reference.child("users");
        RecyclerView recyclerView = getView().findViewById(R.id.homeRV);

        HomeAdapter homeAdapter = new HomeAdapter(getContext(), playlistModelArrayList, new HomeAdapter.ItemClickListener() {
            @Override
            public void onClickListener(PlaylistModel playlistModel) {
                showToast(playlistModel.getAutor());
                moveToPlaylist();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeAdapter);

        usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentUser : snapshot.getChildren()){
                    id = currentUser.getKey();
                    DatabaseReference playlists = usuario.child(id).child("playlits");
                    //System.out.println(currentUser.child("playlits").child("a").getValue().toString());
                    //System.out.println(id);

                    playlists.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                            for (DataSnapshot playlistUser : playlistSnapshot.getChildren()){
                                playlistId = playlistUser.getKey();
                                //System.out.println("id que quero: " + playlistId);
                                //System.out.println(playlistUser.child("nome").getValue().toString());

                                PlaylistModel model = new PlaylistModel();
                                model.setNome(playlistUser.child("nome").getValue().toString());
                                model.setAutor(playlistUser.child("autor").getValue().toString());
                                model.setLikes(0);
                                model.setMovies(null);

                                System.out.println(model.getNome());
                                playlistModelArrayList.add(model);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public void showToast(String message){
        // apresenta uma mensagem para confirmar que a acao foi realizada !SOMENTE PARA TESTE!
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

    public void moveToPlaylist(){
        // Faz a mudanca entre os fragmentos a partir do frameLayout da MainActivity.
        MovieListFragment movieListFragment = new MovieListFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, movieListFragment).commit();
    }
}