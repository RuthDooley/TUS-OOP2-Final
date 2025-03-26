package cards;

// SPEC: 6 record classes
public record MonsterCard(int level, String typeImmmune, int treasureDrop, String description) implements DoorCard {}
