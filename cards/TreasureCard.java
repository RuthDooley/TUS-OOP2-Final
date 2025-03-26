package cards;

public sealed interface TreasureCard extends Card permits LevelUpCard, ArmorCard, CombatPowerCard {}