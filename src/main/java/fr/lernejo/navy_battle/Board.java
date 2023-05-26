package fr.lernejo.navy_battle;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Board {
    private final int size;
    private final List<Ship> ships;

    public Board(int size) {
        this.size = size;
        this.ships = new ArrayList<>();
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public CellStatus shoot(String cell) {
        for (Ship ship : ships) {
            CellStatus status = ship.shoot(cell);
            if (status != CellStatus.MISS) {
                return status;
            }
        }

        return CellStatus.MISS;
    }

    public boolean hasShipsLeft() {
        for (Ship ship : ships) {
            if (ship.isAlive()) {
                return true;
            }
        }

        return false;
    }

    public boolean placeShip(Ship ship) {
        Set<String> cells = new HashSet<>();
        String cell = getRandomCell();
        int direction = getRandomDirection();
        for (int i = 0; i < ship.getSize(); i++) {
            if (!isValidCell(cell)) {
                return false;
            }
            cells.add(cell);
            cell = getNextCell(cell, direction);
        }
        ship = new Ship(cells);
        ships.add(ship);
        return true;
    }

    public String getRandomCell() {
        Random random = new Random();
        int row = random.nextInt(size) + 1;
        int col = random.nextInt(size) + 1;
        return String.format("%c%d", 'A' + col - 1, row);
    }

    public int getRandomDirection() {
        Random random = new Random();
        return random.nextInt(2);
    }

    public String getNextCell(String cell, int direction) {
        int row = cell.charAt(1) - '0';
        int col = cell.charAt(0) - 'A' + 1;
        if (direction == 0) {
            row++;
        } else {
            col++;
        }
        if (row < 1 || row > size || col < 1 || col > size) {
            return null;
        }
        return String.format("%c%d", 'A' + col - 1, row);
    }

    public boolean isValidCell(String cell) {
        if (cell == null || cell.length() != 2) {
            return false;
        }
        int row = cell.charAt(1) - '0';
        int col = cell.charAt(0) - 'A' + 1;
        return row >= 1 && row <= size && col >= 1 && col <= size;
    }
}