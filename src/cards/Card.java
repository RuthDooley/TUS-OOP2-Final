package cards;

import constants.Constants.CardUsageType;
import player.Player;
// SPEC: 4 sealed interfaces
public sealed interface Card permits DoorCard, TreasureCard {
    String name();
    String description();
    CardUsageType type();
    void play(Player player);

    default String printCard() {
        return "Card: " + name() + " | " + description() + " | " + this.getClass().getSimpleName();
    }
}