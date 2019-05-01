package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import GameEngine.GameContainer;
import GameEngine.Renderer;
import gfx.Image;

public class Player extends GameObject
{
  
  private static int posXStatic, posYStatic;
  
  boolean collision[];
  
private Image PlayerImage; //= new Image("/Sprites/BearSprite.png");
  private float speedX = 8;
  private float speedY = 8;
  private boolean pb = false;
  private boolean pb2 = false;
  public int speedDelay = 10;
  public int speedDelay2 = 10;
  boolean anyPressed;
  boolean anyPresssed2;
  boolean movingWithWASD = false;
  
  public Player(int posX, int posY, String path)
  {
    this.tag = "player";
    this.posX = posX;
    this.posY = posY;
    posXStatic = posX;
    posYStatic = posY;
    PlayerImage = new Image(path);
    
  }
  public static  int getXTile()
  {
    return ((posXStatic * 27 / 218)+1);
  }
  public static int getYTile()
  {
    return ((posYStatic * 23 / 180)-2);
  }

  public boolean getBasicCollision(String direc, GameContainer gc, GameManager gm)
  {   
    
    boolean allowed = false;
    
    String up = gm.getCurrentLevelArray()[this.getYTile() - 1][this.getXTile()];
    String down = gm.getCurrentLevelArray()[this.getYTile() + 1][this.getXTile()];
    String left = gm.getCurrentLevelArray()[this.getYTile()][this.getXTile() - 1];
    String right = gm.getCurrentLevelArray()[this.getYTile()][this.getXTile() + 1];
    
    
    
    switch(direc)
    {
    
    case "up":
      if(up.equals(" "))// || checkDoorCollision(gc, gm, up))
      {
//        System.out.println(gm.getCurrentLevelArray()[this.getYTile() - 1][this.getXTile()]) ;
        allowed = true;
      }
    break;
    case "down":
//      System.out.println(gm.getCurrentLevelArray()[this.getYTile() + 1][this.getXTile()]);
      if(down.equals(" ") )//|| checkDoorCollision(gc, gm, down))
      {
        allowed = true;
      }
    break;
    case "left":
      if(left.equals(" "))// || checkDoorCollision(gc, gm, left))
      {
        allowed = true;
      }
    break;
    case "right":
      if(right.equals(" "))// || checkDoorCollision(gc, gm, right))
      {
        allowed = true;
      }
    break;
    default:
    return true;
      
      
    }
    return allowed;
  }

