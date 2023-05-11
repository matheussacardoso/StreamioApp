package br.com.senaicimatec.streamio;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class AddMovieFragment extends Fragment {
    // referencia do no raiz do banco
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    private EditText playlistName, playlistAuthor;
    private Button addPlaylistBtn;

    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // referencia para o no de usuarios
        DatabaseReference usuario = reference.child("users");

        playlistName = view.findViewById(R.id.cadastroNomePlaylistET);
        playlistAuthor = view.findViewById(R.id.cadastroAutorET);

        addPlaylistBtn = view.findViewById(R.id.adicionarPlaylistBtn);

        addPlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlaylistModel playlist = addPlaylist();

                if(playlist != null){
                    // localizar a raiz do user (ID) a partir do nome do autor
                    usuario.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot currentUser : snapshot.getChildren()){
                                if(playlist.getAutor().equals(currentUser.child("userName").getValue())){
                                    id = currentUser.getKey();
                                    System.out.println(id);
                                    DatabaseReference userId = usuario.child(id);
                                    userId.child("playlits").child(playlist.getNome()).setValue(playlist);

                                    userId.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            // Mensagem Que funcionou
                                            //showToast("Playlist Cadastrada!");
                                            // Voltar para a home

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // Mensagem de erro
                                            showToast("Deu pau");
                                        }
                                    });
                                } else {
                                    // Mensagem de Erro para usuario nao cadastrado
                                }
                            }
                            // seguir codigo aqui

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    // Mensagem de erro ao adicionar playlist
                    showToast("Deu outro pau");
                }
            }
        });

    }

    public PlaylistModel addPlaylist(){
        PlaylistModel playlist = new PlaylistModel();

        if(!TextUtils.isEmpty(playlistName.getText().toString()) && !TextUtils.isEmpty(playlistAuthor.getText().toString())){
            playlist.setNome(playlistName.getText().toString());
            playlist.setAutor(playlistAuthor.getText().toString());
            playlist.setLikes(0);
            playlist.setMovies(addMovieArray());
            return playlist;
        } else {
            return null;
        }
    }

    public ArrayList<MovieModel> addMovieArray(){
        ArrayList<MovieModel> moviesArraylist = new ArrayList<>();

        ArrayList<Integer> titulos = new ArrayList<>(Arrays.asList(R.id.tituloFilme1, R.id.tituloFilme2, R.id.tituloFilme3, R.id.tituloFilme4));
        ArrayList<Integer> anos = new ArrayList<>(Arrays.asList(R.id.anoFilme1, R.id.anoFilme2, R.id.anoFilme3, R.id.anoFilme4));

        Iterator<Integer> itTitulos = titulos.iterator();
        Iterator<Integer> itAnos = anos.iterator();



        while (itTitulos.hasNext() && itAnos.hasNext()){
            EditText edtTitulos = getView().findViewById(itTitulos.next());
            EditText edtAnos = getView().findViewById(itAnos.next());
            MovieModel movie = new MovieModel();

            if(!TextUtils.isEmpty(edtTitulos.getText().toString()) && !TextUtils.isEmpty(edtAnos.getText().toString())){
                movie.setTitulo(edtTitulos.getText().toString());
                System.out.println(movie.getTitulo());
                movie.setAno(edtAnos.getText().toString());
                System.out.println(movie.getAno());
                System.out.println("Objeto: " + movie);
                moviesArraylist.add(movie);

            } else {
                // Quando nao existem dados na EditText assumimos que o usuario optou por adicionar menos de 4 filmes
                // Assim, para tratamento do banco, sera adicionado um valor padrao de noDataEntry
                movie.setTitulo("noDataEntry");
                movie.setAno("noDataEntry");
                moviesArraylist.add(movie);

            }

        }

        return moviesArraylist;
    }

    public void showToast(String message){
        // apresenta uma mensagem para confirmar que a acao foi realizada !SOMENTE PARA TESTE!
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

}