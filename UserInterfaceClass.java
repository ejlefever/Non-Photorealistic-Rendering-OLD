import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.util.Stack;

import javax.swing.*;

public class UserInterfaceClass {
	
	//Initialize Everything
	private BufferedImage newImage = null;
	private int style = 0;
	boolean funny = false;
	private JLabel jlabel = new JLabel();
	private JLabel jlabeldst = new JLabel();
	private JFrame editorFrame = new JFrame("Non-Photorealistic Rendering");
	private JPanel imagePanel = new JPanel();
	private JPanel bPanel = new JPanel(new GridLayout(25, 2));
	//buttons
	private JButton brighten = new JButton("Brightness");
	private JButton contrast = new JButton("Contrast");
	private JButton saturation = new JButton("Saturation");
	private JButton blur = new JButton("Blur");
	private JButton sharpen = new JButton("Sharpen");
	private JButton edgedetect = new JButton("EdgeDetect");
	private JButton rdither = new JButton("Random Dither");
	private JButton odither = new JButton("Ordered Dither");
	private JButton crystallize = new JButton("Crystallize");
	private JButton kaleidoscope = new JButton("Kaleidoscope");
	private JButton modern = new JButton("Modern");
	private JButton oil = new JButton("Oil");
	private JButton chrome = new JButton("Chrome");
	private JButton fun = new JButton("Mystery");
	private JButton notfun = new JButton("Reset");
	private JButton goback = new JButton("Go back");
	private JButton save = new JButton("Save");
	private JButton empty = new JButton();
	//textfield
	private JTextField brightenValue = new JTextField();
	private JTextField contrastValue = new JTextField();
	private JTextField saturationValue = new JTextField();
	private JTextField blurValue = new JTextField();
	private JTextField sharpenValue = new JTextField();
	
	//stack
	Stack<BufferedImage> imageStack = new Stack<BufferedImage>();
	
	public UserInterfaceClass(final BufferedImage image, final BufferedImage destination)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				//main feats
				Image img = initializeMainFeatures(image, destination);
								
