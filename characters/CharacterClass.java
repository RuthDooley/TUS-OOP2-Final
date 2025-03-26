package characters;

// SPEC: 4 sealed classes
public abstract sealed class CharacterClass permits Warrior, Wizard, Cleric {}