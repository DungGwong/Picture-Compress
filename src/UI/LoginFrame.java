package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginFrame extends JFrame{
    private final int WIDTH;
    private final int HEIGTH;
    private JLabel background;
    JLabel BackIn;
    private CardLayout cardLayout;
    private JFrame cardFrame;
    public LoginFrame(int width, int height) {
        setTitle("Welcome!");
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        addButtonsIn();
        //此处为登录界面背景图片
        Image image = new ImageIcon("..\\PROJECT\\RelatedPicture\\NumberBack.png").getImage();
        image = image.getScaledInstance(700,460,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        BackIn = new JLabel(icon);
        BackIn.setSize(400,320);
        BackIn.setLocation(0, 0);
        background = BackIn;
        background.setVisible(true);
        add(background);
        setVisible(true);
    }
    private void addButtonsIn(){
        JButton button = new JButton();
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        //此处为按钮图片
        File imageFile = new File("..\\PROJECT\\RelatedPicture\\NumberButton.png");
        Image image = null;
        try {
            image = ImageIO.read(imageFile).getScaledInstance(220, 100, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon iconIn = new ImageIcon(image);
        {
            button1.setBorderPainted(false);
            button1.setOpaque(false);
            button1.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button1.setIcon(iconIn);
            JLabel label = new JLabel("退出程序");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button1.add(label);
            button1.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button1.setLocation(100,200);
            button1.setSize(200, 60);
            add(button1);
            button1.addActionListener(e ->setVisible(false));
        }
        {
            button2.setBorderPainted(false);
            button2.setOpaque(false);
            button2.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button2.setIcon(iconIn);
            JLabel label = new JLabel("进入程序");
            label.setFont(new Font("黑体", Font.BOLD, 30));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button2.add(label);
            button2.setBounds(HEIGTH - 10, HEIGTH / 10 + 80, 200, 100);
            button2.setLocation(100,80);
            button2.setSize(200,60);
            add(button2);
            button2.addActionListener(e -> {
                setVisible(false);
                DealFrame a = new DealFrame(1000,730);
            });
        }
    }
}