  public void render(GameContainer gc, Renderer r)
  {
      r.drawImage(PlayerImage, posX, posY);
//    r.drawFillRect(posX, posY, 10,10, 0xff00ff00);
  }
  public void update(GameContainer gc, GameManager gm, float dt)
  {
    movingWithWASD = false;
    if((gc.getInput().isKey(KeyEvent.VK_W)) || (gc.getInput().isKey(KeyEvent.VK_S)) ||
        (gc.getInput().isKey(KeyEvent.VK_A)) || (gc.getInput().isKey(KeyEvent.VK_D))){
      movingWithWASD = true;
      if((gc.getInput().isKey(KeyEvent.VK_W)) && speedDelay==0 && (getBasicCollision("up",gc,gm)))
      {
        posY -= speedY;
      }
      else if((gc.getInput().isKey(KeyEvent.VK_S)&& speedDelay==0 && (getBasicCollision("down",gc,gm))))
      {
        posY += speedY; 
      }
      else if((gc.getInput().isKey(KeyEvent.VK_A) && speedDelay==0 && (getBasicCollision("left",gc,gm))))
      {
        posX -= speedX;
      }
      else if((gc.getInput().isKey(KeyEvent.VK_D) && speedDelay==0 && (getBasicCollision("right",gc,gm))))
      {
        posX += speedX;
      }
    }
    if(gc.getInput().isKeyUp(KeyEvent.VK_W )|| gc.getInput().isKeyUp(KeyEvent.VK_S) ||
        gc.getInput().isKeyUp(KeyEvent.VK_A) || gc.getInput().isKeyUp(KeyEvent.VK_D))

    {
      pb = false;
    }    
    if(!movingWithWASD)
    updateWarrowrs(gc, gm, dt);
    
    posXStatic = posX;
    posYStatic = posY;
    checkSign(gc, gm);
    checkBigSign(gc,gm);
    checkItem(gc, gm);
    checkDoor(gc,gm);
    checkPortal(gc,gm);
    speedDelay++;
    if(speedDelay >=10){
      speedDelay = 0;
    }
    
    boolean anythingPressed = false;
    for(int i = 0; i< gc.getInput().getKeys().length; i++)
    {
      if(gc.getInput().getKeys()[i])
      {
        anythingPressed = true;
      }
    }
    if(!anythingPressed){
      speedDelay = 0;
    }
  }
  public void updateWarrowrs(GameContainer gc, GameManager gm, float dt)
  {
   
      if((gc.getInput().isKey(KeyEvent.VK_UP)) && speedDelay2==0 && (getBasicCollision("up",gc,gm)))
      {
        posY -= speedY;
      }
      else if((gc.getInput().isKey(KeyEvent.VK_DOWN)&& speedDelay2==0 && (getBasicCollision("down",gc,gm))))
      {
        posY += speedY; 
      }
      else if((gc.getInput().isKey(KeyEvent.VK_LEFT) && speedDelay2==0 && (getBasicCollision("left",gc,gm))))
      {
        posX -= speedX;
      }
      else if((gc.getInput().isKey(KeyEvent.VK_RIGHT) && speedDelay2==0 && (getBasicCollision("right",gc,gm))))
      {
        posX += speedX;
      }
    if(gc.getInput().isKeyUp(KeyEvent.VK_UP)|| gc.getInput().isKeyUp(KeyEvent.VK_DOWN)|| 
       gc.getInput().isKeyUp(KeyEvent.VK_LEFT)|| gc.getInput().isKeyUp(KeyEvent.VK_RIGHT))
    {
      pb2 = false;
    }    
    posXStatic = posX;
    posYStatic = posY;
   
    speedDelay2++;
    if(speedDelay2 >=10){
      speedDelay2 = 0;
    }
    
    boolean anythingPressed2 = false;
    for(int i = 0; i< gc.getInput().getKeys().length; i++)
    {
      if(gc.getInput().getKeys()[i])
      {
        anythingPressed2 = true;
      }
    }
    if(!anythingPressed2){
      speedDelay2 = 0;
    }
  }
  
  
  
  
  
  
  
  
  public void readSign(Sign sn)
  {
    sn.setReadBoolean(true);
  }
  public void readDoor(Door dor)
  {
    dor.setLockedToReadBoolean(true);
  }
  
  
  
  public void checkSign(GameContainer gc, GameManager gm)
  {
    for(Sign sns : gm.getSigns())
    {
      if(sns.getXTile()-1 == this.getXTile() && sns.getYTile() == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) ||
         sns.getXTile()+1 == this.getXTile() && sns.getYTile() == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) || 
         sns.getXTile() == this.getXTile() && sns.getYTile()+1 == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) ||
         sns.getXTile() == this.getXTile() && sns.getYTile()-1 == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER))
      {
        this.readSign(sns);
      }
    }
  }
  
  public void checkBigSign(GameContainer gc, GameManager gm)
  {
    int snx;
    int sny;
   
    for(BigSign bsn : gm.getBigSigns())
    {
      for(sny = bsn.getYTile(); sny <= bsn.getYTile()+bsn.getDimY(); sny++)
      {
        for(snx = bsn.getXTile(); snx <= bsn.getXTile()+bsn.getDimX(); snx++)
        {
//          System.out.println(snx + ", " + sny);
          if(snx-1 == this.getXTile() && sny == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) ||
              snx+1 == this.getXTile() && sny == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) || 
              snx == this.getXTile() && sny+1 == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) ||
              snx == this.getXTile() && sny-1 == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER))
          {
            this.readSign(bsn);
          }
        }
      }
    }
  }
  
  
  public void checkDoor(GameContainer gc, GameManager gm)
  {
    ArrayList<Door> doors = gm.getDoors();
    Door toKill = new Door(1, 1, "1", "1","1", "/Sprites/LockSprite.png", 0xffffff, null);
    for(Door dor : doors)
    {
      if(dor.getXTile()-1 == this.getXTile() && dor.getYTile() == this.getYTile() ||
          dor.getXTile()+1 == this.getXTile() && dor.getYTile() == this.getYTile() || 
          dor.getXTile() == this.getXTile() && dor.getYTile()+1 == this.getYTile() ||
          dor.getXTile() == this.getXTile() && dor.getYTile()-1 == this.getYTile())
       {
         
//         System.out.println("nearDoor");
         if(gc.getInput().isKey(KeyEvent.VK_ENTER))
         {
           this.readDoor(dor);
           dor.playClip();
           toKill = dor;
         }
       }
    }
    if(toKill.ableToGoThru(gm))
    {
      
      gm.getCurrentLevelArray()[toKill.getYTile()][toKill.getXTile()] = " ";
      gm.getDoors().remove(toKill);
      
    }
    
    
  }
  
  
  
