package game;

import GameEngine.GameContainer;
import GameEngine.Renderer;

public abstract class GameObject
{
  protected String tag;
  protected int posX, posY;;
  
  public abstract void update(GameContainer gc, GameManager gm,  float dt);
  public abstract void render(GameContainer gc, Renderer r);
  
  
  public String getTag()
  {
    return tag;
  }
  public void setTag(String tag)
  {
    this.tag = tag;
  }
  public int getPosX()
  {
    return posX;
  }
  public void setPosX(int posX)
  {
    this.posX = posX;
  }
  public int getPosY()
  {
    return posY;
  }
  public void setPosY(int posY)
  {
    this.posY = posY;
  } 
}
