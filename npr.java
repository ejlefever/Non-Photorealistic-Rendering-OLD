import com.jhlabs.image.*;

import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;
import java.util.*;

public class npr {
	public static String[] newargs;

	public static void printUsage() {
		System.out.println("Usage: java imProcess -input <filename> [options]");
		System.out.println("-output <filename>");
		System.out.println("-brightness <float>");
		System.out.println("-edgedetect");
		System.out.println("-blur <float>");

		System.out.println("-contrast <float>");
		System.out.println("-saturation <float>");
		System.out.println("-sharpen <float>");
		System.out.println("-randomdither");
		System.out.println("-ordereddither");
		System.out.println("-mosaic <imagefolder>");
		System.exit(1);
	}

	public static void main(String[] args) {
		int i = 0;
		BufferedImage src = null, dst = null;
		BufferedImage tmp = null;	// used for swapping src and dst buffer
		int width, height;			// image width, height

		String arg;
//		String outputfilename = "output.png";		// default output filename
		newargs = args;
		
		if (args.length < 1) {
			printUsage();
		}
		
		// parse command line options, and call approrpiate member functions
		//while (i <= args.length) {
			//arg = args[i++];

			//if (arg.equals("-input")) {

				String inputfile = args[++i];
				try 
				{
					src = ImageIO.read(new File(inputfile));
				} 
				catch (IOException e) 
				{
					System.out.print(e.toString());
				}
				width = src.getWidth();
				height = src.getHeight();
				dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				//continue;
			
			//}
			/*
			else if (arg.equals("-output")) {

				outputfilename = args[i++];
				System.out.println("Output file: " + outputfilename);
				continue;

			} else if (arg.equals("-brightness")) {

				float brightness = Float.parseFloat(args[i++]);
				System.out.println("Set brightness: " + brightness);
				Brighten(src, dst, brightness);

			} else if (arg.equals("-contrast")) {

				float contrast = Float.parseFloat(args[i++]);
				System.out.println("Set contrast: " + contrast);

				AdjustContrast(src, dst, contrast);

			} else if (arg.equals("-saturation")) {

				float saturation = Float.parseFloat(args[i++]);
				System.out.println("Set saturation: " + saturation);

				AdjustSaturation(src, dst, saturation);

			} else if (arg.equals("-randomdither")) {

				System.out.println("Generated random dithering");

				RandomDither(src, dst);

			} else if (arg.equals("-ordereddither")) {

				System.out.println("Generate ordered dithering");

				OrderedDither(src, dst);
			} else if (arg.equals("-blur")) {

				float radius = Float.parseFloat(args[i++]);
				System.out.println("Set blur radius: " + radius);
				
				Blur(src, dst, radius);

			} else if (arg.equals("-sharpen")) {

				float sharpness = Float.parseFloat(args[i++]);
				System.out.println("Set sharpness : " + sharpness);

				Sharpen(src, dst, sharpness);

			} else if (arg.equals("-edgedetect")) {

				System.out.println("Apply edge detector");

				EdgeDetect(src, dst);

			} else if (arg.equals("-crystallize")) {

				System.out.println("Crystallizing");
				Crystallize(src, dst);
				
			} else if (arg.equals("-kaleidoscope")) {

				System.out.println("Kaleidoscoping");
				Kaleidoscope(src, dst);
			
			} else if (arg.equals("-rays")) {

				System.out.println("Generating rays");
				Rays(src, dst);
			
			} else if (arg.equals("-sparkle")) {

				System.out.println("Generating sparkles");
				Sparkle(src, dst);
			
			} else if (arg.equals("-chrome")) {

				System.out.println("Generating chrome");
				Chrome(src, dst);
			
			} else {
				printUsage();
			}
			*/
			new UserInterfaceClass(src, dst);
			tmp = src; src = dst; dst = tmp;
		//}
		if (i != args.length) {
			System.out.println("there are unused arguments");
		}
	}
	
