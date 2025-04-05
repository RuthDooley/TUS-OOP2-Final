package savegame;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GameState implements Serializable {
    // All cards stored as strings
    private static final long serialVersionUID = 1L;

    private final String playerGender;
    private final String playerClass;
    private final int playerTokens;

    private final List<String> playerHand;
    private final List<String> playerArmour;
    private final List<String> playerCombatPowerCards;

    private final List<String> treasureDeck;
    private final List<String> doorDeck;
    private final List<String> treasureDiscardPile;
    private final List<String> doorDiscardPile;

    private final int turnNumber;
    private final LocalDateTime saveTime;

    public GameState(
        String playerGender,
        String playerClass,
        int playerTokens,
        List<String> playerHand,
        List<String> playerArmour,
        List<String> playerCombatPowerCards,
        List<String> treasureDeck,
        List<String> doorDeck,
        List<String> treasureDiscardPile,
        List<String> doorDiscardPile,
        int turnNumber,
        LocalDateTime saveTime
    ) {
        this.playerGender = playerGender;
        this.playerClass = playerClass;
        this.playerTokens = playerTokens;
        this.playerHand = playerHand;
        this.playerArmour = playerArmour;
        this.playerCombatPowerCards = playerCombatPowerCards;
        this.treasureDeck = treasureDeck;
        this.doorDeck = doorDeck;
        this.treasureDiscardPile = treasureDiscardPile;
        this.doorDiscardPile = doorDiscardPile;
        this.turnNumber = turnNumber;
        this.saveTime = saveTime;
    }

    // Getters for all fields
    public String getPlayerGender() {
        return playerGender;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public int getPlayerTokens() {
        return playerTokens;
    }

    public List<String> getPlayerHand() {
        return playerHand;
    }

    public List<String> getPlayerArmour() {
        return playerArmour;
    }

    public List<String> getPlayerCombatPowerCards() {
        return playerCombatPowerCards;
    }

    public List<String> getTreasureDeck() {
        return treasureDeck;
    }

    public List<String> getDoorDeck() {
        return doorDeck;
    }

    public List<String> getTreasureDiscardPile() {
        return treasureDiscardPile;
    }

    public List<String> getDoorDiscardPile() {
        return doorDiscardPile;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public LocalDateTime getSaveTime() {
        return saveTime;
    }
}