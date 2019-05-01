package GameEngine;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.plaf.TextUI;

import gfx.Font;
import gfx.Image;
import gfx.ImageTile;

public class Renderer
{
  private int pW, pH;
  private int[] pixels;
  
  private Font font = Font.STANDARD;
  
  public Renderer(GameContainer gc)
  {
    pW = gc.getWidth();
    pH = gc.getHeight();
    pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    
  }
  
  public void clear()
  {
    for(int i = 0; i < pixels.length; i++)
    {
      pixels[i] = 0;
    }
  }
 
  
  
  
  public void setPixel(int x, int y, int value)
  {
    if ((x <0 || x >= pW || y < 0 || y > pH) || value == 0xffff00ff)
    {
      return;
    }
    
    pixels[x + y * pW] = value;
    
  }
  
  public void drawText(String text, int offX, int offY, int color)
  {
    Image fontImage = font.getFontImage();
    text = text.toUpperCase();
    int offset = 0;
    for(int i = 0; i < text.length(); i++)
    {
      
//      System.out.println("|".codePointAt(0));
      int unicode = text.codePointAt(i) - 32;
      if(unicode >= 91)
      {
        unicode = unicode - 25; 
      }
      
      for(int y = 0; y < fontImage.getH(); y++)
      {
        for(int x = 0; x < font.getWidths()[unicode]; x++)
        {
          if(fontImage.getPixels()[(x + font.getOffsets()[unicode]) + y * fontImage.getW()] == 0xffffffff)
          {
            setPixel(x + offX + offset, y + offY, color);
          }
        }
      }
      offset+= font.getWidths()[unicode];
    }
  }
  
  public void drawTextUniformSpace(String text, int offX, int offY, int color)
  {
    int width = 8;
    Image fontImage = font.getFontImage();
    text = text.toUpperCase();
    int offset = 0;
    for(int i = 0; i < text.length(); i++)
    {
      int unicode = text.codePointAt(i) - 32;
      if(unicode >= 91)
      {
        unicode = unicode - 25; 
      }
      
      
      for(int y = 0; y < fontImage.getH(); y++)
      {
        for(int x = 0; x < font.getWidths()[unicode]; x++)
        {
          if(fontImage.getPixels()[(x + font.getOffsets()[unicode]) + y * fontImage.getW()] == 0xffffffff)
          {
            setPixel(x + offX + offset, y + offY, color);
          }
        }
      }
      offset+= width;
    }
  }
  
  
  public void drawTextWrapping(String text, int offX, int offY, int color)
  {
    Image fontImage = font.getFontImage();
    text = text.toUpperCase();
    int offset = 0;
    for(int i = 0; i < text.length(); i++)
    {
      int unicode = text.codePointAt(i) - 32;
      if(unicode >= 91)
      {
        unicode = unicode - 25; 
      }
      
      for(int y = 0; y < fontImage.getH(); y++)
      {
        for(int x = 0; x < font.getWidths()[unicode]; x++)
        {
          if(x + offX + offset > pW-font.getWidths()[unicode])
          {
            offY += 7;
//            offX = -60;
            offX = 0;
            offset=0;
            
          }
          if(fontImage.getPixels()[(x + font.getOffsets()[unicode]) + y * fontImage.getW()] == 0xffffffff)
          {
            setPixel(x + offX + offset, y + offY, color);
          }
        }
      }
      offset+= font.getWidths()[unicode];
    }
  }
  