	public static void saveToDisk(BufferedImage src)
	{
		// write the output image to disk file
		File outfile = new File("output.png");
		try 
		{
			ImageIO.write(src, "png", outfile);
		} 
		catch(IOException e) {
		}
	}

	// Change the brightness of an image
	// brightness is a scaling factor 
	// Use this function as an example. There is nothing you need to change here
	public static BufferedImage Brighten(BufferedImage src, BufferedImage dst, float brightness) {

		int width = src.getWidth();
		int height = src.getHeight();

		// a buffer that stores the destination image pixels
		int[] pixels = new int[width * height];
	
		// get the pixels of the source image	
		src.getRGB(0, 0, width, height, pixels, 0, width);

		int i;
		int a, r, g, b;
		for(i = 0; i < width * height; i ++) {
			Color rgb = new Color(pixels[i]);
			// a color is represented as an integer (4 bytes); 
			// each of the alpha, red, green, blue channels is stored in one byte in order;
			// you can use the Color class to extract the value of each individual channel
			// or composite a new integer color from the separated components
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			r = PixelUtils.clamp((int)((float)r * brightness));
			g = PixelUtils.clamp((int)((float)g * brightness));
			b = PixelUtils.clamp((int)((float)b * brightness));

			pixels[i] = new Color(r, g, b, a).getRGB();
		}

		// write pixel values to the destination image
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		return dst;

	}

