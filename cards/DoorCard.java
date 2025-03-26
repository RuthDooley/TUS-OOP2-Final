package cards;

// SPEC: 4 sealed interfaces
public sealed interface DoorCard extends Card permits MonsterCard, ChangeClassCard, ChangeGenderCard, CurseCard {}