//  public boolean checkDoorCollision(GameContainer gc, GameManager gm, String imminent)
//  {
//    Door toKill = null;
//    boolean able = false;
//    
//    ArrayList<Door> doors = gm.getDoors();
////    System.out.print(imminent); 
//    
//    
//    for(Door dor : doors)
//    {
//
//      if(dor.getTag().equals(imminent) && dor.ableToGoThru(gm))
//      {
//        able = true;
//        toKill = dor;
//        }
//      else if(dor.getTag().equals(imminent) && !dor.ableToGoThru(gm))
//      {
////        this.readDoor(dor);
//      }
//    }
//    if(able)
//    {
//      gm.getDoors().remove(toKill);
//    }
// 
//    
//    return able;
//
//  }
  
  public void checkPortal(GameContainer gc, GameManager gm)
  {
    int ptlx;
    int ptly;
    boolean willLoad = false;
    Portal loadingPortal = null;
   
    for(Portal ptl : gm.getPortals())
    {
      for(ptly = ptl.getYTile(); ptly <= ptl.getYTile()+ptl.getDimY(); ptly++)
      {
        for(ptlx = ptl.getXTile(); ptlx <= ptl.getXTile()+ptl.getDimX(); ptlx++)
        {
//          System.out.println(ptlx + ", " + ptly);
//          System.out.println(ptl.AbleToLoad());
          if(ptlx-1 == this.getXTile() && ptly == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) ||
              ptlx+1 == this.getXTile() && ptly == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) || 
              ptlx == this.getXTile() && ptly+1 == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER) ||
              ptlx == this.getXTile() && ptly-1 == this.getYTile() && gc.getInput().isKey(KeyEvent.VK_ENTER))
          {
            if(ptl.AbleToLoad())
            {
//              System.out.println();
              loadingPortal = ptl;
              willLoad = true;
            }else
            {
              ptl.SayMessage(gm);
            }
            
            
            
          }
        }
      }
    }
    if(willLoad)
    {
     loadingPortal.loadItsLevel(gm);
    }
    
    
  }
  
  public void SayMessage()
  {
    
  }
  
  public void checkItem(GameContainer gc, GameManager gm)
  {
    int index = -1;
    
    for(int i = 0; i < gm.getItems().size(); i++)
    {
      if(gc.getInput().isKey(KeyEvent.VK_ENTER))
      {
//        System.out.println("enter");
        Item itm = gm.getItems().get(i);
        if(itm.getXTile()-1 == this.getXTile() && itm.getYTile() == this.getYTile() ||
           itm.getXTile()+1 == this.getXTile() && itm.getYTile() == this.getYTile() || 
           itm.getXTile() == this.getXTile() && itm.getYTile()+1 == this.getYTile() ||
           itm.getXTile() == this.getXTile() && itm.getYTile()-1 == this.getYTile() )
        {
          index = i;
          
        }
      }
    }
    if(index >=0)
    {
    gm.getItems().get(index).shwoopToInventory(gm);
    }
  }
  public void setPlayerImage(Image playerImage)
  {
    PlayerImage = playerImage;
  }
  


}
