import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.Math;
import java.util.Random;
import java.util.Scanner;

public class Project2 {
	
	public static void main(String[] args){
		File catFile = new File("cat.jpg");
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(catFile);
		} catch (IOException e){
			System.out.println();
		}
		int numChars = ((bi.getHeight() * bi.getWidth()) * 4) / 16;
		System.out.println(numChars);
		String msg = getMsg();
		BufferedImage[] images = new BufferedImage[(int) Math.ceil(1.0 * msg.length() / numChars)];
		for (int i = 0; i < images.length; i++){
			try {
				images[i] = ImageIO.read(catFile);
			} catch (IOException e){
				System.out.println("Couldn't read. Shit.");
				return;
			}
		}
		Random rand = new Random();
		int startPlace = rand.nextInt(((numChars * images.length) - msg.length()) * 4);
		System.out.println(startPlace);
		for (int i = 0; i < msg.length() * 16; i++){
			int valToSet = (msg.charAt(i/16) >> (i % 16)) & 1;
			int curr = startPlace + i;
			BufferedImage currImage = images[curr / (images[0].getWidth() * images[0].getHeight() * 4)];
			int row = ((curr / 4) / images[0].getWidth()) % images[0].getHeight();
			int col =(curr / 4) % images[0].getWidth();
			int valInPix = curr % 4;
			int newRGB = (currImage.getRGB(col, row) & ~(1 << (valInPix * 8))) | (valToSet << (valInPix * 8));
			currImage.setRGB(col, row, newRGB);

		}
	}

	public static String getMsg(){
		System.out.print("Enter the message you want to hide in the image: ");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}


}