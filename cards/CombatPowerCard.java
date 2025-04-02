package cards;

import constants.Constants.CardUsageType;
import player.Player;

// SPEC: 6 record classes
public record CombatPowerCard(String name, int monsterLvl, int bonusPower, String description) implements TreasureCard {
    @Override
    public CardUsageType type() {
        return CardUsageType.COMBAT;
    }

    @Override
    public void play(Player player) {
        player.addCombatPowerCardToHand(this);
        player.removeCardFromHand(this);
    }
}