import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments 
     */
    public Picture ()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();
    }

    /**
     * Constructor that takes a file name and creates the picture 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a 
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() + 
            " height " + getHeight() 
            + " width " + getWidth();
        return output;

    }
    
    /** Method to crop a picture and copy it*/
    void cropAndCopy( Picture sourcePicture, int startSourceRow, int endSourceRow,
        int startSourceCol, int endSourceCol,int startDestRow, int startDestCol )
    {
        Pixel[][] originalPic = sourcePicture.getPixels2D();
        Pixel[][] cropPic = new Pixel[(endSourceRow - startSourceRow) + 1]
            [(endSourceCol - startSourceCol) + 1];
        Pixel[][] otherPic = this.getPixels2D();
        Pixel originalPix = null;
        Pixel cropPix = null;
        Pixel otherPix = null;
        int cropCol = 0;
        int cropRow = 0;
        
        for(int row = startSourceRow; row <= endSourceRow; row++)
        {
            for(int col = startSourceCol; col <= endSourceCol; col++)
            {
                originalPix = originalPic[row][col];
                cropPic[cropRow][cropCol] = originalPix;
                cropCol++;
            }
            cropRow++;
            cropCol = 0;
        }
        
        cropCol = 0;
        cropRow = 0;
        
        for(int row = startDestRow; row < (cropPic.length + startDestRow);row++)
        {
            for( int col = startDestCol; col < (cropPic[0].length + startDestCol);col++)
            {
                cropPix = cropPic[cropRow][cropCol];
                otherPix = otherPic[row][col];
                otherPix.setColor(cropPix.getColor());
                cropCol++;
            }
            cropRow++;
            cropCol = 0;
        }
    }
    
    /** Method to set the blue to 0 */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
    }
    
    /** Method to set green and red to 0 */
    public void keepOnlyBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setRed(0);
                pixelObj.setGreen(0);
            }
        }
    }
    
    /** negates all the pixels in a picture */
    public void negate()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                int blue = pixelObj.getBlue();
                blue = 255 - blue;
                int red = pixelObj.getRed();
                red = 255 - red;
                int green = pixelObj.getGreen();
                green = 255 - green;
                
                pixelObj.setBlue(blue);
                pixelObj.setRed(red);
                pixelObj.setGreen(green);
            }
        }
    }
    
    /** sets all pixels to a grayscale value */
    public void grayscale()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                int blue = pixelObj.getBlue();
                int red = pixelObj.getRed();
                int green = pixelObj.getGreen();
                int average = (blue + red + green)/3;
                
                blue = average;
                red = average;
                green = average;
                
                pixelObj.setBlue(blue);
                pixelObj.setRed(red);
                pixelObj.setGreen(green);
            }
        }
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
    }

    public void mirrorVerticalRightToLeft()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            }
        } 
    }
    
    public void mirrorHorizontal()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel upPixel = null;
        Pixel downPixel = null;
        int height = pixels.length;
        for (int row = 0; row < height / 2; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                upPixel = pixels[row][col];
                downPixel = pixels[height - 1 - row][col];
                downPixel.setColor(upPixel.getColor());
            }
        } 
    }
    
    public void mirrorHorizontalBotToTop()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel upPixel = null;
        Pixel downPixel = null;
        int height = pixels.length;
        for (int row = 0; row < height / 2; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                upPixel = pixels[row][col];
                downPixel = pixels[height - 1 - row][col];
                upPixel.setColor(downPixel.getColor());
            }
        } 
    }

    /** Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();
        
        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {
                count++;
                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
        System.out.println(count);
    }
    
    public void mirrorArms()
    {
        int mirrorPoint = 206;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();
        
        for (int row = 159; row < 179; row++)
        {
            for (int col = 100; col < mirrorPoint; col++)
            {
                count++;
                leftPixel = pixels[row][col];
                rightPixel = pixels[row]
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
        
        for (int row = 159; row < 196; row++)
        {
            for (int col = 310; col > mirrorPoint; col--)
            {
                count++;
                rightPixel = pixels[row][col];
                leftPixel = pixels[row]
                [mirrorPoint - col + mirrorPoint];
                leftPixel.setColor(rightPixel.getColor());
            }
        }
        System.out.println(count);
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, 
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < toPixels.length; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
    }

    /** Method to create a collage of several pictures */
    public void collage()
    {
        Picture blueGuitar1 = new Picture("blueGuitar.jpg");
        Picture blueGuitar2 = new Picture("blueGuitar.jpg");
        Picture blueGuitar3 = new Picture("blueGuitar.jpg");
        Picture blueGuitar4 = new Picture("blueGuitar.jpg");

        this.write("collage.jpg");
    }

    /** Method to show large changes in color 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; 
            col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col+1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > 
                edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) 
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

} // this } is the end of class Picture, put all new methods before this
