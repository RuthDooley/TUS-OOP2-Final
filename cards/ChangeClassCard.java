package cards;

public record ChangeClassCard(String name) implements DoorCard {
    @Override
    public String description() {
        return "Allows you to change your character class.";
    }
}