package game;

import GameEngine.GameContainer;
import GameEngine.Renderer;
import audio.SoundClip;
import gfx.Image;

public class Item extends GameObject
{
  protected Image ItemImage;
  protected boolean inInventory = false;
  protected int tileY, tileX;
  
  private SoundClip clip;
  
  public Item(int tileX, int tileY, String tag, String path, String soundPath, float vol)
  {
    this.tileY = tileY;
    this.tileX = tileX;
    this.tag = tag;
    ItemImage = new Image(path);
    clip= new SoundClip(soundPath);
    clip.setVolume(vol);
  }
  
  
  public void update(GameContainer gc, GameManager gm, float dt)
  {
   
  }

  public void render(GameContainer gc, Renderer r)
  {
    r.drawImage(ItemImage, this.getPosX(), this.getPosY());
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
  
  public int getPosY()
  {
    return ((200*(tileY-1) / 25)+28);
  }
  public int getPosX()
  {
    return ((219*(tileX-1) / 27)+6);
  }
  
  
  
  public Image getImage()
  {
    return ItemImage;
  }
  
  public void placeInMap(String[][] curlev)
  {
    curlev[this.getYTile()][this.getXTile()] = this.getTag();
  }
  
  public void shwoopToInventory(GameManager gm)
  {
    clip.play();
    gm.getItems().remove(this);
    gm.getCurrentLevelArray()[this.getYTile()][this.getXTile()] = " ";
//    System.out.println("shwoopd to inventory");
    gm.getHUD().getInventory().add(this);
  }
  
  
}
