package Forms;

import com.sun.deploy.util.BlackList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MainChat extends JPanel {
    static int count ;
    User user ;
    JPanel jpNorth = new JPanel();
    public JPanel jpCenter = new JPanel();
    JPanel jpSouth = new JPanel();
    JPanel jpEast = new JPanel();
    JPanel jpWest = new JPanel();
    JLabel nameUser ;
    JButton bSettings = new JButton();
    JButton bExit = new JButton();
    MainChat(User u){
        //user = u;
        nameUser = new JLabel(u.getName());
        count = 0;
        Dimension dm = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());

        super.setLayout(new BorderLayout());
        super.add(jpCenter);
        super.add(jpNorth, BorderLayout.NORTH);
        super.add(jpWest,BorderLayout.WEST);
        super.add(jpSouth, BorderLayout.SOUTH);
        super.add(jpEast, BorderLayout.EAST);




        //======================= NORTH============================//
        jpNorth.setLayout(new BorderLayout());
        jpNorth.add(nameUser,BorderLayout.EAST);
        jpNorth.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(253,253,253)));
        nameUser.setForeground(Color.WHITE);
        nameUser.setFont(new Font("Dialog", Font.PLAIN, 20));
        nameUser.setPreferredSize(new Dimension(500,60));
        nameUser.setHorizontalAlignment(JLabel.RIGHT);
        nameUser.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

        //======================= WEST============================//
        jpWest.setPreferredSize(new Dimension(250,240));
       // jpWest.
        jpWest.setBorder(BorderFactory.createEmptyBorder());

        try {
            Socket s = new Socket("192.168.0.20",50002);

            DataOutputStream serOut = new DataOutputStream(s.getOutputStream());
            DataInputStream serIn = new DataInputStream(s.getInputStream());

            int i = serIn.readInt();
            int[] id = new int[i];
            String names[] = new String[i];
            for(int j = 0 ; j < i; j++){
                names[j]= serIn.readUTF();
                id[j] = serIn.readInt();
            }



            User[] users = new User[names.length];
            for(int k =0; k< names.length;k++) {
                if( names[k].equals(u.getName())) {
                    u.setId(id[k]);
                }
                users[k]= new User(names[k], id[k] );
            }



            for (int k =0; k< names.length;k++) {
                
                if(!names[k].equals( u.getName())) {
                    jpWest.add(new Contact(users[k], jpCenter,u));
                }
            }

            jpCenter.setLayout(new BorderLayout());
          //  jpCenter.add(new DialogPane());

        } catch (IOException e) {
            e.printStackTrace();
        }

        //======================= CENTER============================//
        //jpCenter.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));
        //jpCenter.setLayout(new BorderLayout());
       // jpCenter.add(dps[0]);

        //======================= SOUTH============================//
       // jpSouth.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GREEN));
        jpSouth.setLayout(new BorderLayout());
        //jpSouth.setBounds(10,5,10,5);
        //bSettings.setBorder(null);

//        bSettings.setContentAreaFilled(false);
//        bSettings.setBorderPainted(false);
//        bSettings.setIcon(new ImageIcon("lib/settings.png"));
//        bSettings.setPreferredSize(new Dimension(32,32));
//        bSettings.setOpaque(false);
        //bSettings.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
        //bExit.setBorder(null);
        bExit.setContentAreaFilled(false);
        bExit.setBorderPainted(false);
        bExit.setIcon(new ImageIcon("lib/exit.png"));
        bExit.setPreferredSize(new Dimension(32,32));
      //  jpSouth.add(bSettings,BorderLayout.WEST);
        jpSouth.add(bExit,BorderLayout.EAST);
        //======================= East============================//
        //jpEast.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.red));
        /*JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(20,200));
        scrollPane.setPreferredSize(new Dimension(500,200));
        //scrollPane.setViewportView(list);
        //list.setLayoutOrientation(JList.VERTICAL);
        jpEast.add(scrollPane);
       // jpEast.add(new Scrollbar());*/
        jpEast.setPreferredSize(new Dimension(0,0));

        super.setOpaque(false);
        jpCenter.setOpaque(false);
        jpNorth.setOpaque(false);
        jpSouth.setOpaque(false);
        jpEast.setOpaque(false);
        jpWest.setOpaque(false);



        super.setVisible(true);
        super.revalidate();



    }

    JPanel getRootPanel(){
        return this;
    }
}
