import com.jhlabs.image.*;

import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.io.*;
import java.util.*;

public class npr {
	
	public static String[] newargs;

	/*public static void printUsage() {
		System.out.println("Usage: java imProcess -input <filename> [options]");
		System.out.println("-output <filename>");
		System.exit(1);
	}*/

	public static void main(String[] args) {
		int i = 0;
		BufferedImage src = null, dst = null;
		BufferedImage tmp = null;
		int width, height;			

		String arg;
		String outputfilename = "output.png";	
		newargs = args;
		
		while (i < args.length) {
			arg = args[i++];

			if (arg.equals("-input")) {

				String inputfile = args[i++];
				System.out.println(inputfile);
				try {
					src = ImageIO.read(new File(inputfile));
				} catch (IOException e) {
					System.out.print(e.toString());
				}
				width = src.getWidth();
				height = src.getHeight();
				dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				//continue;
			}
				/*
				} else if (arg.equals("-output")) {

				outputfilename = args[++i];
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
				new UserInterfaceClass(src, dst);
				
			} else if (arg.equals("-kaleidoscope")) {

				System.out.println("Kaleidoscoping");
				Kaleidoscope(src, dst);
			
			} else if (arg.equals("-chrome")) {

				System.out.println("Generating chrome");
				Chrome(src, dst);
			
			} else if (arg.equals("-abstractImpressionist")) {

				System.out.println("Turning into Abstract Impressionism");
				abstractImpressionist(src, dst);
			
			} else if (arg.equals("-motion")) {

				System.out.println("Motion");
				Motion(src, dst);
			
			}  else if (arg.equals("-paint")) {

				System.out.println("Converting into Oil Painting");
				oilPaint(src, dst);
			
			} else if (arg.equals("-modern")) {

				System.out.println("Modernizing");
				Modern(src, dst); 
			
			} /*else if (arg.equals("-something")) {

				System.out.println("Doing something");
				something(src, dst); 
			
			} else {
				printUsage();
			} */
			new UserInterfaceClass(src, dst);
			tmp = src; src = dst; dst = tmp;
		//}
		if (i != args.length) {
			System.out.println("there are unused arguments");
		}
		// write the output image to disk file
		
		File outfile = new File(outputfilename);
		try {
			ImageIO.write(src, "png", outfile);
		} catch(IOException e) {
		}
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

	// Previously provided sample code
	public static BufferedImage Brighten(BufferedImage src, BufferedImage dst, float brightness) {

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
			r = PixelUtils.clamp((int)((float)r * brightness));
			g = PixelUtils.clamp((int)((float)g * brightness));
			b = PixelUtils.clamp((int)((float)b * brightness));

			pixels[i] = new Color(r, g, b, a).getRGB();
		}

		dst.setRGB(0, 0, width, height, pixels, 0, width);
		return dst;

	}

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

	// saturation = 0 gives a gray scale version of the image
	// saturation = 1 gives the original image
	public static BufferedImage AdjustSaturation(BufferedImage src, BufferedImage dst, float saturation) {

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
		
	public static BufferedImage Blur(BufferedImage src, BufferedImage dst, float radius) {

		GaussianFilter filter = new GaussianFilter();
		filter.setRadius(radius);
		filter.filter(src, dst);
		return dst;
	}

	public static BufferedImage Sharpen(BufferedImage src, BufferedImage dst, float sharpness) {

		float[] sharpenMatrix = {0, -sharpness, 0, - sharpness, 1+(4*sharpness), -sharpness, 0, - sharpness, 0};
		ConvolveFilter filter = new ConvolveFilter(3, 3, sharpenMatrix);
		filter.filter(src, dst);
		return dst;
		
	}

	public static BufferedImage EdgeDetect(BufferedImage src, BufferedImage dst) {

		EdgeFilter filter = new EdgeFilter();
		filter.filter(src, dst);
		return dst;
	}

	// compare each image pixel against a random threshold to quantize it to 0 or 1
	// ignoring the color, and just use the luminance of a pixel to do the dithering
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

	// compare each image pixel against a pseudo-random threshold to quantize it to 0 or 1
	// in this case, the pseudo random number is given by a 4x4 Bayers matrix
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
	
	public static BufferedImage Chrome(BufferedImage src, BufferedImage dst) {
		
		ChromeFilter filter = new ChromeFilter();
		filter.filter(src, dst);
		return dst;
		
	}
	
	public static BufferedImage abstractImpressionist(BufferedImage src, BufferedImage dst) {
		
		final int[][] emboss = {
					{-1,  -1,  -1,  -1,  0},
					{-1,  -1,  -1,   0,  1},
					{-1,  -1,   0,   1,  1},
					{-1,   0,   1,   1,  1}, 
					{0,    1,   1,   1,  1}
				};
		int width = src.getWidth();
		int height = src.getHeight();
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage tmp2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int[] pixels = new int[width*height];
		src.getRGB(0, 0, width, height, pixels, 0, width);
		
		int[][] image = new int[width][height];
		int[][] result = new int[width][height];
		       
		int counter = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image[i][j] = pixels[counter];
				counter++;
			}
		}
	
		int e = 128;
		int r, g, b;
		     
	    for(int x = 0; x < width; x++) {
	    	for(int y = 0; y < height; y++) {
	    
	    		float red = 0.0f, green = 0.0f, blue = 0.0f; 
	    		for (int filterX = 0; filterX < 5; filterX++) {
	    			for(int filterY = 0; filterY < 5; filterY++) {
	    				int imageX = (x - 9 / 2 + filterX + width) % width; 
	    	            int imageY = (y - 9 / 2 + filterY + height) % height; 
	    	            Color c = new Color(src.getRGB(imageX, imageY));
	    	            red += c.getRed() * emboss[filterX][filterY]; 
	    	            green += c.getGreen() * emboss[filterX][filterY]; 
	    	            blue += c.getBlue() * emboss[filterX][filterY]; 
	    			}
	    		}
		        r = Math.min(Math.max((int)(red + e), 0), 255); 
		        g = Math.min(Math.max((int)(green + e), 0), 255); 
		        b = Math.min(Math.max((int)(blue + e), 0), 255);
		        result[x][y] = new Color(r, g, b, 255).getRGB();
	    	}  
    
	    }
	    
	    for (int i = 0; i < result.length; i++) {
	    	for (int j = 0; j < result[0].length; j++) {
	    		tmp.setRGB(i, j, result[i][j]);
	    	}
	    }
				
	    oilPaint(tmp, tmp2);
	    AdjustSaturation(tmp2, dst, 2.0f);
	    return dst;
	}	
		
	

