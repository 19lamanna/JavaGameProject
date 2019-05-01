package game;

import GameEngine.GameContainer;
import GameEngine.Renderer;
import audio.SoundClip;

public class CostumeSetter extends Sign
{
  private SoundClip clip;

  
  
  String costumePath;
  
 public CostumeSetter(int tileX, int tileY, String tag, String message,
      String path, int color, String costumePath)
  {
    super(tileX, tileY, tag, message, path, "/audio/pew.wav", color, -10);
    this.costumePath = costumePath;
//    clip= new SoundClip("/audio/pew.wav");
//    clip.setVolume(-20);
  }

 
  public void setCostume(GameManager gm)
  {
    gm.setPlayerCostume(costumePath);
  }
  
  @Override
  public void sayMessage(GameManager gm)
  {
    // TODO Auto-generated method stub
   
    
    super.sayMessage(gm);
    setCostume(gm);
  }
}
