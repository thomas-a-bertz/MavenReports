package com.tabertz.kata.conway;

public class LiveCell extends Cell {

    @Override
    public boolean willBeAliveInNextGeneration() {
        int liveNeigbourCount = countLiveNeighbours();
        return liveNeigbourCount >= 2 && liveNeigbourCount <= 3;
    }
}
