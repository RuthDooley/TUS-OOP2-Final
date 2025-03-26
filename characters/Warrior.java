package characters;

public record Warrior() implements CharacterClass {
    @Override
    public String getDescription() {
        return "sample";
    }
}

