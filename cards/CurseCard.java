package cards;

import constants.Constants.CardUsageType;

// SPEC: 6 record classes
public record CurseCard(String name, String description) implements DoorCard {
    @Override
    public String description() {
        return "Instantly " + name;
    }

    @Override
    public CardUsageType type() {
        return CardUsageType.IMMEDIATE;
    }
}
