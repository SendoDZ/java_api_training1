package fr.lernejo.navy_battle;

import java.util.Set;

public class Ship {
    private final Set<String> cells;
    private boolean isAlive;
    private final int size;
    private int hits;

    public Ship(Set<String> cells) {
        this.cells = cells;
        this.size = cells.size();
        this.hits = 0;
    }

    public CellStatus shoot(String cell) {
        if (!cells.contains(cell)) {
            return CellStatus.MISS;
        }

        cells.remove(cell);

        if (cells.isEmpty()) {
            isAlive = false;
            return CellStatus.SUNK;
        }

        return CellStatus.HIT;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isHit(String cell) {
        if (cells.contains(cell)) {
            hits++;
            return true;
        }
        return false;
    }

    public boolean isSunk() {
        return hits >= size;
    }

    public int getSize() {
        return size;
    }

    public Set<String> getCells() {
        return cells;
    }
}