package cards;

// SPEC: 6 record classes
public record ChangeGenderCard(String newGender) implements DoorCard {
    @Override
    public String name() {
        return "";
    }

    @Override
    public String description() {
        return "TODO";
    }
}