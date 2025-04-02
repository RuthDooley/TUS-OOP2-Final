package cards;

import constants.Constants.CardUsageType;
import player.Player;

// SPEC: 6 record classes
public record LevelUpCard(String name) implements TreasureCard {
    @Override
    public String description() {
        return "Grants you an immediate level-up.";
    }

    @Override
    public CardUsageType type() {
        return CardUsageType.START_OF_TURN;
    }

    @Override
    public void play(Player player) {
        player.addToken();
        player.removeCardFromHand(this);
    }
}