package Forms;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Contact extends JPanel implements MouseListener, MouseMotionListener {
    DialogPane dp = new DialogPane();
    User I ;
    User out;
    JPanel jp ;
    boolean b = false;
    static int c = 0;
    Contact(User names, JPanel j, User Iid){
        I = new User(Iid.getName(),Iid.getId());
        out = new User(names.getName(),names.getId());
        jp = j;
        c++;
        JLabel name = new JLabel(names.getName());
        name.setHorizontalAlignment(SwingConstants.CENTER);
        super.setFocusable(false);
        super.setLayout(new BorderLayout());
        //name.setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.BLUE));
        setPreferredSize(new Dimension(250,60));
        super.setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        super.setBounds(0,0,0,0);
        name.setPreferredSize(new Dimension(250,60));
        super.add(name);

        addMouseListener( this);
        addMouseMotionListener( this);


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.setBackground(Color.BLUE);
        jp.removeAll();
        paintMessage();
        jp.add(dp);
        jp.revalidate();
        jp.repaint();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.setBackground(Color.BLUE);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

        if(b =true){
            super.setBackground(Color.BLUE);
            b= false;
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!b) {
            super.setBackground(Color.GRAY);
            super.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    void paintMessage(){
        try{
            Socket s = new Socket("192.168.0.20",50003);

            DataOutputStream serOut = new DataOutputStream(s.getOutputStream());
            DataInputStream serIn = new DataInputStream(s.getInputStream());

            serOut.writeInt(I.getId());
            serOut.writeInt(out.getId());


            int countMessage = serIn.readInt();
            String str[] = new String[countMessage];

            for(int i = 0 ; i < countMessage; i++){
                 str[i] = serIn.readUTF();
            }


            for(int i = 0;i < countMessage ; i++){
                int n = Integer.parseInt(str[i].substring(0,1)) ;
                String strOUT = str[i].substring(2,str[i].length());
                if(n == out.getId()){
                    dp.inMessage(strOUT);

                }
                if(n == I.getId()){
                    dp.outMessage(strOUT);

                }
            }

        }catch (Exception e){}

    }
}
