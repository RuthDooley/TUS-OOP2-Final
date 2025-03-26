package characters;

public record Cleric() implements CharacterClass {
    @Override
    public String getDescription() {
        return "sample";
    }
}