	public static BufferedImage Motion(BufferedImage src, BufferedImage dst) {
	
		int[][] filter =
			{
			    {1, 0, 0, 0, 0, 0, 0, 0, 0},
			    {0, 1, 0, 0, 0, 0, 0, 0, 0},
			    {0, 0, 1, 0, 0, 0, 0, 0, 0},
			    {0, 0, 0, 1, 0, 0, 0, 0, 0},
			    {0, 0, 0, 0, 1, 0, 0, 0, 0},
			    {0, 0, 0, 0, 0, 1, 0, 0, 0},
			    {0, 0, 0, 0, 0, 0, 1, 0, 0},
			    {0, 0, 0, 0, 0, 0, 0, 1, 0},
			    {0, 0, 0, 0, 0, 0, 0, 0, 1}
			};
		int width = src.getWidth();
		int height = src.getHeight();
	
	
		int[] pixels = new int[width*height];
		src.getRGB(0, 0, width, height, pixels, 0, width);
		
		int[][] image = new int[width][height];
		int[][] result = new int[width][height];
		       
		int counter = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image[i][j] = pixels[counter];
				counter++;
			}
		}
	
		float f = 1.0f / 10.0f; 
		int r, g, b;
		     
	    for(int x = 0; x < width; x++) {
	    	for(int y = 0; y < height; y++) {
	    		float red = 0.0f, green = 0.0f, blue = 0.0f; 
	    		for (int filterX = 0; filterX < 9; filterX++) {
	    			for(int filterY = 0; filterY < 9; filterY++) {
	    				int imageX = (x - 9 / 2 + filterX + width) % width; 
	    	            int imageY = (y - 9 / 2 + filterY + height) % height; 
	    	            Color c = new Color(src.getRGB(imageX, imageY));
	    	            red += c.getRed() * filter[filterX][filterY]; 
	    	            green += c.getGreen() * filter[filterX][filterY]; 
	    	            blue += c.getBlue() * filter[filterX][filterY]; 
	    			}
	    		}
		        r = Math.min(Math.max((int)(f * red), 0), 255); 
		        g = Math.min(Math.max((int)(f * green), 0), 255); 
		        b = Math.min(Math.max((int)(f * blue), 0), 255);
		        result[x][y] = new Color(r, g, b, 255).getRGB();
	    	}  
    
	    }
	    
	    for (int i = 0; i < result.length; i++) {
	    	for (int j = 0; j < result[0].length; j++) {
	    		dst.setRGB(i, j, result[i][j]);
	    	}
	    }
		return dst;
	    
	}
	
	public static BufferedImage Modern(BufferedImage src, BufferedImage dst) {
		
		int width = src.getWidth();
		int height = src.getHeight();
		int[] pixels = new int[width*height];
		src.getRGB(0, 0, width, height, pixels, 0, width);
		quickSort(pixels, 0, pixels.length - 1);
		BufferedImage tmp1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage tmp2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		tmp1.setRGB(0, 0, width, height, pixels, 0, width);
		Motion(tmp1, tmp2);
		return oilPaint(tmp2, dst);
		
	}
	
	public static void quickSort(int[] unsorted, int lowIndex, int highIndex) {
	    
        int low = lowIndex;
        int high = highIndex;
        int pivot = (unsorted[low] + unsorted[high] + unsorted[(low + high)/2])/3;

        while (low <= high) {
            while (unsorted[low] < pivot) { 
                low++;
            } 
            while (unsorted[high] > pivot) { 
                high--;
            } 
            if (low <= high) {
                swap(unsorted, low, high);
                low++;
                high--;
            } 
        }
        if (lowIndex < high) {
            quickSort(unsorted, lowIndex, high);
        }
        if (low < highIndex) {
            quickSort(unsorted, low, highIndex);
        }
        
    }
	
	public static void swap(int[] unsorted, int i, int j) {

        int temp = unsorted[i];
        unsorted[i] = unsorted[j];
        unsorted[j] = temp;

    }
	
	/*public static BufferedImage something(BufferedImage src, BufferedImage dst) {
		
		int width = src.getWidth();
		int height = src.getHeight();
		int rows = width/5;
		int cols = height/5;
		int nh = 10; //number of halftones
		int c = (int)(0.001 + Math.sqrt((double)nh));
		nh = 256/nh;
		int[] pixels = new int[width*height];
		src.getRGB(0, 0, width, height, pixels, 0, width);
		
		int[][] image = new int[rows][cols];		       
		int[][] result = new int[rows][cols];
		int counter = 0;
		/*for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image[i][j] = pixels[counter];
				counter++;
			}
		}*/
		/*for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				dst.setRGB(i, j, 0); //all white
			}
		}
		int count = 0;
		int black = new Color(0, 0, 0).getRGB(); 
		for (int i = 0; i < rows-c+1; i+=c) {
			for (int j = 0; j < cols-c+1; j+=c) {
				count = src.getRGB(i, j);
				for (int x = i; x < i+c && count!= 0; x++) {
					for (int y = j; y < j+c && count != 0; y++) {
						dst.setRGB(x, y, black);
						count--;
					}
				}
			}
		}*/
		
		/*
		int width = src.getWidth();
		int height = src.getHeight();
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Crystallize(src, tmp);
		oilPaint(tmp, dst);*/
		
	//}
	
	public static BufferedImage oilPaint(BufferedImage src, BufferedImage dst) {
		
		/* 
		 * How this algorithm works:
		 * each pixel is sorted into an intensity'bin' 
		 * intensity usually defined by (r + b + g)/3, but we'll divide that by 20 to create a more blocky effect
		 * therefore, by dividing by 20, each pixel has its intensity 'binned'
		 * for each pixel, you examine the surrounding pixels, calculating their intensities
		 * then determine which intensity bin has the highest number of pixels in it
		 * And determine the final color based on that intensity
		 * source: http://supercomputingblog.com/graphics/oil-painting-algorithm/
		 * */
		
		int width = src.getWidth();
		int height = src.getHeight();
		int[] pixels = new int[width*height];
		src.getRGB(0, 0, width, height, pixels, 0, width);
		
		int[][] image = new int[width][height];		       
		int counter = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image[i][j] = pixels[counter];
				counter++;
			}
		} //now have a integer[][] that maps out the pixels in the image
		
		int curIntens; //keep track of the current intensity
		int red, green, blue;
		int finalR, finalG, finalB, k;
		
		for (int i = 0; i < width; i++) { //go through every pixel
			for(int j = 0; j < height; j++) {
				int[] intensCount = new int[21]; //count the number of sub-pixels in each intensity bin
				int[] totR = new int[21]; //keep track of total color values for each bin
				int[] totG = new int[21];
				int[] totB = new int[21];
				for (int a = -5; a <= 5; a++) { //go through every pixel within radius of 5
					for (int b = -5; b <= 5; b++) {
						Color c = new Color(src.getRGB(i, j));
						if (i >= 5 && i < (width - 5) && j >= 5 && j < (height - 5)) {
							c = new Color(src.getRGB(i + a, j + b));
						} if (i >= 5 && i < (width - 5) && !(j >= 5 && j < (height - 5))) {
							c = new Color(src.getRGB(i + a, j));
						}if (!(i >= 5 && i < (width - 5)) && j >= 5 && j < (height - 5)) {
							c = new Color(src.getRGB(i, j + b));
						}
						red = c.getRed(); 
						green = c.getGreen();
						blue = c.getBlue();
						curIntens = ((red + green + blue)/3)*20/255; 
						intensCount[curIntens]++;
						totR[curIntens] += red;
						totG[curIntens] += green;
						totB[curIntens] += blue;
					}
				} 
				int currentMax = 0;
				int maxIndex = 0;
				for (int m = 0; m < 20; m++) {
					if (intensCount[m] > currentMax) {
						currentMax = intensCount[m];
						maxIndex = m;
					}
				}
				if (currentMax != 0) {
					finalR = totR[maxIndex] / currentMax;
					if (finalR < 0) {
						finalR = -1* finalR;
					}
					if (finalR > 255) {
						finalR = 255;
					}
					finalG = totG[maxIndex] / currentMax;
					if (finalG < 0) {
						finalG = -1 * finalG;
					}
					if (finalG > 255) {
						finalG = 255;
					}
					finalB = totB[maxIndex] / currentMax;
					if (finalB < 0) {
						finalB = -1 * finalB;
					}
					if (finalB > 255) {
						finalB = 254;
					} 
					k = new Color(finalR, finalG, finalB, 255).getRGB();
					dst.setRGB(i, j, k);
				} else {
					Color c = new Color(src.getRGB(i, j));
					dst.setRGB(i, j, c.getRGB()); 
				} 
			}
		}
		return dst;
			
	}
	
}