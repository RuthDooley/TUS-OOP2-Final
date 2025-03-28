import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import characters.CharacterClass;
import constants.Constants;
import cards.Card;
import cards.ArmourCard;

public class Player {
    private String gender;
    private CharacterClass characterClass;
    private int tokens;
    private final List<Card> hand;
    private final List<ArmourCard> armour;

    public Player() {
        this.gender = "male";
        this.characterClass = null;
        this.tokens = Constants.STARTING_TOKENS; 
        this.hand = new ArrayList<>();
        this.armour = new ArrayList<>();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public int getTokens() {
        return tokens;
    }

    public void addToken() {
        tokens++;
    }

    public boolean removeToken() {
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false; // TODO: Event on false
    }

    public void printTokens() {
        System.out.println("Tokens: " + tokens);
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void addCardToHand(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    public List<ArmourCard> getArmour() {
        return Collections.unmodifiableList(armour);
    }

    public void addArmour(ArmourCard armourCard) {
        if (armourCard != null) {
            armour.add(armourCard);
        }
    }

    public void removeArmour(ArmourCard armourCard) {
        armour.remove(armourCard);
    }

    public void setArmour(List<ArmourCard> newArmour) {
        this.armour.clear();
        if (newArmour != null) {
            this.armour.addAll(newArmour);
        }
    }

    // SPEC: 2a intermediate operations, .forEach()
    public void printArmour() {
        System.out.println("Armour:");
        if (armour == null || armour.isEmpty()) {
            System.out.println("No armour equipped.");
            return;
        }
        armour.forEach(card -> System.out.println(card.printCard()));
    }

    // SPEC: 2a intermediate operations, .forEach()
    public void printHand() {
        System.out.println("Cards in Hand:");
        hand.forEach(card -> System.out.println(card.printCard()));
    }

    public void printMoveOptions(ArrayList<String> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + options.get(i));
        }
    }

    @Override
    public String toString() {
        return "Gender: '" + gender + "', CharacterClass: " + characterClass +
               ", Tokens: " + tokens + ", Hand: " + hand + ", Armour: " + armour;
    }
}
