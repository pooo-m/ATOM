package Forms;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Message extends JPanel {
    final static int INC = 0;
    final static int OUT = 1;
    private JPanel messagePane;
    private JTextArea messageText;
    private JPanel datePane;
    private JTextField dateText;

    public Message(int type) {
        setOpaque(false);
        setLayout(new BorderLayout());

        if(type == 0) {
            messagePane = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        BufferedImage top = ImageIO.read(new File(
                                "lib/message-in-top.png"
                        ));
                        g.drawImage(top, 35, 0, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        BufferedImage bottom = ImageIO.read(new File(
                                "lib/message-in-bottom.png"
                        ));
                        g.drawImage(
                                bottom,
                                35,
                                this.getHeight() - 8,
                                this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        BufferedImage left = ImageIO.read(new File(
                                "lib/message-in-left.png"
                        ));
                        g.drawImage(left,
                                29,
                                this.getHeight() / 2 - 6,
                                this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        } else if(type == 1) {
            messagePane = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        BufferedImage top = ImageIO.read(new File(
                                "lib/message-out-top.png"
                        ));
                        g.drawImage(top,
                                this.getWidth() - 35 - 307,
                                0,
                                this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        BufferedImage bottom = ImageIO.read(new File(
                                "lib/message-out-bottom.png"
                        ));
                        g.drawImage(
                                bottom,
                                this.getWidth() - 35 - 307,
                                this.getHeight() - 8,
                                this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        BufferedImage left = ImageIO.read(new File(
                                "lib/message-out-right.png"
                        ));
                        g.drawImage(left,
                                this.getWidth() - 35,
                                this.getHeight() / 2 - 6,
                                this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        messagePane.setOpaque(false);
        add(messagePane, BorderLayout.NORTH);

        messageText = new JTextArea(1, 22);
        if(type == 0) {
            messageText.setBackground(new Color(
                    0,
                    169,
                    213
            ));
        } else if(type == 1) {
            messageText.setBackground(new Color(
                    70,
                    73,
                    162
            ));
        }
        try {
            messageText.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    new FileInputStream(new File(
                            "lib/OpenSansRegular.ttf")))
                    .deriveFont(Font.PLAIN, 14));
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageText.setForeground(Color.WHITE);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messagePane.add(messageText);

        datePane = new JPanel();
        datePane.setOpaque(false);
        datePane.setPreferredSize(new Dimension(
                getWidth(),
                35
        ));
        add(datePane, BorderLayout.SOUTH);

        dateText = new JTextField();
        dateText.setOpaque(false);
        dateText.setBorder(null);
        dateText.setPreferredSize(new Dimension(
                150,
                20
        ));
        try {
            dateText.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    new FileInputStream(new File(
                            "lib/OpenSansRegular.ttf")))
                    .deriveFont(Font.PLAIN, 12));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dateText.setForeground(new Color(
                130,
                130,
                130
        ));
        datePane.add(dateText);
    }

    public JTextArea getMessageText() {
        return messageText;
    }

    public JTextField getDateText() {
        return dateText;
    }

    public static Message createMessageIn(String text, String date) {
        Message message = new Message(Message.INC);
        message.messageText.setMargin(new Insets(8, 11, 8, 11));
        message.messageText.setText(text);
        message.dateText.setHorizontalAlignment(JTextField.TRAILING);
        message.dateText.setText(date);
        message.messagePane.setLayout(new FlowLayout(FlowLayout.LEFT, 35, 7));
        message.datePane.setLayout(new FlowLayout(FlowLayout.LEFT, 185, 5));
        message.setMaximumSize(new Dimension(
                Short.MAX_VALUE,
                message.getHeight()
        ));
        return message;
    }

    public static Message createMessageOut(String text, String date) {
        Message message = new Message(Message.OUT);
        message.messageText.setMargin(new Insets(8, 10, 8, 11));
        message.messageText.setText(text);
        message.dateText.setText(date);
        message.messagePane.setLayout(new FlowLayout(FlowLayout.RIGHT, 35, 7));
        message.datePane.setLayout(new FlowLayout(FlowLayout.RIGHT, 185, 5));
        message.setMaximumSize(new Dimension(
                Short.MAX_VALUE,
                message.getHeight()
        ));
        return message;
    }
}

