package com.example.demo;

import java.io.Serializable;

public class Game implements Serializable {
    String name; int nrPlayers; int boardDim;

    public Game(String name, int nrPlayers, int boardDim) {
        this.name = name;
        this.nrPlayers=nrPlayers;
        this.boardDim=boardDim;
    }

    @Override
    public String toString() {
        return "Game : {" +
                "name : '" + name + '\'' +
                ", nrPlayers : " + nrPlayers +
                ", boardDim : "  + boardDim +
                '}';
    }
}
