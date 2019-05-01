package game;

import GameEngine.GameContainer;
import GameEngine.Renderer;
import audio.SoundClip;
import gfx.Image;

public class Portal extends GameObject
{
  protected Image PortalImage;
  protected int tileY, tileX;
  protected int dimY, dimX;
  protected Item neededItem = new Item(23,14, "default", "/Sprites/KeySprite.png", "/audio/nothing.wav", 0);
  protected String levelToLoad;
  protected String closedMessage;
  protected boolean ableToLoad = false;
  
  private SoundClip clip;
  private SoundClip noClip;
  
  public Portal(int tileX, int tileY, int dimY, int dimX, String tag, String path, Item neededItem, String levelToLoad, String closedMessage)
  {
    this.tileY = tileY;
    this.tileX = tileX;
    this.dimY = dimY;
    this.dimX = dimX;
    this.closedMessage = closedMessage;
    if(neededItem != null)
    {
      this.neededItem = neededItem;
    }
    this.levelToLoad = levelToLoad;
    
    this.tag = tag;
    PortalImage = new Image(path);
    clip= new SoundClip("/audio/bullet.wav");
    noClip = new SoundClip("/audio/punch.wav");
    clip.setVolume(-20);
  }
  
  public void update(GameContainer gc, GameManager gm, float dt)
  {
   ableToLoad = false; 
    if(!neededItem.getTag().equals("default"))
    {
      for(Item itm : gm.getHUD().getInventory())
      {
        if(itm.getTag().equals(neededItem.getTag()))
        {
          
          ableToLoad = true;
        }
      }
    }else
    {
      ableToLoad = true;
    }
//    
  }
  
  public boolean AbleToLoad()
  {
    return ableToLoad;
  }
    
  public void render(GameContainer gc, Renderer r)
  {
    r.drawImage(PortalImage, this.getPosX(), this.getPosY());
  }

  public int getXTile()
  {
//    System.out.println((posX * 30 / 218));

    return tileX;
  }
  public int getYTile()
  {
//    System.out.println((posY * 23 / 180)-2);
    return tileY;
  }
  
  public void SayMessage(GameManager gm)
  {
    
    if(!noClip.isRunning())
    {
      noClip.play();
    }
    gm.getHUD().setMessage(closedMessage, 0xfffffff);
//    System.out.println("denying");
  }
  
  public int getPosY()
  {
    return ((200*(tileY-1) / 25)+28);
  }
  public int getPosX()
  {
    return ((8*tileX));
  }
  
  public int getDimY()
  {
    return dimY;
  }

  public int getDimX()
  {
    return dimX;
  }
  
  public void placeInMap(String[][] curlev)
  {
    for(int x = this.getXTile(); x <= this.getXTile() + this.getDimX(); x++)
    {
      for(int y = this.getYTile(); y <= this.getYTile() + this.getDimY(); y++)
      {
        curlev[y][x] = this.getTag();
      }
    }
  }
  
  public void loadItsLevel(GameManager gm)
  {
    clip.play();
    switch(levelToLoad)
    {
      case "1":
        gm.loadLevel1();
      break;
      case "2":
        gm.loadLevel2();
      break;
      case "3":
        gm.loadLevel3();
      break;
      case "3Combat1":
        gm.loadLevel3Combat1();
      break;
      case "4":
        gm.loadLevel4();
      break;
      
      
    }
  }

  
  
  
  
}
