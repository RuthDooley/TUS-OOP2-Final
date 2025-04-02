package cards;

import constants.Constants.CardUsageType;

// SPEC: 6 record classes
public record MonsterCard(String name, int level, String typeImmmune, int treasureDrop, String description) implements DoorCard {
    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }

    public int getLevel() {
        return level;
    }
}
