import greenfoot.*;

public class MyWorld extends World
{
    public MyWorld()
    {    
        super(600, 400, 1); 

        GreenfootImage bg = new GreenfootImage("Maps.jpg");
        bg.scale(600, 400);
        setBackground(bg);

        prepare();
    }

    private void prepare()
    {
        Player player = new Player();
        addObject(player, 143, 130);
    }
}