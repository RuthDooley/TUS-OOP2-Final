package cards;

public record LevelUpCard(String name) implements TreasureCard {
    @Override
    public String description() {
        return "Grants you an immediate level-up.";
    }
}