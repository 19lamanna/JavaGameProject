package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import GameEngine.AbstractGame;
import GameEngine.GameContainer;
import GameEngine.Renderer;
import audio.SoundClip;
//import gfx.Image;
import gfx.Image;

public class GameManager extends AbstractGame
{ 
//  private int levelH;
//  private int levelW;
  private String[][] currentLevel;
  private String[][] currentLevelToPrint;
  private int levelColor;
  private String playerCostume;
  private SoundClip clip;
  
  private boolean beenInLevel1;
  private boolean beenInLevel2;
  private boolean foughtRat;
  
  
  private ArrayList<Sign> signs = new ArrayList<Sign>();
  private ArrayList<BigSign> bigSigns = new ArrayList<BigSign>();
  private ArrayList<Item> items = new ArrayList<Item>();
  private ArrayList<Door> doors = new ArrayList<Door>();
  private ArrayList<Portal> portals = new ArrayList<Portal>();
  private ArrayList<Player> players = new ArrayList<Player>();
  
  private HUD HUD = new HUD();
   
  //sets starting state of game
  public GameManager()
  {
    
    clip = new SoundClip("/audio/ta-da.wav");
    clip.setVolume(-20);
    clip.play();
    playerCostume = "/Sprites/DefaultSprite.png";
    loadLevel3();
  }
  
  public void init(GameContainer gc)
  {
    // TODO Auto-generated method stub
    
  }
  
  //called continously, updates all objects
  public void update(GameContainer gc, float dt)
  {
    for(int i = 0; i < signs.size(); i++)
    {
      signs.get(i).update(gc, this, dt);
    }
    for(int i = 0; i < doors.size(); i++)
    {
      doors.get(i).update(gc, this, dt);
    }   
    for(int i = 0; i < portals.size(); i++)
    {
      portals.get(i).update(gc, this, dt);
    }  
    for(int i = 0; i < bigSigns.size(); i++)
    {
      bigSigns.get(i).update(gc, this, dt);
    } 
    
    
    for(int i = 0; i < players.size(); i++)
    {
      players.get(i).update(gc, this, dt);
    }
     
    
   HUD.update(gc,this);
   
   
   
  }

  
  //called continously, renders all objects
  public void render(GameContainer gc, Renderer r)
  {
   
    
    for(GameObject sns : signs)
    {
      sns.render(gc, r);
    }
       
    for(Item itm : items)
    {
      itm.render(gc, r);
    }
    for(Door dor : doors)
    {
      dor.render(gc, r);
    }
    
    for(Portal ptl : portals)
    {
      ptl.render(gc, r);
    }
    for(BigSign bsn : bigSigns)
    {
      bsn.render(gc,r);
    }
    
    for(Player play : players)
    {
      play.render(gc, r);
    }
    
    r.drawTextArray(currentLevelToPrint,levelColor);
    r.drawText("X: " + gc.getInput().getMouseX() + ",Y: " + gc.getInput().getMouseY(), 30, 00, 0xfffffff);
    r.drawText("xTile: " + Player.getXTile() + ", yTile: " + Player.getYTile(), 85, 00, 0xfffffff);
    
    HUD.render(gc, r);
    
      
//      for(GameObject obj: objects){
//        for(Player play: players){
//          if(Math.abs(play.getXAsTile(gc)-obj.getPosX()) == 1 || Math.abs(play.getYAsTile(gc)-obj.getPosY()) == 1){
//            obj.sayMessage(this, r);
//          }
//        }
//      }
      
      
  }
    //starts game
  public static void main(String args[])
  {
    GameContainer gc = new GameContainer(new GameManager());
    gc.setWidth(240);//218
    gc.setHeight(180);//180
    gc.setScale(4f);
    gc.start();    
  }
  
