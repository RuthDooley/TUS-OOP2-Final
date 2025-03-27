package cards;

// SPEC: 4 sealed interfaces
public sealed interface Card permits DoorCard, TreasureCard {
    String name();
    String description();

    // TODO, do this out better at end with proper dash art
    // UI 2
    default String printCard() {
        return "Card: " + name() + " " + description() + " " + this.getClass().getSimpleName();
    }
}

