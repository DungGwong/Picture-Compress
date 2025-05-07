package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NumberFrame extends JFrame {
    private ArrayList<SizeChangeListener> listeners = new ArrayList<>();
    JTextField w;
    JTextField h;
    JLabel wt;
    JLabel ht;
    private int WIDTH;
    private int HEIGHT;
    int strH;
    int strW;
    JLabel background;

    public NumberFrame(int width,int height){
        setTitle("目标尺寸");
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        w = new JTextField();
        h = new JTextField();
        w.setFont(new Font("黑体", Font.BOLD, 24));
        h.setFont(new Font("黑体", Font.BOLD, 24));
        w.setBounds(70,20,200,35);
        h.setBounds(70,70,200,35);
        wt = new JLabel("宽：");
        ht = new JLabel("长：");
        wt.setBounds(20,20,200,40);
        ht.setBounds(20,70,200,40);
        wt.setFont(new Font("黑体", Font.BOLD, 30));
        wt.setForeground(Color.WHITE); // 设置文字颜色（可选）
        ht.setFont(new Font("黑体", Font.BOLD, 30));
        ht.setForeground(Color.WHITE); // 设置文字颜色（可选）
        add(w);
        add(h);
        add(wt);
        add(ht);
        addBot();
        Image image = new ImageIcon("..\\PROJECT\\RelatedPicture\\NumberBack.png").getImage();
        image = image.getScaledInstance(1100, 810, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        this.background = new JLabel(icon);
        this.background.setSize(1100, 810);
        this.background.setLocation(0, 0);
        add(background);
    }
    public void addBot(){
        File imageFile = new File("..\\PROJECT\\RelatedPicture\\NumberButton.png");
        //System.out.println(imageFile.getAbsolutePath()); debug用这条程序
        Image image = null;
        try {
            image = ImageIO.read(imageFile).getScaledInstance(220, 100, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon iconDeal = new ImageIcon(image);
        JButton as = new JButton();
        as.setBorderPainted(false);
        as.setOpaque(false);
        as.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
        as.setIcon(iconDeal);
        JLabel label = new JLabel("确认操作");
        label.setFont(new Font("黑体", Font.BOLD, 30));
        label.setForeground(Color.WHITE); // 设置文字颜色（可选）
        label.setBounds(0, 0, 170, 75);
        as.setBounds(50, 110, 190, 90);
        as.add(label);
        as.addActionListener((e) -> {
            //注意，这里是图片的宽和长，如果顺序有变动，到时候再调
            strW = Integer.parseInt(w.getText());
            strH = Integer.parseInt(h.getText());
            notifyListeners();
            setVisible(false);
        });
        add(as);
    }
    public void addSizeChangeListener(SizeChangeListener listener) {
        listeners.add(listener);
    }
    public void notifyListeners() {
        for (SizeChangeListener listener : listeners) {
            listener.onSizeChanged(strW,strH);
        }
    }
    public int getStrW() {
        return strW;
    }
    public void setStrW(int strW) {
        this.strW = strW;
    }
    public int getStrH() {
        return strH;
    }
    public void setStrH(int strH) {
        this.strH = strH;
    }
}
