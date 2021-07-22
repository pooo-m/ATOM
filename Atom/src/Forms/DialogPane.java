package Forms;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogPane extends JPanel {

    private JPanel userPane;
    private JPanel userInfoPane;
    private AdjustmentListener downScroller;
    private JScrollPane dialogScroll;
    private JPanel dialogPane;
    private JPanel messageSendPane;
    private JTextArea messageSendText;
    private JButton messageSendButton;



    public DialogPane() {

        setLayout(new BorderLayout());

        userPane = new JPanel();
        userPane.setBackground(Color.WHITE);
        userPane.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(236,236,236)));

        userPane.setLayout(new BoxLayout(userPane, BoxLayout.X_AXIS));
        userPane.setPreferredSize(new Dimension(Short.MAX_VALUE,47));
        add(userPane, BorderLayout.NORTH);

       /* userInfoPane = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 9));
        userInfoPane.setOpaque(false);
        userPane.add(userInfoPane);*/


        dialogPane = new JPanel();
        dialogPane.setBackground(Color.WHITE);
        dialogPane.setLayout(new BoxLayout(dialogPane, BoxLayout.Y_AXIS));
        dialogPane.add(Box.createVerticalGlue());

        downScroller = new AdjustmentListener()  {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                Adjustable adjustable = adjustmentEvent.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                dialogScroll.getVerticalScrollBar().removeAdjustmentListener(this);
            }
        };

        dialogScroll = new JScrollPane(
                dialogPane,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER

        );
        dialogScroll.getVerticalScrollBar().setUnitIncrement(15);
        dialogScroll.setBorder(null);
        dialogScroll.setComponentZOrder(dialogScroll.getVerticalScrollBar(), 0);
        dialogScroll.setComponentZOrder(dialogScroll.getViewport(), 1);
        dialogScroll.getVerticalScrollBar().setOpaque(false);

        dialogScroll.setLayout(new ScrollPaneLayout() {
            @Override
            public void layoutContainer(Container parent) {
                JScrollPane scrollPane = (JScrollPane)parent;

                Rectangle availR = scrollPane.getBounds();
                availR.x = availR.y = 0;

                Insets insets = parent.getInsets();
                availR.x = insets.left;
                availR.y = insets.top;
                availR.width  -= insets.left + insets.right;
                availR.height -= insets.top  + insets.bottom;

                Rectangle vsbR = new Rectangle();
                vsbR.width  = 12;
                vsbR.height = availR.height;
                vsbR.x = availR.x + availR.width - vsbR.width;
                vsbR.y = availR.y;

                if(viewport != null) {
                    viewport.setBounds(availR);
                }
                if(vsb != null) {
                    vsb.setVisible(true);
                    vsb.setBounds(vsbR);
                }
            }
        });

        dialogScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            private final Dimension d = new Dimension();
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                JScrollBar sb = (JScrollBar)c;
                Color color = new Color(160,160,160, 150);
                g2.setPaint(color);
                g2.fillRoundRect(r.x,r.y,r.width,r.height,12,12);
                g2.dispose();
            }
            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        });

        add(dialogScroll, BorderLayout.CENTER);

        messageSendPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        messageSendPane.setBackground(Color.WHITE);
        add(messageSendPane, BorderLayout.SOUTH);

        messageSendText = new JTextArea(1, 28) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(235,235,235));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                super.paintComponent(g);
            }
        };
        messageSendText.setOpaque(false);
        messageSendText.setMargin(new Insets(13, 20, 13 ,20));
        messageSendText.setLineWrap(true);
        messageSendText.setWrapStyleWord(true);
        try {
            messageSendText.setFont(Font.createFont(
                    Font.TRUETYPE_FONT,
                    new FileInputStream(new File(
                            "lib/OpenSansRegular.ttf")))
                    .deriveFont(Font.PLAIN, 16));
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageSendPane.add(messageSendText);

        messageSendButton = new JButton();
        messageSendButton.setContentAreaFilled(false);
        messageSendButton.setBorderPainted(false);
        messageSendButton.setIcon(new ImageIcon("lib/button-send.png"));
        messageSendButton.setPreferredSize(new Dimension(          63,             46        ));
        messageSendPane.add(messageSendButton);
        messageSendButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(messageSendText.getText().length() >= 1) {
                    Message messageOut = Message.createMessageOut(
                            messageSendText.getText(),
                            new SimpleDateFormat("dd.MM.YY в HH:mm").format(new Date()));
                    dialogPane.add(messageOut);
                    messageSendText.setText("");
                    repaint();
                    revalidate();
                }
            }


            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
       // createTestDialog();
    }

    public JPanel getUserPane() {
        return userPane;
    }

    public JPanel getUserInfoPane() {
        return userInfoPane;
    }


    public AdjustmentListener getDownScroller() {
        return downScroller;
    }

    public JScrollPane getDialogScroll() {
        return dialogScroll;
    }

    public JPanel getDialogPane() {
        return dialogPane;
    }

    public JPanel getMessageSendPane() {
        return messageSendPane;
    }

    public JTextArea getMessageSendText() {
        return messageSendText;
    }
/*
    public JLabel getMessageSendButton() {
        return messageSendButton;
    }*/

    public void inMessage(String s){
        if(s.length() >= 1) {
            Message messageIN = Message.createMessageIn(s ,new SimpleDateFormat("dd.MM.YY в HH:mm").format(new Date()));
            dialogPane.add(messageIN);
           // messageSendText.setText("");
            repaint();
            revalidate();
        }
    }

    public void outMessage(String s){
        if(s.length() >= 1) {
            Message messageOut = Message.createMessageOut(s ,new SimpleDateFormat("dd.MM.YY в HH:mm").format(new Date()));
            dialogPane.add(messageOut);
            // messageSendText.setText("");
            repaint();
            revalidate();
        }
    }

    public void createTestDialog() {
        Message messageIn = Message.createMessageIn(
                "Соня привет",
                new SimpleDateFormat("dd.MM.YY в HH:mm").format(new Date()));
        dialogPane.add(messageIn);

        Message messageOut = Message.createMessageOut(
                "Привет саша",
                new SimpleDateFormat("dd.MM.YY в HH:mm").format(new Date()));
        dialogPane.add(messageOut);
    }
}
