package cards;

import constants.Constants.CardUsageType;

// SPEC: 6 record classes
public record MonsterCard(int level, String typeImmmune, int treasureDrop, String description) implements DoorCard {
    @Override
    public String name() {
        return "";
    }

    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN; // TODO: Also triggered immediately if drawn from door, might have to think of another wya to implement this
    }
}
