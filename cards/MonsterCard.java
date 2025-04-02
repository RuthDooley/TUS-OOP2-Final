package cards;

import constants.Constants.CardUsageType;
import player.Player;

// SPEC: 6 record classes
public record MonsterCard(String name, int level, String typeImmmune, int treasureDrop, String description, Runnable effect) implements DoorCard {
    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void play(Player player) {
        player.removeCardFromHand(this);
        System.out.println("Combat triggered with " + name);
        effect.run(); // TODO: Need to decide do I want to trigger combat here
    }
}
