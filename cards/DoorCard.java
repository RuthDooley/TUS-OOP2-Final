package cards;

public sealed interface DoorCard extends Card permits MonsterCard, ChangeClassCard, ChangeGenderCard, CurseCard {}