import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ASCII_Lib {
 
    private static final String[] Color_list = {"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[37m"};

    private static final String Color_reset = "\u001B[0m";

    public static void main(String[] args) {
        String input = "Oussama Tanfouri";
        Make_art(input);
        System.out.println("\n");
        String input2 = "Jihad Khouly";
        Make_art(input2);
    }

    public static void Make_art(String text) {
        try {
            BufferedImage image = new BufferedImage(500, 100, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setFont(new Font("Monospaced", Font.BOLD, 18));
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            g.setColor(Color.WHITE);
            g.drawString(text, 10, 50); 

           
            BufferedImage final_image = cropImage(image);

            for (int y = 0; y < final_image.getHeight(); y++) {
                for (int x = 0; x < final_image.getWidth(); x++) {
                    int color = final_image.getRGB(x, y);
                    if (color == Color.BLACK.getRGB()) {
                        System.out.print(" "); 
                    } else {
                        String ansiColor = Color_list[(x + y) % Color_list.length];
                        System.out.print(ansiColor + "0" + Color_reset);
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage cropImage(BufferedImage image) {
        int min_X = image.getWidth(), min_Y = image.getHeight();
        int max_X = 0, max_Y = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getRGB(x, y) != Color.BLACK.getRGB()) {
                    min_X = Math.min(min_X, x);
                    min_Y = Math.min(min_Y, y);
                    max_X = Math.max(max_X, x);
                    max_Y = Math.max(max_Y, y);
                }
            }
        }

        // Create the cropped image
        return image.getSubimage(min_X, min_Y, max_X - min_X + 1, max_Y - min_Y + 1);
    }
}
