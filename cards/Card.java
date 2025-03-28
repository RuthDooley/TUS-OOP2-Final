package cards;

import constants.Constants.CardUsageType;
// SPEC: 4 sealed interfaces
public sealed interface Card permits DoorCard, TreasureCard {
    String name();
    String description();
    CardUsageType type();

    // TODO, do this out better at end with proper dash art
    default String printCard() {
        return "Card: " + name() + " " + description() + " " + this.getClass().getSimpleName();
    }
}

