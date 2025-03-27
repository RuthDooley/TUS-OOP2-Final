import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import characters.CharacterClass;
import cards.Card;

public class Player {
    private String gender;
    private CharacterClass characterClass;
    private final List<Card> hand;

    public Player() {
        this.gender = "male";
        this.characterClass = null;
        this.hand = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Gender:'" + gender + " CharacterClass:" + characterClass + " Hand:" + hand;
    }
}