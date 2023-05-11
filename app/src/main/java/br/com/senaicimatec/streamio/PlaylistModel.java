package br.com.senaicimatec.streamio;

/*
Atualizar a classe Model, adiconar todas as informacoes restantes e atualizar em classes que se utilizam dela
 */

import java.util.ArrayList;

public class PlaylistModel {
    private String nome;
    private String autor;
    private int likes;
    private ArrayList<MovieModel> movies = new ArrayList<>();

    public PlaylistModel(){

    }

    public PlaylistModel(String nome, String autor, int likes) {
        this.nome = nome;
        this.autor = autor;
        this.likes = likes;
    }

    public ArrayList<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieModel> movies) {
        this.movies = movies;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
