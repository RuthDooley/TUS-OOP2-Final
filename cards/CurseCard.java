package cards;

import constants.Constants.CardUsageType;
import player.Player;

// SPEC: 6 record classes
public record CurseCard(String name, String description, Runnable effect) implements DoorCard {
    @Override
    public CardUsageType type() {
        return CardUsageType.IMMEDIATE;
    }

    @Override
    public void play(Player player) {
        System.out.println("Curse activated: " + name);
        effect.run();
    }
}
