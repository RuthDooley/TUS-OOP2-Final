package cards;

// SPEC: 6 record classes
public record CombatPowerCard(String name, int monsterLvl, int bonusPower, String description) implements TreasureCard {
    @Override
    public String description() {
        return "Gives +" + bonusPower + " combat power against " + monsterLvl + " monsters.";
    }
}