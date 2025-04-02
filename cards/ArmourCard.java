package cards;

import java.util.function.Consumer;

import constants.Constants.CardUsageType;
import player.Player;

public final class ArmourCard implements TreasureCard {
    private final String name;
    private final int value;
    private final int isBig;
    private final String description;
    private final String requiredClass;
    private final Consumer<Player> action;

    public ArmourCard(String name, int value, int isBig, String requiredClass, String description) {
        this.name = name;
        this.value = value;
        this.isBig = isBig;
        this.description = description != null ? description : "No description available.";
        this.requiredClass = requiredClass != null ? requiredClass : "None";
        this.action = player -> {
            player.addArmour(this);
            player.removeCardFromHand(this);
        };
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }


    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }

    public String requiredClass() { 
        return requiredClass; 
    }
    
    public int isBig() { 
        return isBig; 
    }

    public int value() { 
        return value; 
    }

    @Override
    public void play(Player player) {
        action.accept(player);  // Execute the action when played
    }
}
