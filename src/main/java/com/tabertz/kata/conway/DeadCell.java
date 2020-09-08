package com.tabertz.kata.conway;

public class DeadCell extends Cell {

    @Override
    public boolean willBeAliveInNextGeneration() {
        int liveNeigbourCount = countLiveNeighbours();
        return liveNeigbourCount == 3;
    }

}
