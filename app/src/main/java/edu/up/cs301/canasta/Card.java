/**
 * @author
 *      Ryan Hunter-Bliss
 *      Sarah Ebner
 *      Lute Lillo Portero
 */

package edu.up.cs301.canasta;

public class Card {

    private int value;
    private char suit;
    private boolean knownCard;

    /**
     * Default constructor
     * @param v (Value of the card)
     * @param s (Suit of the card)
     */
    public Card(int v, char s) {
        value = v;
        suit = s;
        knownCard = false;
    }

    /**
     * Copy constructor
     * @param orig (The original card)
     */
    public Card (Card orig) {
        value = orig.value;
        suit = orig.suit;
        knownCard = orig.knownCard;
    }

    /**
     * Converts a card into a string
     * @return (Returns the card as a string)
     */
    @Override
    public String toString() {
        return "" + suit + " " + value + " " + knownCard;
    }

    public int getValue() {
        return value;
    }
    public char getSuit() {
        return suit;
    }
    public boolean getKnownCard() { return knownCard; }
}
