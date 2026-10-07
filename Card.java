import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Cards here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Card extends Actor
{
    private GreenfootImage frontImage;
    private GreenfootImage backImage;

    private String cardName;

    private boolean isOpen = false;
    private boolean isMatched = false;

    public Card(String imageName)
    {
        cardName = imageName;

        frontImage = new GreenfootImage(imageName);
        backImage = new GreenfootImage("1.png");

        frontImage.scale(100, 120);
        backImage.scale(100, 120);

        setImage(backImage);
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            openCard();
        }
    }

    public void openCard()
    {
        if (!isOpen && !isMatched)
        {
            isOpen = true;
            setImage(frontImage);
        }
    }

    public void closeCard()
    {
        if (!isMatched)
        {
            isOpen = false;
            setImage(backImage);
        }
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public boolean isMatched()
    {
        return isMatched;
    }

    public void setMatched()
    {
        isMatched = true;
    }

    public String getCardName()
    {
        return cardName;
    }
}
