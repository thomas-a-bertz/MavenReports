package com.tabertz.kata.conway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/*
 * 1. UNDERPOPULATION
 * Any live cell with fewer than two live neighbours dies.
 * 
 * 2. OVERCROWDING
 * Any live cell with more than three live neighbours dies.
 * 
 * 3. SURVIVAL
 * Any live cell with two or three live neighbours lives on to the next generation.
 * 
 * 4. REPRODUCTION
 * Any dead cell with exactly three live neighbours becomes a live cell.
 */
public class CellTest {

    private Cell aLiveCell;
    private Cell aDeadCell;

    @BeforeEach
    public void setUp() {
        aLiveCell = new LiveCell();
        aDeadCell = new DeadCell();
    }

    @Test
    public void testUnderpopulationZeroLiveNeighbours() throws Exception {
	    aLiveCell.setNeighbours(createNeighbours(8, 0));
        assertFalse(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testUnderpopulationOneLiveNeighbour() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(7, 1));
        assertFalse(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingFourLiveNeighbours() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(4, 4));
        assertFalse(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingFiveLiveNeighbours() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(3, 5));
        assertFalse(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingSixLiveNeighbours() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(2, 6));
        assertFalse(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingSevenLiveNeighbours() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(1, 7));
        assertFalse(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingEightLiveNeighbours() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(0, 8));
        assertFalse(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testSurvivalTwoLiveNeighbours() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(6, 2));
        assertTrue(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testSurvivalThreeLiveNeighbours() throws Exception {
        aLiveCell.setNeighbours(createNeighbours(5, 3));
        assertTrue(aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testReproductionAdeadCellWithThreeLiveNeighboursWillBeAliveInNextGeneration() throws Exception {
        aDeadCell.setNeighbours(createNeighbours(5, 3));
        assertTrue(aDeadCell.willBeAliveInNextGeneration());
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForReproductionAdeadCell")
    public void testReproductionAdeadCellWith(int deadCellCount, int aliveCellCount,
                                                                     boolean expectedResult)
            throws Exception {
        aDeadCell.setNeighbours(createNeighbours(deadCellCount, aliveCellCount));
        assertEquals(expectedResult, aDeadCell.willBeAliveInNextGeneration());
    }

    private static Stream<Arguments> provideTestDataForReproductionAdeadCell() {
        return Stream.of(
                Arguments.of(8, 0, false),
                Arguments.of(7, 1, false),
                Arguments.of(6, 2, false),
                Arguments.of(5, 3, true),
                Arguments.of(4, 4, false),
                Arguments.of(3, 5, false),
                Arguments.of(2, 6, false),
                Arguments.of(1, 7, false),
                Arguments.of(0, 8, false)
        );
    }

    private List<Cell> createNeighbours(int dead, int alive) throws Exception {
        if (dead < 0 || alive < 0) {
            throw new Exception("CellTest: Parameter out of bounds");
        }
        List<Cell> neighbours = new ArrayList<>();
        for (int i = 0; i < dead; i++) {
            neighbours.add(new DeadCell());
        }
        for (int j = 0; j < alive; j++) {
            neighbours.add(new LiveCell());
        }
        return neighbours;
        }
}