	// change the contrast of an image
	// contrast = 0 gives a medium gray (0.5, 0.5, 0.5) image
	// constrat = 1 gives the original image
	public static BufferedImage AdjustContrast(BufferedImage src, BufferedImage dst, float contrast) {
		
		int width = src.getWidth();
		int height = src.getHeight();
		int[] pixels = new int[width * height];
		src.getRGB(0, 0, width, height, pixels, 0, width);
		int i;
		int a, r, g, b;
		for(i = 0; i < width * height; i ++) {
			Color rgb = new Color(pixels[i]);
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			r = PixelUtils.clamp((int)((float)r * contrast + 0.5*(1-contrast)));
			g = PixelUtils.clamp((int)((float)g * contrast + 0.5*(1-contrast)));
			b = PixelUtils.clamp((int)((float)b * contrast + 0.5*(1-contrast)));
			pixels[i] = new Color(r, g, b, a).getRGB();
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		return dst;
	}

	// change the saturation of an image
	// saturation = 0 gives a gray scale version of the image
	// saturation = 1 gives the original image
	public static BufferedImage AdjustSaturation(BufferedImage src, BufferedImage dst, float saturation) {

		//output[p] = input[p]*t + L*(1-t);
		float L;
		int width = src.getWidth();
		int height = src.getHeight();
		int[] pixels = new int[width * height];
		src.getRGB(0, 0, width, height, pixels, 0, width);
		int i;
		int a, r, g, b;
		for(i = 0; i < width * height; i ++) {
			Color rgb = new Color(pixels[i]);
			a = rgb.getAlpha();
			r = rgb.getRed();
			g = rgb.getGreen();
			b = rgb.getBlue();
			//L is the gray-scale value of a pixel
			L = (float)(0.30*r + 0.59*g + 0.11*b);
			r = PixelUtils.clamp((int)((float)r * saturation + L*(1-saturation)));
			g = PixelUtils.clamp((int)((float)g * saturation + L*(1-saturation)));
			b = PixelUtils.clamp((int)((float)b * saturation + L*(1-saturation)));
			pixels[i] = new Color(r, g, b, a).getRGB();
		}
		dst.setRGB(0, 0, width, height, pixels, 0, width);
		return dst;
	}
		

	// blur an image
	// use the GaussianFilter from jhlabs to perform Gaussian blur
	// This function is a given, and there is nothing you need to change here
	public static BufferedImage Blur(BufferedImage src, BufferedImage dst, float radius) {
		GaussianFilter filter = new GaussianFilter();
		filter.setRadius(radius);
		filter.filter(src, dst);
		return dst;
	}

	// sharpen an image
	// sharpness sets the amount of sharpening
	// use the ConvolveFilter from jhlabs and the sharpening matrix we covered in class to perform this operation
	public static BufferedImage Sharpen(BufferedImage src, BufferedImage dst, float sharpness) {
		float[] sharpenMatrix = {0, -sharpness, 0, - sharpness, 1+(4*sharpness), -sharpness, 0, - sharpness, 0};
		ConvolveFilter filter = new ConvolveFilter(3, 3, sharpenMatrix);
		filter.filter(src, dst);
		return dst;
		
	}

	// detect edge features of an image
	// use the EdgeFilter from jhlabs
	// This function is a given, and there is nothing you need to change here
	public static BufferedImage EdgeDetect(BufferedImage src, BufferedImage dst) {

		EdgeFilter filter = new EdgeFilter();
		filter.filter(src, dst);
		return dst;
	}

	// random dithering
	// compare each image pixel against a random threshold to quantize it to 0 or 1
	// ignore the color, and just use the luminance of a pixel to do the dithering
	// your output should be a binary (black-white) image
	public static BufferedImage RandomDither(BufferedImage src, BufferedImage dst) {
		
		int width = src.getWidth();
		int height = src.getHeight();
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		AdjustSaturation(src, tmp, 0);
		int c;
		double e;
		int black = new Color(0, 0, 0).getRGB(); 
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j++) {
				c = -1*tmp.getRGB(i, j); 
				e = Math.random(); 
				if (c >= (e*16777216)) { 			
					dst.setRGB(i, j, black);
				} else {
					dst.setRGB(i, j, 0);
				}
			}		
		}
		return dst; 
		
	}

	// ordered dithering
	// compare each image pixel against a pseudo-random threshold to quantize it to 0 or 1
	// in this case, the pseudo random number is given by a 4x4 Bayers matrix
	// ignore the color, and just use the luminance of a pixel to do the dithering
	// your output should be a binary (black-white) image
	public static BufferedImage OrderedDither(BufferedImage src, BufferedImage dst) {
		
		final float[][] Bayers = {{15/16.f,  7/16.f,  13/16.f,   5/16.f},
								  {3/16.f,  11/16.f,   1/16.f,   9/16.f},
								  {12/16.f,  4/16.f,  14/16.f,   6/16.f},
								  { 0,      8/16.f,    2/16.f,  10/16.f} };
		int width = src.getWidth();
		int height = src.getHeight();
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		AdjustSaturation(src, tmp, 0);
		int c;
		double e;
		int black = new Color(0, 0, 0).getRGB(); 
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j++) {
				c = -1*tmp.getRGB(i, j); 
				e = Bayers[i%2][j%2];
				if (c >= (e*16777216)) { 			
					dst.setRGB(i, j, black);
				} else {
					dst.setRGB(i, j, 0);
				}
			}		
		}
		return dst; 		

	}
	
	public static BufferedImage Crystallize(BufferedImage src, BufferedImage dst) {
		CrystallizeFilter filter = new CrystallizeFilter();
		filter.filter(src, dst);
		return dst;
	}

	public static BufferedImage Kaleidoscope(BufferedImage src, BufferedImage dst) {
		
		KaleidoscopeFilter filter = new KaleidoscopeFilter();
		filter.filter(src, dst);
		return dst;
		
	}
	
	public static BufferedImage Rays(BufferedImage src, BufferedImage dst) {
		RaysFilter filter = new RaysFilter();
		filter.filter(src, dst);
		return dst;
	}
	
	public static BufferedImage Sparkle(BufferedImage src, BufferedImage dst) {
		
		SparkleFilter filter = new SparkleFilter();
		filter.filter(src, dst);
		return dst;
		
	}
	
	public static BufferedImage Chrome(BufferedImage src, BufferedImage dst) {
		
		ChromeFilter filter = new ChromeFilter();
		filter.filter(src, dst);
		return dst;
		
	}
	
}
