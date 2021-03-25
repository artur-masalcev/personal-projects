import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Artur Masalcev
 *
 * This program generates and paints a Sierpiński triangle fractal using the mathematical link between it and Pascal
 * triangle: https://books.google.com/books?id=theofRmeg0oC&pg=PT145
 * The created fractal is saved as an image file
 */

public class Main {
    String dir = "C:\\Fractals"; //Modify this variable to change the output directory

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> pascal = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row, row1;
        row = new ArrayList<Integer>();

        //Making first row as 1 by default
        row.add(1);
        pascal.add(new ArrayList<Integer>(row));

        //As row is already [1] adding only one 1 to make it [1,1]
        row.add(1);
        pascal.add(new ArrayList<Integer>(row));

        int n = new Scanner(System.in).nextInt(); //This variable decides the resolution of the fractal, the bigger is n the higher is resolution

        for(int i = 0; i < n; i++) {
            row1 = new ArrayList<Integer>();
            for (int j = 0; j < row.size() + 1; j++) {
                if (j == 0) row1.add(1);
                else if (j == row.size()) row1.add(1);
                else {
                    row1.add(row.get(j) + row.get(j - 1));
                }
            }
            pascal.add(row1);
            row = new ArrayList<>(row1);
        }

        int imageSize = pascal.get(pascal.size()-1).size() * 2;
        BufferedImage image = new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_RGB);

        System.out.println(imageSize);

        int elementsPerRow = imageSize;
        int iterator = 0;
        int iterator2 = pascal.size() - 1;
        for(int i = imageSize - 1; i >= 0; i--){
            for(int j = imageSize - i; j < elementsPerRow; j+= 2){
                int color;
                if(pascal.get(iterator2).get(iterator) % 2 != 0) {
                    int r = (255 * iterator2/pascal.size()-1);
                    if(r < 0)r = 0;
                    color = (0 << 24) | (r << 16) | (0 << 8) | (255);
                    image.setRGB(j, i, color);
                }
                iterator++;
            }
            elementsPerRow--;
            iterator = 0;
            iterator2--;
        }

        File f;
        try{
            f = new File(dir);
            ImageIO.write(image,"png",f);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
