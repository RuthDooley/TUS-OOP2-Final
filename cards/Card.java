package cards;

public sealed interface Card permits DoorCard, TreasureCard {
    String name();
    String description();
}