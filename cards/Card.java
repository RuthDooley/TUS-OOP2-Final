package cards;

// SPEC: 4 sealed interfaces
public sealed interface Card permits DoorCard, TreasureCard {
    // String name();
    String description();
}