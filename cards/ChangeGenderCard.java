package cards;

public record ChangeGenderCard(String name) implements DoorCard {
    @Override
    public String description() {
        return "Changes your character's gender.";
    }
}