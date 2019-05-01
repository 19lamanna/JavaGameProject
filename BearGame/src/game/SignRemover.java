package game;

import GameEngine.GameContainer;

public class SignRemover extends Sign
{
  protected Sign toKill;
  
  public SignRemover(int tileX, int tileY, String tag, String message,
      String path, String soundPath, int color, float vol, Sign toKill)
  {
    super(tileX, tileY, tag, message, path, soundPath, color, vol);
    this.toKill = toKill;
  }
  
  public void update(GameContainer gc, GameManager gm, float dt)
  {
    if(readBoolean)
    {
      sayMessage(gm);
      removeSign(gm);
    }
    readBoolean = false;
  }

  private void removeSign(GameManager gm)
  {
    toKill.remove(gm);
  }

}
