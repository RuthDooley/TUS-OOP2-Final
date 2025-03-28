package cards;

import constants.Constants.CardUsageType;

// SPEC: 6 record classes
public record LevelUpCard() implements TreasureCard {
    @Override
    public String name() {
        return "";
    }

    @Override
    public String description() {
        return "Grants you an immediate level-up.";
    }

    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }
}