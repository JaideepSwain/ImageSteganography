import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class SGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static{	
	try {
		
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
	
	private StringBuilder ssttrr=new StringBuilder();
	private JTextArea txtrYfytc;
	private JLabel label;
	private JPanel contentPane;
	private String path;
	private BufferedImage img=null;
	private BufferedImage jai=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SGUI frame = new SGUI();
					frame.setVisible(true);
					frame.setTitle("Steganographer");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 921, 736);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 661, 663);
		contentPane.add(scrollPane);
		
		label = new JLabel("");
		scrollPane.setViewportView(label);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(685, 13, 97, 25);
		btnOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Open();
			}
		});
		contentPane.add(btnOpen);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(794, 13, 97, 25);
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Save();
			}
		});
		contentPane.add(btnSave);
		
		txtrYfytc = new JTextArea();
		txtrYfytc.setFont(new Font("Georgia", Font.ITALIC, 16));
		txtrYfytc.setRows(6);
		txtrYfytc.setColumns(10);
		txtrYfytc.setBounds(682, 52, 203, 252);
		contentPane.add(txtrYfytc);
		
		JButton btnHide = new JButton("Hide");
		btnHide.setBounds(685, 347, 97, 25);
		btnHide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(chkLen()){
					Hide();
				}else{
					JOptionPane.showMessageDialog(null, "The message is too long for the selected image!!");
				}
			}
		});
		contentPane.add(btnHide);
		
		JButton btnExtract = new JButton("Extract");
		btnExtract.setBounds(794, 347, 97, 25);
		btnExtract.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Extract();
			}
		});
		contentPane.add(btnExtract);
	}


	//Opens and set the image to the GUI.
	void Open(){
		txtrYfytc.setText("");
		JFileChooser fc=new JFileChooser("C:\\Users\\AKASH\\Desktop");
		if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			File file=new File(path=fc.getSelectedFile().getAbsolutePath()) ;
			try {
				
				img=ImageIO.read(file);
				
				label.setIcon(new ImageIcon(img));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	
	
	
	boolean chkLen(){
		
		if((img.getHeight()*img.getWidth())>=((32+txtrYfytc.getText().length()*8)/4)){
			return true;
		}else{
			return false;
		}
	}
	
	void Hide(){
		int count=0;
		int k=0;
		byte [] barry=txtrYfytc.getText().getBytes();
		
		byte [] bit=new byte[barry.length*8];
		int LEN=bit.length;
		for(byte b:barry){
			// System.out.print(b);
			// System.out.print("\t");
			bit[k]=(byte)(b&0b1);k++;
			bit[k]=(byte)((b>>1)&0b1);k++;
			bit[k]=(byte)((b>>2)&0b1);k++;
			bit[k]=(byte)((b>>3)&0b1);k++;
			bit[k]=(byte)((b>>4)&0b1);k++;
			bit[k]=(byte)((b>>5)&0b1);k++;
			bit[k]=(byte)((b>>6)&0b1);k++;
			bit[k]=(byte)((b>>7)&0b1);k++;
		}
		
		k=0;
		int p,a,r,g,b;
	//	System.out.println("LEN at writing "+LEN);
		outer:
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				p=img.getRGB(j, i);
				if(count>=32){
					//writing data
					if(k>=LEN){
						break outer;
					}
					a=p>>24&0xff;
					r=p>>16&0xff;
					g=((p>>8)&0b11111110)|bit[k];k++;
					b=(p&0b11111110)|bit[k];k++;
					p=(a<<24)|(r<<16)|(g<<8)|(b);
				}else{
					//writing LEN
					
					a=p>>24&0xff;
					r=p>>16&0xff;
					g=p>>8&0xff;
					b=p&0xff;
					//System.out.print("("+a+";"+r+";"+g+";"+b+")--->");
					
					a=p>>24&0xff;
					r=p>>16&0xff;
					g=((p>>8)&0b11111110)|((LEN>>count)&0b1);count++;
					b=((p)&0b11111110)|((LEN>>count)&0b1);count++;
					p=(a<<24)|(r<<16)|(g<<8)|(b);
					//System.out.println("("+a+";"+r+";"+g+";"+b+")");
				}
				img.setRGB(j, i, p);
				
				
			}
		}
		label.setIcon(new ImageIcon(img));
		JOptionPane.showMessageDialog(null, "Done!!");
	}
	
	
	void Extract(){
		int k=0,count=0;
		int p,a,r,g,b;
		int LEN=0;
		int val=0,counter=0;
		outer:
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				p=img.getRGB(j, i);
				if(count>=32){
					if(k>=LEN){
						break outer;
					}
	
					val=val|((p>>8&1)<<counter++);
					val=val|((p&1)<<counter++);
					if (counter%8==0)
					{
						counter=0;
						k+=8;
						//System.out.print((char)val);
					char ch=(char)val;
					ssttrr.append(ch);
						val=0;
						
					}
				}else{
					
					LEN=LEN|((p>>8&1)<<count++);
					LEN=LEN|((p&1)<<count++);
					a=p>>24&0xff;
					r=p>>16&0xff;
					g=p>>8&0xff;
					b=p&0xff;
					//System.out.print("("+a+";"+r+";"+g+";"+b+")");
				}
				
			}
		}
		
		//System.out.println("\nLEN at reading "+LEN);
		JOptionPane.showMessageDialog(null, ssttrr);
		ssttrr.delete(0,ssttrr.length());
	}
	
	void Save(){
		try {
			File f=new File(path+"OUTPUT.png");
			ImageIO.write(img, "png", f);
			File fff=new File(path+"OUTPUT.jpg");
			if(fff.exists()){
				fff.delete();
			}
			f.renameTo(fff);
			JOptionPane.showMessageDialog(null, "Saved !!!!!");
		
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Could not save the image!!");
		}
	}
	
	
	
	
	
}
