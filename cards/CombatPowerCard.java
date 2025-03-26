package cards;

public record CombatPowerCard(String name, String description, String monsterType, int bonusPower) implements TreasureCard {
    @Override
    public String description() {
        return "Gives +" + bonusPower + " combat power against " + monsterType + " monsters.";
    }
}