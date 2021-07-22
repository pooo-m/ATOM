package Forms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MainForm extends JFrame {

    private static JPanel rootPanel = new JPanel();

    private  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
   // private Dimension dm = super.getSize();
    private  BufferedImage bac  = ImageIO.read(new File("lib/back.jpg"));;
    public MainForm() throws IOException {
        super("ATOM");
        super.setMinimumSize(new Dimension(900,600));
        super.setLayout(new BorderLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.setSize(900,600);
        super.setLocationRelativeTo(null);
       // rootPanel.setPreferredSize(new Dimension(200,200));
       // rootPanel.setLayout( new BorderLayout());

        createUIComponents(bac);
        //MainChat s = new MainChat();
        InPanel in = new InPanel();
        rootPanel.add(in);
        rootPanel.setOpaque(true);
       // rootPanel.setBackground(Color.RED);
        //rootPanel.setBackground(Color.RED);
        rootPanel.setPreferredSize(new Dimension(900,600));
        super.add(rootPanel);
        super.setVisible(true);
        super.revalidate();
        super.repaint();
        rootPanel.revalidate();
        rootPanel.repaint();

    }

    public static JPanel getRP(){
        return rootPanel;
    }


    public static void setRP(JPanel j){

        rootPanel.removeAll();
rootPanel.setLayout(new GridLayout());
        rootPanel.add(j);
        //rootPanel.setLayout(new BorderLayout());

        rootPanel.revalidate();
        rootPanel.repaint();

    }

   public void createUIComponents( BufferedImage img){
        rootPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(img,0,0,(int)dim.getWidth(),(int)dim.getHeight(), this);
            }
        };
   }





}