				//fun
				setButtonFeatures(fun);				
				fun.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						editorFrame.setVisible(false);
						editorFrame.dispose();
						new UserInterfaceClass(image, image);
					}
				});
				
				//not fun
				setButtonFeatures(notfun);				
				notfun.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						editorFrame.setVisible(false);
						editorFrame.dispose();
						//run program again
						npr.main(npr.newargs);
					}
				});
				
				
				//brighten
				JPanel brightenPane = new JPanel(new GridLayout(0, 2));
				brightenPane.setBackground(Color.white);
				setButtonFeatures(brighten);
				brighten.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						float value = Float.parseFloat(brightenValue.getText());
						setNewImageIcon(npr.Brighten(image, destination, value));
						imageStack.add(newImage);
						updateImage();
					}
				});
				brightenPane.add(brighten);
				brightenPane.add(brightenValue);

				//contrast
				JPanel contrastPane = new JPanel(new GridLayout(0, 2));
				contrastPane.setBackground(Color.white);
				setButtonFeatures(contrast);
				contrast.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						float value = Float.parseFloat(contrastValue.getText());
						setNewImageIcon(npr.AdjustContrast(image, destination, value));
						imageStack.add(newImage);
						updateImage();
					}
				});
				contrastPane.add(contrast);
				contrastPane.add(contrastValue);

				//saturation
				JPanel saturationPane = new JPanel(new GridLayout(0, 2));
				saturationPane.setBackground(Color.white);
				setButtonFeatures(saturation);
				saturation.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						float value = Float.parseFloat(saturationValue.getText());
						setNewImageIcon(npr.AdjustSaturation(image, destination, value));
						imageStack.add(newImage);
						updateImage();
					}
				});
				saturationPane.add(saturation);
				saturationPane.add(saturationValue);

				//blur
				JPanel blurPane = new JPanel(new GridLayout(0, 2));
				blurPane.setBackground(Color.white);
				setButtonFeatures(blur);
				blur.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						float value = Float.parseFloat(blurValue.getText());
						setNewImageIcon(npr.Blur(image, destination, value));
						imageStack.add(newImage);
						updateImage();
					}
				});
				blurPane.add(blur);
				blurPane.add(blurValue);

				//sharpen
				JPanel sharpenPane = new JPanel(new GridLayout(0, 2));
				sharpenPane.setBackground(Color.white);
				setButtonFeatures(sharpen);
				sharpen.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						float value = Float.parseFloat(sharpenValue.getText());
						setNewImageIcon(npr.Sharpen(image, destination, value));
						imageStack.add(newImage);
						updateImage();
					}
				});
				sharpenPane.add(sharpen);
				sharpenPane.add(sharpenValue);

				//edgedetect
				setButtonFeatures(edgedetect);				
				edgedetect.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.EdgeDetect(image, destination));
						imageStack.add(newImage);
						updateImage();
					}
				});

				//rdither
				setButtonFeatures(rdither);				
				rdither.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.RandomDither(image, destination));
						imageStack.add(newImage);
						updateImage();
					}
				});

				//odither
				setButtonFeatures(odither);				
				odither.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.RandomDither(image, destination));
						imageStack.add(newImage);
						updateImage();
					}
				});
				
				//crystallize
				setButtonFeatures(crystallize);				
				crystallize.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.Crystallize(image, destination));
						imageStack.add(newImage);
						updateImage();
					}
				});
				
				//kaleidoscope
				setButtonFeatures(kaleidoscope);
				kaleidoscope.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.Kaleidoscope(image, destination));
						imageStack.add(newImage);
						updateImage();
					}
				});

				//rays
				setButtonFeatures(modern);
				modern.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.Modern(image, destination));
						npr.saveToDisk(newImage);						
						imageStack.add(newImage);
						updateImage();
					}
				});
				
				//sparkle
				setButtonFeatures(oil);
				oil.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.oilPaint(image, destination));
						npr.saveToDisk(newImage);
						imageStack.add(newImage);
						updateImage();
					}
				});
				
				//chrome
				setButtonFeatures(chrome);
				chrome.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setNewImageIcon(npr.Chrome(image, destination));
						imageStack.add(newImage);
						updateImage();
					}
				});

				setButtonFeatures(save);
				save.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						npr.saveToDisk(newImage);
					}
				});

				setButtonFeatures(goback);
				goback.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						imageStack.pop();
						updateImage();
					}
				});

				//non image processes
				setButtonFeatures(empty);
				empty.setBorderPainted(false);
				
				//add buttons to button panel
				bPanel.add(brightenPane);
				bPanel.add(contrastPane);
				bPanel.add(saturationPane);
				bPanel.add(blurPane);
				bPanel.add(sharpenPane);
				bPanel.add(rdither);
				bPanel.add(odither);
				bPanel.add(crystallize);
				bPanel.add(kaleidoscope);
				bPanel.add(modern);
				bPanel.add(oil);
				bPanel.add(chrome);
				bPanel.add(fun);
				bPanel.add(empty);
				bPanel.add(goback);
				bPanel.add(notfun);
				bPanel.add(save);
				

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
	
	public void updateImage()
	{
		jlabeldst.setIcon(getImageIcon());
		jlabeldst.updateUI();
	}
	
	public Image initializeMainFeatures(BufferedImage image, BufferedImage destination)
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
		ImageIcon imageIcon;
		ImageIcon dstIcon;

		imageIcon = new ImageIcon(img);
		dstIcon = new ImageIcon(dst);
		
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
		
		return img;
	}

	public void setButtonFeatures(JButton b){
		b.setBackground(Color.white);
		b.setFocusPainted(false);
	}
	
	public ImageIcon getImageIcon()
	{
		Image tmp = imageToResize(imageStack.peek(), style);
		ImageIcon tm = new ImageIcon(tmp);
		return tm;
	}

	public void setNewImageIcon(BufferedImage image) {
		if(imageStack.isEmpty())
			newImage = image;
		else
			imageStack.add(image);
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
