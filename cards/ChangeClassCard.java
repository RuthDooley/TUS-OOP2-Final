package cards;

// SPEC: 6 record classes
public record ChangeClassCard(String newClass) implements DoorCard {
    @Override
    public String name() {
        return "";
    }

    @Override
    public String description() {
        return "TODO";
    }
}