  //allows objects to acces level
public String[][] getCurrentLevelArray()
{
  return currentLevel;
}
  //removes all prevous objects, adds new objects, sets level "walls"
public void loadLevel0()
{
  clear();
  
  players.add(new Player(48,124, playerCostume));
  
  CostumeSetter defaultSet = new CostumeSetter(12,7,  "DefaultSet", "set to default","/Sprites/DefaultSprite.png", 0xfff00ff, "/Sprites/DefaultSprite.png");
  CostumeSetter bearSet = new CostumeSetter(15,7,  "BearSet", "set to bear","/Sprites/BearSprite.png", 0xfff00ff, "/Sprites/BearSprite.png");
  CostumeSetter RomanowSet = new CostumeSetter(18,7,  "RomanowSet", "set to Romanow","/Sprites/Romanow.png", 0xfff00ff, "/Sprites/Romanow.png");
  signs.add(defaultSet);
  signs.add(bearSet);
  signs.add(RomanowSet);
  
  Portal StartPortal = new Portal(23,14, 0,2, "Start", "/Sprites/StartSprite.png", null, "1", "");
  portals.add(StartPortal);
  
  levelColor = 0x00ff00;
  String Level0 = 
      "0110111000110010101010110011"
    + "0                         11"
    + "1                         11"
    + "0 select your character?  10"
    + "0                         10"
    + "1                         10"
    + "0                         10"
    + "0                         10"
    + "1                         10"
    + "0                         10"
    + "1                         10"
    + "0                         10"
    + "0                         10"
    + "0                         11"
    + "0                         10"
    + "0011011000101000100110101001";
  
  PlaceStuffInLevel(Level0);
  
}


public void loadLevel1()
{  
  clear();
  
  Item key1 = new Item(23,14, "key1", "/Sprites/KeySprite.png", "/audio/bop.wav", 0);
  items.add(key1);
  
  Portal portal1 = new Portal(3, 13, 1,1, "portal1", "/Sprites/PortalSprite.png", key1, "2", "this portal is from 1988, so you need a key to use it");
  portals.add(portal1);
  
  
  
  Sign sign1 = new Sign(15,7,  "sign1", "Never Trust A Bandit","/Sprites/SignSprite.png", "/audio/dun.wav", 0xfff00ff, -20);
  Sign ganditBuy = new Sign(14, 2, "ganditBuy", "Hey, my name is Gandit Buy, Pleased to meet you, "
                                      +               "the    key you need is in the bottom Right", "/Sprites/Gandit Buy.png", "/audio/yahoo.wav", 0xf0f0f0, 0);
  Door lock1 = new Door(10, 13, "door1", "you need a key to open this","you can go thru this dor", "/Sprites/LockSprite.png", 0xffffff, key1);
  doors.add(lock1);
//  signs.add(lock1);
  
  signs.add(sign1);
  signs.add(ganditBuy);
  
  
  if(beenInLevel1)
  {
    doors.remove(lock1);
    items.remove(key1);
  }
  
  
  
  
  
  players.add(new Player(48,124, playerCostume));

  levelColor = 0x00ff00;
  String Level1 = 
      "0110111000110010101110110011"
    + "001010111001     00010000001"
    + "1101                  000111"
    + "0010 0101011     1011 000110"
    + "0101 0101001001011000 001010"
    + "1011 0001101110101010 010010"
    + "0100             0010 001110"
    + "0011 001100011   1001 010100"
    + "1010 101101100   0111 011010"
    + "001      010000101010 001000"
    + "110      001001011101 110010"
    + "001      010100101011 001100"
    + "011      001110010010 010110"
    + "000                     0001"
    + "010      00100000010    1100"
    + "0011011000101000100110101001";
  

  
  
  PlaceStuffInLevel(Level1);
  
//  System.out.println(currentLevel[13][10]);
  
}

public void loadLevel2()
{
  beenInLevel1=true;
  
  clear();
  
  players.add(new Player(176,124, playerCostume));
  
  Portal portal2 = new Portal(25, 6, 0,0, "portal2", "/Sprites/SewerSprite.png", null, "3", "you shouldnt see this words");
  Portal portal1 = new Portal(24,13, 1, 1, "portal1", "/Sprites/PortalSprite.png", null, "1", "you shouldnt see this words");
  portals.add(portal2);
  portals.add(portal1);
  Sign guard = new Sign(7, 9, "guard", "Yo, Welcome to the City 1, bro, none shall pass!", "/Sprites/GuardSprite.png", "/audio/poopoo.wav", 0xC0C0C0, -10);
  Sign ganditBuy = new Sign(20, 1, "ganditBuy", "Oh no! My cat Cairo lost himself in the sewer! Please, find him!", "/Sprites/Gandit Buy.png", "/audio/yahoo.wav", 0xfff000, -10);
  signs.add(ganditBuy);
  signs.add(guard);
  Door easyGate = new Door(6,8, "DOOR", null, null, "/Sprites/DoorSprite.png", 0xffffff, null);
  doors.add(easyGate);
  
  

  levelColor = 0x00bfff;
  String Level2 = 
      "0110111000110010101__0110011"
    + "0                 [  ]    11"
    + "1                 [  ]    11"
    + "0-----------------[  ]----10"
    + "0                         10"
    + "1                         10"
    + "0---[   ]--------------[  10"
    + "0   [   ]              [--10"
    + "1   [] []                 10"
    + "0   [   ]                 10"
    + "1   [  []                 10"
    + "0   [   ]                 10"
    + "0---[   ]-----------------10"
    + "0                         11"
    + "0                         10"
    + "0011011000101000100110101001";
  
  PlaceStuffInLevel(Level2);
}



public void loadLevel3()
{
  beenInLevel2=true;
  
  clear();
  
  players.add(new Player(8,28, playerCostume));
  
  
   
  Item cairo = new Item(25,14, "cat", "/Sprites/noSprite.png",  "/audio/meow.wav", -10);
  items.add(cairo);
  Portal ladder = new Portal(25, 1, 0,1, "ladder", "/Sprites/noSprite.png", cairo, "4", "you cant leave without that cat, Cairo");
  Portal ratCombat = new Portal(6,1, 1, 1, "rat", "/Sprites/noSprite.png", null, "3Combat1", "you shouldnt see this words");
  portals.add(ladder);
  portals.add(ratCombat);
  
//  portals.add(portal1);
  BigSign bigBlock = new BigSign(12,12, 3,2, "bigBlock","this door is probably controlled by that ominous button" , "/Sprites/noSprite.png",
                                "/audio/nothing.wav", 0xffffff, 0);
  bigSigns.add(bigBlock);
  SignRemover button = new SignRemover(13,10, "button", "you hear a very loud rumbling up above, You "
      + "think that the city must be poorly designed "
      + "if you hear such a sound after pressing such a small button",
      "/Sprites/ButtonSprite.png", "/audio/caveIn.wav", 0xffffff, -20, bigBlock);
  signs.add(button);
  
  if(foughtRat)
  {
    portals.remove(ratCombat);
  }
  
  
  levelColor = 0x808080;
  String Level3 = 
      "----------------------------"
    + "i                         i]"
    + "i-----------i             i]"
    + "i           i  i----------i]"
    + "i           i  i          i]"
    + "i           i  i          i]"
    + "i           i  i          i]"
    + "i-----------i  i----------i]"
    + "i   o                o    i]"
    + "i   o                o    i]"
    + "i   o                o    i]"
    + "i   o                o    i]"
    + "i----------i    i---------i]"
    + "i----------i    i---------i]"
    + "i                         i]"
    + "---------------------------1";
  
  PlaceStuffInLevel(Level3);
}


public void loadLevel3Combat1()
{
  foughtRat = true;
  clear();
  
  players.add(new Player(48,124, playerCostume));
  
  Sign choice1 = new Sign(4,12,"badChoice1", "bad choice, buddy", "/Sprites/noSprite.png", "/audio/dun.wav", 0xff000f, 0);
  Sign choice2 = new Sign(7,12,"badChoice2", "bad choice, buddy", "/Sprites/noSprite.png", "/audio/dun.wav", 0xff000f, 0);
  Sign choice3 = new Sign(10,12,"badChoice3", "bad choice, buddy", "/Sprites/noSprite.png", "/audio/dun.wav", 0xff000f, 0);
  Sign blockage = new Sign(25,11,"blockage", "bad choice, buddy", "/Sprites/noSprite.png", "/audio/dun.wav", 0xff000f, 0);
  signs.add(choice1);
  signs.add(choice2);
  signs.add(choice3);
  signs.add(blockage);
  
  SignRemover choice4 = new SignRemover(13,12,"badChoice1", "good choice, buddy", "/Sprites/noSprite.png", "/audio/bop.wav", 0x00ff0c, 0, blockage);
  signs.add(choice4);
  
  Portal exit = new Portal(24,1, 0,2, "exit", "/Sprites/ExitSprite.png", null, "3", "");
  portals.add(exit);

  
  levelColor = 0x0f0cff;
  String Level3Combat1 = 
      "0110111000110010101010110011"
    + "0                      !  11"
    + "1                      !  11"
    + "0                      !  10"
    + "0                      !  10"
    + "1                      !  10"
    + "0                      !  10"
    + "0                      !  10"
    + "1                      !  10"
    + "0                      !  10"
    + "1                         10"
    + "0!combat-opportunity!!!!! 10"
    + "0                         10"
    + "0                         11"
    + "0                         10"
    + "0011011000101000100110101001";
  
  PlaceStuffInLevel(Level3Combat1);
  
}
public void loadLevel4()
{
  
  clear();
  
  players.add(new Player(32,124, playerCostume));  
  BigSign building = new BigSign(8, 2, 6, 3, "building", 
      "like everything else, this building looks destroyed...", 
      "/Sprites/DestroyedBuildingSprite.png", "/audio/nothing.wav", 
      0xff4500, 0);
  bigSigns.add(building);
  Sign rock = new Sign(9,9,"rock", "the remnant of a once great building", "/Sprites/RockSprite.png", "/audio/nothing.wav", 0xff000f, 0);
  Sign rock2 = new Sign(10,12, rock);
  Sign rock3 = new Sign(17,4, rock);
  Sign rock4 = new Sign(25,5,rock);
  Sign rock5 = new Sign(4,6, rock);
  signs.add(rock);
  signs.add(rock2);
  signs.add(rock3);
  signs.add(rock4);
  signs.add(rock5);
  
  levelColor = 0xA0522D;
  String Level4 = 
      "----------------------------"
    + "i                         i]"
    + "i-----i    ---i i-----i   i]"
    + "i     i       i i---  i   i]"
    + "i     i             i i   i]"
    + "i--                   i   i]"
    + "i                         i]"
    + "i-----i i--i    i--- -i   i]"
    + "i       i             i   i]"
    + "i     i i     - i         i]"
    + "i-----i i-----i i---  i   i]"
    + "i                         i]"
    + "i--- -i i-    i    ---i   i]"
    + "i     i i     i       i   i]"
    + "i     i i       i     i   i]"
    + "---------------------------1";
  
  PlaceStuffInLevel(Level4);
}

//get methods

public HUD getHUD()
{
  return HUD;
}
  
public Player getPlayer()
{
  return players.get(1);
}

public ArrayList<Sign> getSigns()
{
  return signs;
}

public ArrayList<Item> getItems()
{
  return items;
}
public ArrayList<Door> getDoors()
{
  return doors;
}

public void setItems(ArrayList<Item> items)
{
  this.items = items;
}

public ArrayList<Portal> getPortals()
{
  return portals;
}

//places the level string into an array accesible by objects
//places object tags in array, invisible to game though
public void PlaceStuffInLevel(String level)
{

  int width = 27;
  int height = 16;
  
  String[][] StringArray = new String[height][width];
  String[][] StringArray2 = new String[height][width];
  for(int y = 0; y < StringArray.length; y++)
  {
    for(int x = 0; x < StringArray[1].length; x++)
    {
      StringArray[y][x] = "" + level.charAt(x+(width+1)*y);
      StringArray2[y][x] = "" + level.charAt(x+(width+1)*y);
    }
  }
  currentLevel = StringArray;
  currentLevelToPrint = StringArray2;
  for(Sign sns : signs)
  {
    sns.placeInMap(currentLevel);
  }
  for(Item itm : items)
  {
    itm.placeInMap(currentLevel);
  }
  for(Door dor : doors)
  {
    dor.placeInMap(currentLevel);
  }
  for(Portal ptl : portals)
  {
    ptl.placeInMap(currentLevel);
  }
  for(BigSign bsn : bigSigns)
  {
    bsn.placeInMap(currentLevel);
  }
//  System.out.println("1: " +currentLevel[13][3]);
//  System.out.println("2: " + currentLevel[13][4]);
//  System.out.println("3: " + currentLevel[14][3]);
//  System.out.println("4: " + currentLevel[14][4]);
//  
}

public void setPlayerCostume(String playerCostume)
{
  this.playerCostume = playerCostume;
  
  Image img = new Image(playerCostume);
  
  for(Player play : players)
  {
    play.setPlayerImage(img);
  }  
}

public void clear()
{
  players.removeAll(players);
  signs.removeAll(signs);
  bigSigns.removeAll(bigSigns);
  doors.removeAll(doors);
  items.removeAll(items);
  portals.removeAll(portals);
}

public ArrayList<BigSign> getBigSigns()
{
  return bigSigns;
}





 
  
  
}
