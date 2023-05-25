package fr.lernejo.navy_battle;

import java.util.HashMap;
import java.util.Map;

public class BattleGame {
    private final Map<String, String> gameGrid;
    private boolean shipLeft = true;  // suppose qu'il y a un navire à l'initiation du jeu

    public BattleGame() {
        this.gameGrid = initializeGameGrid();
    }

    private Map<String, String> initializeGameGrid() {
        // Initialiser la grille du jeu avec des cellules vides. Cette grille peut être modifiée pour représenter votre jeu.
        Map<String, String> gameGrid = new HashMap<>();
        for (char row = 'A'; row <= 'J'; row++) {
            for (int column = 1; column <= 10; column++) {
                gameGrid.put(String.valueOf(row) + column, "empty");
            }
        }
        // Supposez que nous avons un navire à la position B2 pour la démonstration
        gameGrid.put("B2", "ship");
        return gameGrid;
    }

    public Map<String, String> getGameGrid() {
        return gameGrid;
    }

    // Méthode pour gérer le tir à une cellule spécifique
    public Map<String, Object> fire(String cell) {
        String cellStatus = gameGrid.get(cell);
        Map<String, Object> result = new HashMap<>();

        switch (cellStatus) {
            case "empty":
                result.put("consequence", "miss");
                break;
            case "ship":
                result.put("consequence", "hit");
                gameGrid.put(cell, "hit");  // Marquez cette cellule comme touchée
                break;
            case "hit":
                result.put("consequence", "sunk");
                gameGrid.put(cell, "sunk");  // Marquez cette cellule comme coulée
                // Ici, vous devriez vérifier s'il reste encore des navires et mettre à jour shipLeft en conséquence
                shipLeft = false;  // Pour cet exemple, nous supposons qu'il n'y a qu'un seul navire qui a maintenant été coulé
                break;
            default:
                // Gérer les cas non prévus
        }

        result.put("shipLeft", shipLeft);
        return result;
    }
}
