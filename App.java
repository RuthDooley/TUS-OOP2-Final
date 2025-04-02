import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cards.*;
import constants.Constants;
import constants.Constants.CardUsageType;

public class App {
    static Player player = new Player();

    // SPEC: 1 Consumer lambda (Moved outside the method)
    private static final Consumer<List<?>> shuffleDeck = Collections::shuffle;
    private static final Scanner scanner = new Scanner(System.in);

    static List<DoorCard> doorCardsDeck = new ArrayList<>();
    static List<TreasureCard> treasureCardsDeck = new ArrayList<>();

    static List<DoorCard> doorDiscardPile = new ArrayList<>();
    static List<TreasureCard> treasureDiscardPile = new ArrayList<>();

    private static final Supplier<Card> drawDoorCard = () -> {
        // if (doorCardsDeck.isEmpty()) {
        //     TODO: Event for no cards
        // }

        DoorCard drawnCard = doorCardsDeck.remove(0);
        doorDiscardPile.add(drawnCard);

        return drawnCard;
    };

    private static final Supplier<Card> drawTreasureCard = () -> {
        // if (doorCardsDeck.isEmpty()) {
        //     TODO: Event for no cards
        // }

        TreasureCard drawnCard = treasureCardsDeck.remove(0);
        treasureDiscardPile.add(drawnCard);

        return drawnCard;
    };

