package Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class InPanel extends JPanel {
    JPanel jp = new JPanel();
   // private BufferedImage bac;
    private JTextField name = new JTextField("Админ");
    private JTextField pass = new JTextField("12345678");;
    private JButton next = new JButton("Войти");

    {

        User u = new User(name.getText(),0);
       // this.setLayout(new BorderLayout());
        jp.setLayout( new GridLayout(3, 1,0,100));
        this.add(jp);

        name.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE));
        pass.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.WHITE));

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                u.setName(name.getText());
                u.setPassword(pass.getText());

                String ret;
                /////////////////////////////////////////////  подключение к серверу //////////////////////

                try {
                    Socket s = new Socket("192.168.0.20",50001);

                    DataOutputStream serOut = new DataOutputStream(s.getOutputStream());
                    DataInputStream serIn = new DataInputStream(s.getInputStream());
                    System.out.println(u.getName() );
                    System.out.println(u.getPassword());

                    serOut.writeUTF(u.getName());
                    System.out.println("::::::::::::::::::::::::::::::::::");

                    serOut.writeUTF(u.getPassword());
                    System.out.println("::::::::::::::::::::::::::::::::::");

                    ret = serIn.readUTF();
                    System.out.println(ret);
                    System.out.println("::::::::::::::::::::::::::::::::::");
                    if(!ret.equals("GOOD")){
                        throw new Exception("Не правельный пороль");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
                ///////////////////////////////////////////////////////////////////////////////
                jp.removeAll();
                jp.revalidate();
                MainForm.setRP(new MainChat(u).getRootPanel());

            }
        });

        name.setForeground(Color.WHITE);
        pass.setForeground(Color.WHITE);
        next.setForeground(Color.WHITE);
        next.setBackground(Color.BLUE);

        name.setFont(new Font("Dialog", Font.PLAIN, 18));
        pass.setFont(new Font("Dialog", Font.PLAIN, 18));
        next.setFont(new Font("Dialog", Font.PLAIN, 20));

        name.setPreferredSize(new Dimension(350,60));
        pass.setPreferredSize(new Dimension(350,60));
        next.setPreferredSize(new Dimension(350,60));

        name.setOpaque(false);
        pass.setOpaque(false);
        jp.add(name);
        jp.add(pass);
        jp.add(next);
        jp.setOpaque(false);
        jp.setVisible(true);
        super.setPreferredSize(new Dimension(700,400));
        this.setOpaque(false);
        this.setVisible(true);


    }


}
