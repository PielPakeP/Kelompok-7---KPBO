import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MemoryGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MemoryGame extends World
{
    private Card firstCard;
    private Card secondCard;
    
    public MemoryGame()
    {
        super(600, 500, 1);

        createCards();
    }

    private void createCards()
    {
        addObject(new Card("sapi.png"), 100, 150);
        addObject(new Card("sapi.png"), 240, 150);

        addObject(new Card("bunga.png"), 380, 150);
        addObject(new Card("bunga.png"), 520, 150);

        addObject(new Card("lab.png"), 100, 330);
        addObject(new Card("lab.png"), 240, 330);

        addObject(new Card("universe.png"), 380, 330);
        addObject(new Card("universe.png"), 520, 330);
    }
    
    public void act()
    {
        checkCards();
    }
    
    private void checkCards()
    {
        java.util.List<Card> cards = getObjects(Card.class);

        Card first = null;
        Card second = null;

    for (Card card : cards)
    {
        if (card.isOpen() && !card.isMatched())
        {
            if (first == null)
            {
                first = card;
            }
            else if (second == null)
            {
                second = card;
            }
        }
    }

    if (first != null && second != null)
    {
        if (first.getCardName().equals(second.getCardName()))
        {
            first.setMatched();
            second.setMatched();
        }
        else
        {
            Greenfoot.delay(30);

            first.closeCard();
            second.closeCard();
        }
    }
}
}