    public static void main(String[] args) {
        Path path = Paths.get("card-desc.txt");

        // Cards from card-desc.txt, in a map of values monster, armour and combat
        Map<String, List<String[]>> inputCards = new HashMap<>();

        // SPEC: 2 Streams
        try (Stream<String> lines = Files.lines(path)) {

            // SPEC: 2a intermediate operations, .map(), .filter()
            // SPEC: 2b Collectors.groupingBy, Collectors.mapping, .collect()
            inputCards = lines
                .map(line -> line.split("\\s*,\\s*")) // Split by line, excl leading and trailing space
                .filter(data -> data.length > 1)  // More than 1 element in the array
                .collect(Collectors.groupingBy(data -> { // Get category
                    if (data[0].contains("armour")) return "armour";
                    else if (data[0].contains("combat")) return "combat";
                    else if (data[0].contains("monster")) return "monster";
                    else return "null"; 
                }, Collectors.mapping(data -> Arrays.copyOfRange(data, 1, data.length), Collectors.toList())));

            // TODO: Remove sample print 
            inputCards.forEach((category, values) -> {
                // if (category == "monster") {
                //     System.out.println("Monster Cards:");
                //     values.forEach(value -> System.out.println(Arrays.toString(value)));
                // }
                // System.out.println(category + " Cards:");
                // values.forEach(value -> System.out.println(Arrays.toString(value))); 
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        // All info including remaining card types, "cardified"

        // ** DOOR CARDS

        // * Monster cards
        // SPEC: 2b Collectors.toList()
        List<MonsterCard> monsterCards = new ArrayList<>();
        monsterCards = inputCards.get("monster").stream()
                    .map(data -> {
                        int level = Integer.parseInt(data[0].trim());
                        String typeImmmune = data[1].trim(); // TODO, instead of immune type add exxtra combat points
                        int treasureDrop = Integer.parseInt(data[2].trim()); 
                        String name = data[3].trim();
                        String description = "Lvl " + level + " monster, " + typeImmmune + " + 1 combat power, defeat yields " + treasureDrop + " treasures";

                        return new MonsterCard(name, level, typeImmmune, treasureDrop, description);
                    })
                    .collect(Collectors.toList());

        // * Change class cards
        List<ChangeClassCard> changeClassCards = new ArrayList<>();
        for (int i = 0; i < Constants.CHANGE_CLASS_CARD_COUNT; i++) {
            String[] classes = {"warrior", "wizard", "cleric"};
            String randomClass = classes[(int) (Math.random() * classes.length)];
            ChangeClassCard card = new ChangeClassCard(randomClass);
            changeClassCards.add(card);
        }

        // * Change gender cards
        List<ChangeGenderCard> changeGenderCards = new ArrayList<>();
        for (int i = 0; i < Constants.CHANGE_GENDER_CARD_COUNT; i++) {
            String[] genders = {"male", "female"};
            String randomGender = genders[(int) (Math.random() * genders.length)];
            ChangeGenderCard card = new ChangeGenderCard(randomGender);
            changeGenderCards.add(card);
        }

        // * Curse cards
        List<CurseCard> curseCards = new ArrayList<>();
        for (int i = 0; i < Constants.CURSE_CARD_COUNT; i++) {
            String[] curses = {"lose lvl", "lose armour", "lose class"}; // Losing
            String randomCurse = curses[(int) (Math.random() * curses.length)];
            CurseCard card = new CurseCard(randomCurse, "TODO");
            curseCards.add(card);
        }

        // ** TREASURE CARDS

        // * Level up cards
        List<LevelUpCard> levelUpCards = new ArrayList<>();
        for (int i = 0; i < Constants.LEVEL_UP_CARD_COUNT; i++) {
            LevelUpCard card = new LevelUpCard("+1 Level");
            levelUpCards.add(card);
        }

        // * Armour cards
        List<ArmourCard> armourCards = new ArrayList<>();
        armourCards = inputCards.get("armour").stream()
                    .map(data -> {
                        int value = Integer.parseInt(data[0].trim());
                        int isBig = Integer.parseInt(data[1].trim());
                        String requiredClass = data[2].trim();
                        String name = data[3].trim();
                        String description = "Add +" + value + " lvl in combat for " + requiredClass;

                        return new ArmourCard(name, value, isBig, requiredClass, description);
                    })
                    .collect(Collectors.toList());

        // * Combat power cards
        List<CombatPowerCard> combatPowerCards = new ArrayList<>();
        combatPowerCards = inputCards.get("combat").stream()
                    .map(data -> {
                        int monsterLvl = Integer.parseInt(data[0].trim());
                        String name = data[1].trim();
                        int bonusPower = new Random().nextInt(3) + 1; // 1 - 3 inclusive
                        String description = "Add +" + bonusPower + " lvl in combat for monster lvl >=" + monsterLvl;

                        return new CombatPowerCard(name, monsterLvl, bonusPower, description);
                    })
                    .collect(Collectors.toList());

        doorCardsDeck.addAll(monsterCards);
        doorCardsDeck.addAll(changeClassCards);
        doorCardsDeck.addAll(changeGenderCards);
        doorCardsDeck.addAll(curseCards);

        treasureCardsDeck.addAll(levelUpCards);
        treasureCardsDeck.addAll(armourCards);
        treasureCardsDeck.addAll(combatPowerCards);

        shuffleDeck.accept(doorCardsDeck);
        shuffleDeck.accept(treasureCardsDeck);

        // Draw 2 door cards, and 3 treasure cards
        for (int i = 0; i < Constants.DOOR_CARD_START_COUNT; i++) 
            player.addCardToHand(drawCard.apply(doorCardsDeck));

        for (int i = 0; i < Constants.TREASURE_CARD_START_COUNT; i++) 
            player.addCardToHand(drawCard.apply(treasureCardsDeck));

        // Draw a door card, return the card drawn and add to the appropriate discard pile


        // Game loop
        // TODO: Curse cards in hand must be used immediately
        // TODO: Ask the user for their gender
        int turnCount = 0;
        int turnCycle = 0;
        while (true) {

            // Get the options for turn and print
            String selectedOption = calculateTurnOptionsStart();
            
            switch (selectedOption) {
                case "Draw a Door Card" -> {
                    DoorCard drawnCard = (DoorCard) drawDoorCard.get();
            
                    if (drawnCard instanceof MonsterCard monster) {
                        triggerCombat(monster);
                    } else if (drawnCard instanceof CurseCard curse) {
                        System.out.println(curse.name());
                        // Parse the card from the selected

                        // Complete curse action
                        // if 
                        
                        // TODO: If drawn card is curse play instantly
                    } else {
                        player.addCardToHand(drawnCard);
                    }
                }
                // default -> {
                //     playCard(getCardByName(selectedOption, player), player);
                // } // TODO: MAKE FUNCTIONALITY
            }


            // Step 2: Recieve user input
            // String userInput = getPlayerChoice(); // TODO: Lambda?

            // // Step 3: If decison is not to play monster from hand or draw door card, excute action of card chosen
            // if (!userInput.equals("draw door card") && !userInput.equals("play monster from hand")) {
            //     executeCardAction(userInput); // TODO
            // } else {
            //     turnCount++;
            //     Card drawnCard = drawDoorCard(); // TODO: Lambda?

            //     if (drawnCard instanceof MonsterCard monsterCard) {
            //         triggerCombat(monsterCard);
            //     } else if (drawnCard instanceof CurseCard curseCard) {
            //         applyCurse(curseCard); // TODO: Lambda?
            //     } else {
            //         player.addCardToHand(drawnCard);
            //         drawTreasureCard(); // TODO: Lambda?
            //     }
            // }

            // // Step 4: Exit loop if tokens is >= 10 or <= 0
            // if (player.getTokens() >= 10) {
            //     // TODO: WIN EVENT
            //     break;
            // } else if (player.getTokens() <= 0) {
            //     // TODO: LOSE EVENT
            //     break;
            // }
        }
    }

    private static final Function<List<? extends Card>, Card> drawCard = deck -> {
        // TODO: Maybe handle the empty deck somewhere else
        if (!deck.isEmpty()) {
            return deck.remove(0); // Draw the top card
        }
        System.out.println("Deck is empty!");
        return null;
    };

    private static void triggerCombat(MonsterCard monster) {
        while (true) {
            String selectedOption = calculateTurnOptionsStart();

            switch (selectedOption) {
                case "Fight Monster" -> {
                    if (player.getTokens() + player.getArmourValue() + player.getCombatPower() > monster.getLevel()) {
                            player.addToken();
                            player.clearCombatPowerCards();
                            break;
                        }
                    }
                case "Run Away" -> triggerRunAway(monster);
                default -> {
                    // Parse the combat power card from the option selected

                    // equipCombatPower(selectedOption);
                }
            }
        }
    }

    private static void triggerRunAway(MonsterCard monster) {
        player.clearCombatPowerCards();
        int diceRoll = new Random().nextInt(6) + 1;

        if (diceRoll < 5) {
            player.removeToken();
        }
    }

    public static int getUserInput(ArrayList<String> options) {
        int choice = -1;

        while (true) {
            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= options.size()) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 1 and " + options.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    /**
     * Return a list of Cards that can be played at the start of the turn.
     * In the main game loop the array list of strings in option menu will be generated
     */
    public static String calculateTurnOptionsStart() {
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
                optionsStart.add("Play " + card.name());
            }
        }

        optionsStart.add("Save & Exit");
     
        // Display options
        GameUI.printTurnOptions(player, optionsStart);
     
        // TODO: Continue to ask for choice if not a valid choice
        int choice = getUserInput(optionsStart);
        return optionsStart.get(choice - 1); 
    }

    public static String calculateTurnOptionsCombat(MonsterCard monster) {
        /**
         * The options on combat are:
         * 1. Play combat power cards from hand
         * 2. Fight monster
         * 3. Run away
         */

        ArrayList<String> optionsCombat = new ArrayList<>();
     
        for (Card card : player.getHand()) {
            if (card.type() == CardUsageType.COMBAT) {
                optionsCombat.add("Play " + card.name());
            }
        }

        optionsCombat.add("Fight Monster");
        optionsCombat.add("Run Away");
     
        // Display options
        GameUI.printCombatOptions(player, monster, optionsCombat);
     
        // TODO: Continue to ask for choice if not a valid choice
        int choice = getUserInput(optionsCombat);
        return optionsCombat.get(choice - 1); 
    }
}

// Monster type, level, run away condition, treasure, description?
// Armour type, big, class, description?
// Combat card type, monster level, description?