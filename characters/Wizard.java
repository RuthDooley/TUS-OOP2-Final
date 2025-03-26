package characters;

public record Wizard() implements CharacterClass {
    @Override
    public String getDescription() {
        return "sample";
    }
}

