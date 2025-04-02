package cards;

import constants.Constants.CardUsageType;
// SPEC: 6 record classes
public record ChangeClassCard(String name) implements DoorCard {
    @Override
    public String description() {
        return "Change current class to " + name;
    }

    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }
}