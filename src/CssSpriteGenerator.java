// SpriteGenerator - a simple java program for creating vertical sprites from a folder of PNG images.
//
// The software is released free of any copyright of license restrictions.
// Modified by Richar Muñico Samaniego (granlinux@gmail.com) April 26 2013
// originally by Peter Moberg (http://sourcecodebean.com/archives/a-simple-image-sprite-generator-in-java/1065) April 2011
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
//end imports

public class CssSpriteGenerator extends JFrame implements ActionListener {
    
    BufferedImage sprite = null;
    public void makeSprite(){
        String imagePath = txt_dir.getText();
        Integer margin = Integer.parseInt(spn_space.getValue().toString());
        String outputFile = txt_ima.getText();
        
        File[] files;
        
        if(chooser_dir.getSelectedFile().isDirectory()){
            File imageFolder = new File(imagePath);
            files = imageFolder.listFiles();
        }else{
            files = chooser_dir.getSelectedFiles();
        }

        // Read images
        ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
        
        String css = "";
        // Find max width and total height
        int maxWidth = 0;
        int maxHeight = 0;
        int totalHeight = 0;        
        int totalWidth = 0;        
        
        try{
            for (File f : files) {
                if (f.isFile()) {
                    String fileName = f.getName();
                    String ext = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());

                    if (ext.equals("png")) {                                               
                        BufferedImage io = ImageIO.read(f);

                        
                        
                        if (horizontal.isSelected()) {
                            totalWidth += io.getWidth() + margin;
                            if (io.getHeight() > maxHeight){
                                maxHeight = io.getHeight(); 
                            }                       
                            css += fileName+" {background: transparent url('"+outputFile+"') 0px "+totalWidth+";}\n";                                               
                        }else if(vertical.isSelected()){
                            totalHeight += io.getHeight() + margin;
                            if (io.getWidth() > maxWidth){
                                maxWidth = io.getWidth(); 
                            }                       
                            css += fileName+" {background: transparent url('"+outputFile+"') 0px "+totalHeight+";}\n";                                               
                        }else{
                        
                        }
                        

                        imageList.add(io);
                    }
                }
            }
        }catch(Exception e){ }
        if (vertical.isSelected()) {
            lbl_status.setText(String.format("Number of images: %s, total height: %spx, width: %spx%n", imageList.size(), totalHeight, maxWidth));
            sprite = new BufferedImage(maxWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
            outputFile = "V"+outputFile;
        }else if(horizontal.isSelected()){
            lbl_status.setText(String.format("Number of images: %s, total width: %spx, height: %spx%n", imageList.size(), totalWidth, maxHeight));
            sprite = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
            outputFile = "H"+outputFile;
        }
        
        lbl_status2.setText(String.format("Writing sprite: %s%n", outputFile));
        
        // Create the actual sprite


        int currentY = 0;
        int currentX = 0;
        Graphics g = sprite.getGraphics();
        
        for (BufferedImage image : imageList) {
            
            
        Graphics2D g2 = image.createGraphics();
        g2.setClip(g.getClip());
        super.paint(g2);
        g2.dispose();
                

        g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.2f));
        g2.drawImage(image, 0, 0, null);
            
            
            
            
            
            
            g.drawImage(image, currentX, currentY, null);
            
            
            if(horizontal.isSelected()){
                currentX += image.getWidth() + margin;
            }else if(vertical.isSelected()){
                currentY += image.getHeight() + margin;
            }else{
                
            }        


        }
        
        
        
        try{
            ImageIO.write(sprite, "png", new File(outputFile));
            createCssFile(css,outputFile);
        }catch(Exception e){ }
    }
    
    public void createCssFile(String css, String outputFile){
        String imgName = outputFile.substring(0,outputFile.lastIndexOf("."));
        String sFichero = "cssSprite."+imgName+".css";             
        
        File fichero = new File(sFichero);

        if (fichero.exists()) {
            JOptionPane.showMessageDialog(null, "El fichero " + sFichero + " ya existe");            
        } else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));               
                bw.write(css);               
                bw.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        
        
        
        
        
    }
    public static void main(String[] args) throws IOException {
        new CssSpriteGenerator().__init__();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        JComponent c = (JComponent)e.getSource();
        if(c == btn_dir){
            int opc = chooser_dir.showOpenDialog(this);
            if(opc == JFileChooser.APPROVE_OPTION){
                txt_dir.setText(chooser_dir.getSelectedFile().toString());
            }
        }else if(c == btn_ima){
            int opc = chooser_ima.showSaveDialog(this);
            if(opc == JFileChooser.APPROVE_OPTION){
                txt_ima.setText(chooser_ima.getSelectedFile().toString());
            }
        }else if(c == btn_crt){
            if(checkValues()){
                makeSprite();
                lbl_spriteImage.setIcon(new ImageIcon(sprite));
            }
        }else if(c == btn_new){
            txt_dir.setText("");
            txt_ima.setText("");
            spn_space.setValue(0);

            options.clearSelection();
            grid.setSelected(true);
        }else if(c == btn_quit){
            dispose();
        }
    }
    public boolean checkValues(){
        boolean chk = false;
        if(txt_dir.getText().isEmpty() || txt_ima.getText().isEmpty() || spn_space.getValue().toString().isEmpty()){
            chk = false;
        }else{
            chk = true;
        }
        return chk;
    }

    public void __init__(){
        setSize(800,600);
        setDefaultCloseOperation(3);
        setLayout(null);
        setVisible(true);

        lbl_title.setBounds(190,5,400,30);
        lbl_title.setForeground(Color.red);
        lbl_title.setHorizontalAlignment(0);
        lbl_title.setFont(new Font("Cambria",1,30));
        btn_crt.setBounds(245,200,300,50);
        btn_crt.setFont(new Font("Cambria",1,20));
        btn_crt.setForeground(Color.magenta);

        btn_new.setBounds(150,210,90,30);
        btn_quit.setBounds(550,210,90,30);
        add(btn_new);
        add(btn_quit);
        btn_new.addActionListener(this);
        btn_quit.addActionListener(this);

        lbl_dir.setBounds(15,50,200,30);
        lbl_ima.setBounds(15,85,2000,30);
        lbl_space.setBounds(15,120,200,30);
        lbl_dir.setFont(fnt_label);
        lbl_ima.setFont(fnt_label);
        lbl_space.setFont(fnt_label);
        lbl_dir.setForeground(Color.blue);
        lbl_ima.setForeground(Color.blue);
        lbl_space.setForeground(Color.blue);

        lbl_status.setBounds(15,215,730,20);
        lbl_status2.setBounds(15,240,730,20);
        lbl_status.setForeground(Color.red);
        lbl_status2.setForeground(Color.red);


        scroll_image.setBounds(15,265,763,305);

        txt_dir.setBounds(220,50,478,30);
        txt_ima.setBounds(220,85,478,30);
        spn_space.setBounds(220,120,478,30);
        
        horizontal.setBounds(200,160,100,30);
        grid.setBounds(380,160,100,30);
        vertical.setBounds(500,160,100,30);
        
        txt_dir.setFont(new Font("Cambria",2,15));
        txt_ima.setFont(new Font("Cambria",2,15));
        spn_space.setFont(new Font("Cambria",2,25));
        txt_dir.setForeground(Color.magenta);
        txt_ima.setForeground(Color.magenta);
        spn_space.setForeground(Color.magenta);

        btn_dir.setBounds(700,50,78,30);
        btn_ima.setBounds(700,85,78,30);
        btn_dir.setFont(new Font("Cambria",1,30));
        btn_ima.setFont(new Font("Cambria",1,30));
        btn_dir.setVerticalAlignment(3);
        btn_ima.setVerticalAlignment(3);
        btn_dir.setForeground(Color.magenta);
        btn_ima.setForeground(Color.magenta);

        btn_new.setMnemonic('n');
        btn_crt.setMnemonic('c');
        btn_quit.setMnemonic('q');

        scroll_image.setViewportView(lbl_spriteImage);
        
        options.add(horizontal);
        options.add(vertical);
        options.add(grid);


        add(btn_crt);
        add(lbl_title);
        add(scroll_image);
        add(lbl_status);
        add(lbl_status2);

        add(lbl_dir);
        add(lbl_ima);
        add(lbl_space);

        add(txt_dir);
        add(txt_ima);
        add(spn_space);

        add(btn_dir);
        add(btn_ima);
        
        add(horizontal);
        add(vertical);
        add(grid);

        chooser_dir.setFileSelectionMode(2);
        chooser_dir.setMultiSelectionEnabled(true);
        
//        chooser_dir.setCurrentDirectory(new File("images"));
        chooser_dir.setSelectedFile(new File("images"));

        btn_crt.addActionListener(this);
        btn_crt.setFocusPainted(false);
        btn_dir.addActionListener(this);
        btn_ima.addActionListener(this);
        horizontal.addActionListener(this);
        
        

    }
    JLabel lbl_title = new JLabel("CSS Sprite Generator");

    JLabel lbl_dir = new JLabel("Directory with Images: ");
    JLabel lbl_ima = new JLabel("Name new Sprite Image: ");
    JLabel lbl_space = new JLabel("Space between Images: ");
    JLabel lbl_spriteImage = new JLabel();
    JLabel lbl_status = new JLabel("...");
    JLabel lbl_status2 = new JLabel("...");
    Font fnt_label = new Font("Arial", 2, 16);

    JTextField txt_dir = new JTextField();
    JTextField txt_ima = new JTextField();
    JSpinner spn_space = new JSpinner();

    JButton btn_dir = new JButton("...");
    JButton btn_ima = new JButton("...");
    JButton btn_new = new JButton("New");
    JButton btn_quit = new JButton("Quit");

    JButton btn_crt = new JButton("Create Sprite Image");
    JFileChooser chooser_dir = new JFileChooser(".");
    JFileChooser chooser_ima = new JFileChooser(".");
    JScrollPane scroll_image = new JScrollPane();
    JRadioButton horizontal = new JRadioButton("Horizontal");
    JRadioButton vertical = new JRadioButton("Vertical");
    JRadioButton grid = new JRadioButton("Grid");
    ButtonGroup options = new ButtonGroup();
    
}

