package cards;

// SPEC: 4 sealed interfaces
public sealed interface TreasureCard extends Card permits LevelUpCard, ArmourCard, CombatPowerCard {}