package game;

import java.util.ArrayList;
import java.util.List;

import cards.ArmourCard;
import cards.Card;
import constants.Constants.CardUsageType;
import player.Player;
import ui.GameUI;

public class CardManager {
        /**
     * Return a list of Cards that can be played at the start of the turn.
     * In the main game loop the array list of strings in option menu will be generated
     */
    public static String calculateTurnOptionsStart(Player player) {
        /**
         * The options on turn start are:
         * 1. Play monster from hand
         * 2. Equip armour from hand
         * 3. Change class from card from hand
         * 4. Change gender from card from hand
         * 5. Use level up card from hand
         * 6. Draw door card
         */

        ArrayList<String> optionsStart = new ArrayList<>();
     
        optionsStart.add("Draw a Door Card");
     
        for (Card card : player.getHand()) {
            if (card.type() == CardUsageType.START_OF_TURN) {
                // Armour can only be played for the right character class
                if (card instanceof ArmourCard armourCard) {
                    if (player.getCharacterClass() != null && player.getCharacterClass().equals(armourCard.requiredClass())) {
                        optionsStart.add("Play " + card.name());
                    }
                } else {
                    optionsStart.add("Play " + card.name());
                }
            }
        }

        optionsStart.add("Save & Exit");
        optionsStart.add("Exit");
     
        // Display options
        GameUI.printTurnOptions(player, optionsStart);
     
        // TODO: Continue to ask for choice if not a valid choice
        int choice = PlayManager.getUserInput(optionsStart.size());
        return optionsStart.get(choice - 1); 
    }

    // Form an array list of card, return a string of the card names only. This is for parsing for the game state
    // TODO: Maybe put this somewhere else?
    public static List<String> listOfCardNameStringsFromCardArrayList(List<? extends Card> cards) {
        List<String> cardNames = new ArrayList<>();
        for (Card card : cards) {
            cardNames.add(card.name());
        }
        return cardNames;
    }

    /**
     * Given an array list deck of cards, and a array list of strings (represent cards), 
     * Find the card in the deck add it to a new list, remove it from the original list and repeat for the rest of the strings.
     * Return the new list 
     */
    public static ArrayList<Card> extractCardsByName(List<? extends Card> allCardsDeck, List<String> cardNames) {
        ArrayList<Card> extractedCards = new ArrayList<>();
        ArrayList<Card> deckCopy = new ArrayList<>(allCardsDeck);
    
        for (String cardName : cardNames) {
            Card matchingCard = deckCopy.stream()
                    .filter(card -> card.name().equalsIgnoreCase(cardName))
                    .findFirst()
                    .orElse(null);
    
            // Remove from the deck, call by reference
            if (matchingCard != null) {
                extractedCards.add(matchingCard);
                deckCopy.remove(matchingCard);
            }
        }
    
        return extractedCards;
    }
}