  public void drawTextArray(String[][] textArr, int color)
  {
    
    String ArrayAsString;
    
//    int spacesHorz = 8;
    int spacesVert = 8;
    
    for(int y = 0; y < textArr.length; y++)
    {
      ArrayAsString = Arrays.toString(textArr[y]);
//      ArrayAsString = String.join("", textArr);      
//      System.out.println(ArrayAsString);
      ArrayAsString = ArrayAsString.replace("[, ", "repWb1");
      ArrayAsString = ArrayAsString.replace("], ", "repWb2");

      
      ArrayAsString = ArrayAsString.replace(", ", "");
      ArrayAsString = ArrayAsString.replace("]", "");
      ArrayAsString = ArrayAsString.replace("[", "");
      
      ArrayAsString = ArrayAsString.replace("repWb1", "[");
      ArrayAsString = ArrayAsString.replace("repWb2", "]");
      
//      System.out.println(ArrayAsString);
      
      drawTextUniformSpace(ArrayAsString, 1, 20 +(spacesVert * y), color);
      
    }
//    System.out.println("");
  }
  
  public void drawImage(Image image, int offX, int offY)
  {
    int newX = 0;
    int newY = 0;
    int newWidth = image.getW();
    int newHeight = image.getH();
    
    //don't render code
    if(offX < -image.getW()) {return;}
    if(offY < -image.getH()) {return;}
    if(offX >= pW) {return;}
    if(offY >= pH) {return;}
    
    // clipping code
    if(newX + offX < 0){newX -= offX;}
    if(newY + offY < 0){newY -= offY;}
    if(newWidth + offX >= pW){newWidth -=(newWidth + offX - pW);}
    if(newHeight + offY >= pH){newHeight -=(newHeight + offY - pH);}
    
    
    for(int y = 0; y < newHeight; y++)
    {
      for(int x = 0; x < newWidth; x++)
      {
        setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getW()]);
      }
    }
  }
  public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
  {
    int newX = 0;
    int newY = 0;
    int newWidth = image.getTileW();
    int newHeight = image.getTileH();
    
    //don't render code
    if(offX < -image.getTileW()) {return;}
    if(offY < -image.getTileH()) {return;}
    if(offX >= pW) {return;}
    if(offY >= pH) {return;}
    
    // clipping code
    if(newX + offX < 0){newX -= offX;}
    if(newY + offY < 0){newY -= offY;}
    if(newWidth + offX >= pW){newWidth -=(newWidth + offX - pW);}
    if(newHeight + offY >= pH){newHeight -=(newHeight + offY - pH);}
   
    for(int y = 0; y< newHeight; y++)
    {
      for(int x = 0; x< newWidth; x++)
      {
        setPixel(x + offX, y + offY, image.getPixels()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH())]);
      }
    }
    
  }
  public void drawRect(int offX, int offY, int width, int height, int color)
  {
    
    
    for(int y = 0; y <=height; y++)
    {
       setPixel(offX, y + offY, color);
       setPixel(offX + width, y + offY, color);
    }
    for(int x = 0; x <=width; x++)
    {
      setPixel(x + offX, offY, color);
      setPixel(x + offX, offY + height, color);
    }
  }
  public void drawFillRectSimple(int offX, int offY, int width, int height, int color)
  {
    for(int y = 0; y <=height; y++)
    {
      for(int x = 0; x <=width; x++)
      {
        setPixel(x + offX, y + offY, color);
      } 
    }
  }
  
  
  public void drawFillRect(int offX, int offY, int width, int height, int color)
  {
    int newX = 0;
    int newY = 0;
    int newWidth = width;
    int newHeight = width;
    
    //don't render code
    if(offX < -width) {return;}
    if(offY < -height) {return;}
    if(offX >= pW) {return;}
    if(offY >= pH) {return;}
        
    
    // clipping code
    if(newX + offX < 0){newX -= offX;}
    if(newY + offY < 0){newY -= offY;}
    if(newWidth + offX >= pW){newWidth -=(newWidth + offX - pW);}
    if(newHeight + offY >= pH){newHeight -=(newHeight + offY - pH);}
   
    for(int y = newY; y <=newHeight; y++)
    {
      for(int x = newX; x <=newWidth; x++)
      {
        setPixel(x + offX, y + offY, color);
      } 
    }
  }
}

