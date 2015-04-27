import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class UserInterfaceClass {
	public UserInterfaceClass(final BufferedImage image, final BufferedImage destination)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame editorFrame = new JFrame("Non-Photorealistic Rendering");
				editorFrame.setLayout(new BorderLayout());
				editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
				Image img = imageToResize(image);
				Image dst = imageToResize(destination);
				
				ImageIcon imageIcon = new ImageIcon(img);
				ImageIcon dstIcon = new ImageIcon(dst);
				
				JLabel jlabel = new JLabel();
				jlabel.setIcon(imageIcon);
				
				JLabel jlabeldst = new JLabel();
				jlabeldst.setIcon(dstIcon);
				editorFrame.getContentPane().add(jlabel, BorderLayout.WEST);
				editorFrame.getContentPane().add(jlabeldst, BorderLayout.EAST);
				
				editorFrame.pack();
				//System.out.println(editorFrame.getSize());
				//editorFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				editorFrame.setLocationRelativeTo(null);
				editorFrame.setVisible(true);								
			}
		});
	}
	
	public Image imageToResize(BufferedImage image)
	{
		//get screen dimensions
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//int screenWidth = (int) screen.getWidth();
		int screenHeight = (int) screen.getHeight();
		//System.out.println("Screen Size: " + screenWidth + " " + screenHeight);
		
		//get image dimensions
		int imageWidth = (int) image.getWidth();
		int imageHeight = (int) image.getHeight();
		
		//match height scale width
		//boundary height * original width / original height
		int new_width = (screenHeight * imageWidth)/imageHeight;
		
		//scale image
		return image.getScaledInstance(new_width, screenHeight, Image.SCALE_SMOOTH);
	}

}
