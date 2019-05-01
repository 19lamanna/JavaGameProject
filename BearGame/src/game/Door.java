package game;

import GameEngine.GameContainer;
import GameEngine.Renderer;
import gfx.Image;

public class Door extends Sign
{
  protected Image DoorImage;
//  protected int tileY, tileX;
  protected Item neededItem;
  protected boolean LockedToRead = false;
  String message;
  String closedMessage;
  String openMessage;
  public Door(int tileX, int tileY, String tag, String closedMessage, String openMessage,  String path, int color ,Item needed)
  {
    super(tileX, tileY, tag, closedMessage,  path, "woosh.wav", color, -10);
    neededItem = needed;
    message = closedMessage;
    this.closedMessage = closedMessage;
    this.openMessage = openMessage;
  }

  public void update(GameContainer gc, GameManager gm, float dt)
  {
    
    if(LockedToRead)
    {
      if(closedMessage!=null)
      {
        sayMessage(gm,gc,gc.getRenderer());
      }
    
    }
    LockedToRead = false;
    if(neededItem != null)
    {
      for(Item itm : gm.getHUD().getInventory())
      {
        if(itm.getTag() == neededItem.getTag())
        {
          if(closedMessage!=null)
          {
          message = openMessage;
          }
        }
      }
    }
    
  }

//  public void render(GameContainer gc, Renderer r)
//  {
//    r.drawImage(DoorImage, this.getPosX(), this.getPosY());
//  }
  

  public boolean ableToGoThru(GameManager gm)
  {
    
    boolean able = false;
    if(neededItem != null)
    {
      for(Item itm : gm.getHUD().getInventory())
      {
        if(itm.getTag() == neededItem.getTag())
        {
          able = true;
        }
      }
    }else
    {
      able = true;
    }
    
    
    
    return able;
  }
  
  public void setLockedToReadBoolean(boolean toBeLocked)
  {
    this.LockedToRead = toBeLocked;
  }
  
  public void sayMessage(GameManager gm, GameContainer gc, Renderer r)
  {
    if(closedMessage!=null)
    {
      if(!clip.isRunning())
      {
        clip.play();
      }
      
    gm.getHUD().setMessage(message, color);
    }
    
//    r.drawText("Press X", tileX - 16, tileX - 16,  0xff00ff00);
  }
  
  
  public int getXTile()
  {
    return tileX;
  }
  public int getYTile()
  {
    return tileY;
  }
  public int getPosY()
  {
    return ((200*(tileY-1) / 25)+28);
  }
  public int getPosX()
  {
    return ((8*tileX));
  }
  
  public void playClip()
  {
    clip.play();
  }


}
