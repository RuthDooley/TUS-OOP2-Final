package characters;

public sealed interface CharacterClass permits Warrior, Wizard, Cleric {
    String getDescription();
}