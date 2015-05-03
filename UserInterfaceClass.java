import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

public class UserInterfaceClass {
	
	//Initialize Everything
	BufferedImage newImage = null;
	int style = 0;
	private JLabel jlabel = new JLabel();
	private JLabel jlabeldst = new JLabel();
	private JButton crystallize = new JButton("Crystallize");
	private JFrame editorFrame = new JFrame("Non-Photorealistic Rendering");
	private JPanel imagePanel = new JPanel();
	private JPanel bPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

	public UserInterfaceClass(final BufferedImage image, final BufferedImage destination)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				if(image.getHeight() > image.getWidth())
					style = 1;

				//frame
				editorFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
				editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				editorFrame.getContentPane().setBackground(Color.white);
				
				//image resize
				Image img = imageToResize(image, style);
				Image dst = imageToResize(destination, style);
				
				//Icon
				ImageIcon imageIcon = new ImageIcon(img);
				ImageIcon dstIcon = new ImageIcon(dst);
				
				//imagePanel
				imagePanel.setBackground(Color.white);
				editorFrame.add(imagePanel);
				
				//buttonPanel
				bPanel.setBackground(Color.white);
				editorFrame.add(bPanel);
				
				
				//label
				jlabel.setIcon(imageIcon);				
				jlabeldst.setIcon(dstIcon);
				
				if(image.getHeight() > image.getWidth())
				{
					imagePanel.add(jlabel, BorderLayout.WEST);
					imagePanel.add(jlabeldst, BorderLayout.CENTER);
				}
				else
				{
					imagePanel.add(jlabel, BorderLayout.NORTH);
					imagePanel.add(jlabeldst, BorderLayout.SOUTH);
				}
				
				//set buttons
				crystallize.setBackground(Color.white);
				crystallize.setFocusPainted(false);
				crystallize.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// TODO Auto-generated method stub
						//new UserInterfaceClass(image, npr.Crystallize(image, destination));
						setNewImageIcon(npr.Crystallize(image, destination));
						jlabeldst.setIcon(getImageIcon());
						jlabeldst.updateUI();
					}
				});
				
				//add buttons to panel
				bPanel.add(crystallize);	

				//bPanel size after adding everything
				double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
				bPanel.setPreferredSize(new Dimension((int) (width - 30 - (img.getWidth(null) * 2)), img.getHeight(null)));
				
				//finalize JFrame
				editorFrame.pack();
				editorFrame.setLocationRelativeTo(null);
				editorFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				editorFrame.setVisible(true);								
			}			
		});
		
	}
	
	public ImageIcon getImageIcon()
	{
		Image tmp = imageToResize(newImage, style);
		ImageIcon tm = new ImageIcon(tmp);
		return tm;
	}

	public void setNewImageIcon(BufferedImage image) {
		newImage = image;
	}
	
	public Image imageToResize(BufferedImage image, int style)
	{
		//get screen dimensions
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		//int screenWidth = (int) screen.getWidth();
		int screenHeight = (int) screen.getHeight();
		
		//get image dimensions
		int imageWidth = (int) image.getWidth();
		int imageHeight = (int) image.getHeight();
		
		//match height scale width
		//boundary height * original width / original height
		if(style == 1)
		{
			int new_width = (screenHeight * imageWidth)/imageHeight;

			//scale image
			return image.getScaledInstance(new_width, screenHeight, Image.SCALE_SMOOTH);
		}
		else
		{
			int new_height = screenHeight / 2;
			int new_width = (new_height * imageWidth)/imageHeight;
			
			//scale image
			return image.getScaledInstance(new_width, new_height, Image.SCALE_SMOOTH);
		}
	}

}
