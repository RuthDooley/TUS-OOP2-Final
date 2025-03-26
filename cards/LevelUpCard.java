package cards;

// SPEC: 6 record classes
public record LevelUpCard() implements TreasureCard {
    // TODO: Is name being included or not?
    // @Override
    // public String name() {
    //     return "Level Up";
    // }

    @Override
    public String description() {
        return "Grants you an immediate level-up.";
    }
}