package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import cards.Card;
import cards.DoorCard;
import cards.TreasureCard;

public class DeckManager {
    public static List<DoorCard> doorCardsDeck = new ArrayList<>();
    public static List<TreasureCard> treasureCardsDeck = new ArrayList<>();

    public static List<DoorCard> doorDiscardPile = new ArrayList<>();
    public static List<TreasureCard> treasureDiscardPile = new ArrayList<>();

    public static final Consumer<List<?>> shuffleDeck = Collections::shuffle;

    public static final Supplier<Card> drawDoorCard = () -> {
        if (doorCardsDeck.isEmpty()) {
            // Shuffle door discard pile and make it the new deck
            doorCardsDeck.addAll(doorDiscardPile);
            doorDiscardPile.clear();
            shuffleDeck.accept(doorCardsDeck);
        }

        DoorCard drawnCard = doorCardsDeck.remove(0);
        return drawnCard;
    };

    public static final Supplier<Card> drawTreasureCard = () -> {
        if (treasureCardsDeck.isEmpty()) {
            // Shuffle treasure discard pile and make it the new deck
            treasureCardsDeck.addAll(treasureDiscardPile);
            treasureDiscardPile.clear();
            shuffleDeck.accept(treasureCardsDeck);
        }

        TreasureCard drawnCard = treasureCardsDeck.remove(0);
        return drawnCard;
    };
}
