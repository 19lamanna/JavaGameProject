package game;

public class BigSign extends Sign
{
  int dimY, dimX;
  public BigSign(int tileX, int tileY, int dimX, int dimY, String tag, String message, String path,
      String soundPath, int color, float vol)
  {
    super(tileX, tileY, tag, message, path, soundPath, color, vol);
    this.dimX = dimX;
    this.dimY = dimY;
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
  
  public void remove(GameManager gm)
  {
    for(int x = this.getXTile(); x <= this.getXTile() + this.getDimX(); x++)
    {
      for(int y = this.getYTile(); y <= this.getYTile() + this.getDimY(); y++)
      {
        gm.getCurrentLevelArray()[y][x] = " ";
        gm.getBigSigns().remove(this);
      }
    }
  }
  

  public int getDimY()
  {
    return dimY;
  }

  public int getDimX()
  {
    return dimX;
  }
}
