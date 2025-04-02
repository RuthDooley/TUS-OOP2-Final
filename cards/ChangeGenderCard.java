package cards;

import constants.Constants.CardUsageType;
import player.Player;

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

    @Override
    public void play(Player player) {
        player.setCharacterClass(name);
        player.removeCardFromHand(this);
    }
}