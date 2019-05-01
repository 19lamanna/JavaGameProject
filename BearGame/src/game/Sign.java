package game;

import GameEngine.GameContainer;
import GameEngine.Renderer;
import audio.SoundClip;
import gfx.Image;

public class Sign extends GameObject
{
  protected boolean readBoolean;
  protected int color = 0xfff00ff;
  
  protected int tileX, tileY;
//  private static int posXStatic;
//  private static int posYStatic;
  
  private Image signImage;
  private String Message;
  
  protected SoundClip clip;

  
  public Sign(int tileX, int tileY, String tag, String message, String path, String soundPath, int color, float vol)
  {    
   this.tag = tag;
   Message = message;
   this.tileX = tileX;
   this.tileY = tileY;
   
//   this.posX = posx;
//   this.posY = posy;
//   posXStatic = posx;
//   posYStatic = posy;
   this.color = color;
 
   signImage = new Image(path);
   
   clip= new SoundClip(soundPath);
   clip.setVolume(vol);
  }
  public Sign(int tileX, int tileY, Sign sn)
  {
    tag = sn.getTag();
    Message = sn.getMessage();
    this.tileX = tileX;
    this.tileY = tileY;
    signImage = sn.getSignImage();
    color = sn.getColor();
    clip = sn.getClip();
    clip.setVolume(sn.getClip().getVolume());
  }
  
  
  public void placeInMap(String[][] curlev)
  {
    curlev[this.getYTile()][this.getXTile()] = this.getTag();
  }
  
  public int getXTile()
  {
//    System.out.println((posXStatic * 30 / 218));

//    return ((posX * 30 / 218)-1);
    return tileX;
  }
 
  
  public int getYTile()
  {
//    return ((posY * 23 / 180)-2);
    return tileY;
  }
  
  public int getPosY()
  {
    return ((200*(tileY-1) / 25)+28);
  }
  public int getPosX()
  {
    return ((8*(tileX)));
  }
  
  
  private String getMessage()
  {
    return Message;
  }

  public void update(GameContainer gc, GameManager gm, float dt)
  {
    if(readBoolean)
    {
      sayMessage(gm);
    }
    readBoolean = false;
  }

  public void render(GameContainer gc, Renderer r)
  {
    r.drawImage(signImage, this.getPosX(), this.getPosY());
//    r.drawFillRect(posX, posY, 10,10, 0xff00ff00);
    
  }
  
  public void sayMessage(GameManager gm)
  {
    if(!clip.isRunning())
    {
      clip.play();
    }
    gm.getHUD().setMessage(Message, color);
//    r.drawText("Press X", tileX - 16, tileX - 16,  0xff00ff00);
  }

  public boolean isReadBoolean()
  {
    return readBoolean;
  }

  public void setReadBoolean(boolean readBoolean)
  {
    this.readBoolean = readBoolean;
  }
  
  public void remove(GameManager gm)
  {
    gm.getCurrentLevelArray()[this.getYTile()][this.getXTile()] = " ";
    gm.getSigns().remove(this);
  }
  public int getColor()
  {
    return color;
  }
  public Image getSignImage()
  {
    return signImage;
  }
  public SoundClip getClip()
  {
    return clip;
  }



  
  
  

}
