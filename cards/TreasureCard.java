package cards;

public sealed interface TreasureCard extends Card permits LevelUpCard, ArmourCard, CombatPowerCard {}