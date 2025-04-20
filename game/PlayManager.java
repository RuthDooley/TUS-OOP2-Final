package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cards.Card;
import cards.CombatPowerCard;
import cards.DoorCard;
import cards.MonsterCard;
import cards.TreasureCard;
import constants.Constants.CardUsageType;
import player.Player;
import ui.GameUI;

public class PlayManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getUserInput(int optionsSize) {
        int choice = -1;

        while (true) {
            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= optionsSize) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 1 and " + optionsSize);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    public static void triggerCombat(MonsterCard monster, Player player) {
        if (monster.typeImmune() == player.getGender()) {
            System.out.println(player.getGender() + "'s get +5 combat power against " + monster.name());
            player.addCombatPowerCardToHand(new CombatPowerCard("Gender Boost", 0, 5, "Gender specific +5 combat"));
        }

        // Discard the monster card
        DeckManager.doorDiscardPile.add(monster); 

        String selectedOption = calculateTurnOptionsCombat(monster, player);

        switch (selectedOption) {
            case "Fight Monster" -> {
                // Specific classes get a boost for monster type
                if (player.getTokens() + player.getArmourValue() + player.getCombatPower() + 
                (monster.typeImmune() == player.getGender() ? 5 : 0) 
                > monster.level()) {
                        player.addToken();

                        for (Card card : player.getCombatPowerCards()) {
                            if (card instanceof CombatPowerCard) {
                                // Discard combat power cards
                                DeckManager.treasureCardsDeck.add((TreasureCard)card);
                            }
                        }
                        player.clearCombatPowerCards();
                        DeckManager.doorDiscardPile.add(monster);

                        // Pick up treasure card reward
                        ArrayList<TreasureCard> treasureCards = new ArrayList<>();
                        for (int i = 0; i < monster.treasureDrop(); i++) {
                            TreasureCard treasureCard = (TreasureCard) DeckManager.drawTreasureCard.get();
                            player.addCardToHand(treasureCard);
                        }

                        GameUI.printCombatSucess(player, treasureCards);
                        break;
                    } else {
                        // Run away triggered otherwise
                        PlayManager.triggerRunAway(monster, player);
                        break;
                    }

                }
            case "Run Away" -> {
                PlayManager.triggerRunAway(monster, player);
                break;
            }
            default -> {
                Card selectedCard = parseCardFromOptionString("Play ", selectedOption, player);
                selectedCard.play(player);

                // Discard card appropriately
                switch (selectedCard) {
                    case DoorCard doorCard -> DeckManager.doorDiscardPile.add(doorCard);
                    case TreasureCard treasureCard -> DeckManager.treasureDiscardPile.add(treasureCard);
                    default -> System.out.println("Error handelling, unknown card type");
                }
            }
        }

    }

    public static void triggerRunAway(MonsterCard monster, Player player) {
        for (Card card : player.getCombatPowerCards()) {
            if (card instanceof CombatPowerCard) {
                // Discard combat power cards
                DeckManager.treasureCardsDeck.add((TreasureCard)card);
            }
        }

        System.out.println(player.getCombatPowerCards());
        if (player.getCombatPowerCards() instanceof ArrayList) {
            System.out.println("combatPowerCards is an ArrayList.");
        } else {
            System.out.println("combatPowerCards is not an ArrayList.");
        }
        if (player.getCombatPowerCards() != null) {
            System.out.println("combatPowerCards is of type: " + player.getCombatPowerCards().getClass().getName());
        } else {
            System.out.println("combatPowerCards is null.");
        }
        System.out.println(player.getCombatPowerCards() == null);
        System.out.println(player.getCombatPowerCards().isEmpty());

        if (player.getCombatPowerCards() == null) {
            player.clearCombatPowerCards();
        }

        int diceRoll = new Random().nextInt(6) + 1;

        if (diceRoll < 5) {
            player.removeToken();
        }

        GameUI.printRunAwayResult(diceRoll, player);
    }

    public static String calculateTurnOptionsCombat(MonsterCard monster, Player player) {
        /**
         * The options on combat are:
         * 1. Play combat power cards from hand, if combat allows it 
         * 2. Fight monster
         * 3. Run away
         */

        ArrayList<String> optionsCombat = new ArrayList<>();
     
        for (Card card : player.getHand()) {
            if (card.type() == CardUsageType.COMBAT) {
                // Combat power cards can only be played for the right monster level
                if (card instanceof CombatPowerCard combatPowerCard) {
                    if (monster.level() >= combatPowerCard.monsterLvl()) {
                        optionsCombat.add("Play " + card.name());
                    }
                } 
            }
        }

        optionsCombat.add("Fight Monster");
        optionsCombat.add("Run Away");

        // Display options
        GameUI.printCombatOptions(player, monster, optionsCombat);
    
        int choice = PlayManager.getUserInput(optionsCombat.size());
        return optionsCombat.get(choice - 1); 
    }

    // Return the card from the player's hand given the option string
    public static Card parseCardFromOptionString(String stringToParseOut, String option, Player player) {
        String cardName = option.replace(stringToParseOut, "").trim();

        // Cycle through the player's hand to find the card by name
        for (Card card : player.getHand()) {
            if (card.name().equals(cardName)) {
                return card;
            }
        }
        return null;
    }
}
