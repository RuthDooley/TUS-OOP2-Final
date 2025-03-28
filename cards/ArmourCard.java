package cards;

import constants.Constants.CardUsageType;

public final class ArmourCard implements TreasureCard {
    private final String name;
    private final int isBig;
    private final String description;
    private final String requiredClass;

    public ArmourCard(String name, int isBig, String requiredClass, String description) {
        this.name = name != null ? name : "Armour";
        this.isBig = isBig;
        this.description = description != null ? description : "No description available.";
        this.requiredClass = requiredClass != null ? requiredClass : "None";
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description + " TODO"; // Keeping TODO as you had
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
}
