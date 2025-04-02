package cards;

import constants.Constants.CardUsageType;

// SPEC: 6 record classes
public record ChangeGenderCard(String name) implements DoorCard {
    @Override
    public String description() {
        return "Change gender to " + name;
    }

    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }
}