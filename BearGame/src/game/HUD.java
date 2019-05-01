package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GameEngine.GameContainer;
import GameEngine.Renderer;

public class HUD
{
  boolean firstMessage = true;
  
  private String message = "WASD/arrows to move, ENTER to interakt,                                  click in the game container to begin playing";
  private int color = 0xfffffff;
   private ArrayList<Item> inventory = new ArrayList<Item>();
   int inventoryHeight = 10;
  
  public void render(GameContainer gc, Renderer r)
  {
  
    r.drawText("------------------------------------", 0, 145, 0xffffffff);
    r.drawTextWrapping(message, 0, 153, color);
    
    
    r.drawText("Inventory:", 0, 10, 0xffffff);
    
    for(int i = 0; i<inventory.size(); i++)
    {
      r.drawImage(inventory.get(i).getImage(), 46 + 10*i, inventoryHeight );
    }
    
    
    
//    if(message.equals("                                  "))
//    {
//      r.drawFillRectSimple(0,155,200,20, 0);
//    }
    
    
//    r.drawFillRect(0,148, 33, 218, 0xfffffff);
    
  }
  public void update(GameContainer gc, GameManager gm)
  {
    if(!firstMessage)
    {
      for(int i = 0; i< gc.getInput().getKeys().length; i++)
      {
        if(gc.getInput().getKeys()[i] && !gc.getInput().isKey(KeyEvent.VK_ENTER))
        {
          message = "                                  ";
          System.out.println("ogreriding");
        }
      }
    }
//    inventoryHeight = gc.getHeight() - 10;
  }
  public void setMessage(String str, int color)
  {
        message = str;
//        System.out.println(message);
        
        this.color = color;
//        System.out.println(color);
        firstMessage = false;
  }
  public ArrayList<Item> getInventory()
  {
    return inventory;
  }
  
  
}
