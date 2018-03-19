package plugins;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorInvertRed {

    public static void invertRedImage(String imageName) {
        BufferedImage inputFile = null;
        try {
            inputFile = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int x = 0; x < inputFile.getWidth(); x++) {
            for (int y = 0; y < inputFile.getHeight(); y++) {
                int rgba = inputFile.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color( col.getRed(),
                        col.getGreen()/3,
                        col.getBlue()/3);
                inputFile.setRGB(x, y, col.getRGB());
            }
        }

        try {
            File outputFile = new File(imageName);
            ImageIO.write(inputFile, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ColorInversion.invertImage(imageName);
    }

}
