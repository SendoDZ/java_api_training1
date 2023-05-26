package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int BOARD_SIZE = 10;
    private static final int[] SHIP_SIZES = {5, 4, 3, 3, 2};
    private static final int MAX_SHOTS = 100;

    private final List<Ship> ships;
    private final Board board;
    private int shots;

    private static Game instance;

    private Game() {
        this.ships = new ArrayList<>();
        this.board = new Board(BOARD_SIZE);
        this.shots = 0;
        placeShips();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public CellStatus shoot(String cell) {
        if (shots >= MAX_SHOTS) {
            return CellStatus.MISS;
        }

        CellStatus status = board.shoot(cell);
        if (status == CellStatus.HIT) {
            for (Ship ship : ships) {
                if (ship.isHit(cell)) {
                    if (ship.isSunk()) {
                        return CellStatus.SUNK;
                    } else {
                        return CellStatus.HIT;
                    }
                }
            }
        }

        shots++;
        return status;
    }

    public boolean hasShipsLeft() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return true;
            }
        }
        return false;
    }

    private void placeShips() {
        for (int size : SHIP_SIZES) {
            Ship ship;
            do {
                ship = new Ship(size);
            } while (!board.placeShip(ship));
            ships.add(ship);
        }
    }
}