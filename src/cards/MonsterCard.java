package cards;

import constants.Constants.CardUsageType;
import player.Player;

// SPEC: 6 record classes
public record MonsterCard(String name, int level, String typeImmune, int treasureDrop, String description, Runnable effect) implements DoorCard {
    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }

    @Override
    public void play(Player player) {
        player.removeCardFromHand(this);
        System.out.println("Combat triggered with " + name);
    }
}
