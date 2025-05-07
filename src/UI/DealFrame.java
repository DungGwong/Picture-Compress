package UI;
//那个操作继承的弄出来
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import hundred.*;
public class DealFrame extends JFrame implements Serializable,MouseListener,SizeChangeListener {
    private final int WIDTH;
    private final int HEIGHT;
    private JPanel panel;
    JTextField wf;
    JTextField hf;
    JLabel imageLabel = new JLabel();
    JLabel background = new JLabel();
    ImageIcon iconIn = new ImageIcon();
    BufferedImage iconUpload;
    ImageIcon picProduct = new ImageIcon();
    private static Set<Point> redPoints = new HashSet<>();
    private static Set<Point> greenPoints = new HashSet<>();
    private boolean isRed = false;
    Point centrePoint;
    //或许可以整一个可选择的circleRadius
    int circleRadius = 5;
    boolean isDragging = false;
    public DealFrame(int width, int height) {
        setTitle("图片变换");
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        addButton();
        addText();
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // 绘制红色圆形
                if (iconUpload != null) {
                    g2d.drawImage(iconUpload, 0, 0, this); // 假设图片从左上角开始绘制
                }

                for (Point p : redPoints) {
                        g2d.setColor(Color.RED);
                        g2d.fillOval(p.x - circleRadius, p.y - circleRadius, 2 * circleRadius, 2 * circleRadius);
                    }
                    // 绘制绿色圆形
                    for (Point p : greenPoints) {
                        g2d.setColor(Color.GREEN);
                        g2d.fillOval(p.x - circleRadius, p.y - circleRadius, 2 * circleRadius, 2 * circleRadius);
                    }
                }
        };
        //在这里调整画布的大小格局位置
        panel.setBounds(44, 68, 910, 560);
        panel.setLayout(null);
        add(panel);
        //这里可以插入背景图片,改整个frame大小的时候注意也要改这里的size
        Image image = new ImageIcon("..\\PROJECT\\RelatedPicture\\DealBack.jpg").getImage();
        image = image.getScaledInstance(1000, 700, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        this.background = new JLabel(icon);
        this.background.setSize(1000, 700);
        this.background.setLocation(0, 0);
        add(background);
        //关于画图的部分
        addRG();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = true;
                centrePoint = e.getPoint();
                System.out.println(centrePoint);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    centrePoint = e.getPoint();
                    for (int i = centrePoint.x - circleRadius; i <= centrePoint.x + circleRadius; i++) {
                        for (int j = centrePoint.y - circleRadius; j <= centrePoint.y + circleRadius; j++) {
                            checkAndAddPoint(new Point(i, j));
//                            System.out.println(greenPoints);
//                            System.out.println(redPoints);
                        }
                    }
                    panel.repaint(); // 刷新面板以显示新的圆形
                }
            }
        });
    }

public void addText(){
    wf = new JTextField();
    hf = new JTextField();
    wf.setFont(new Font("黑体", Font.BOLD, 24));
    hf.setFont(new Font("黑体", Font.BOLD, 24));
    wf.setBounds(250,17,150,35);
    hf.setBounds(500,17,150,35);
    JLabel wt = new JLabel("宽：");
    JLabel ht = new JLabel("长：");
    wt.setBounds(200,16,200,40);
    ht.setBounds(450,16,200,40);
    wt.setFont(new Font("黑体", Font.BOLD, 30));
    wt.setForeground(Color.WHITE); // 设置文字颜色（可选）
    ht.setFont(new Font("黑体", Font.BOLD, 30));
    ht.setForeground(Color.WHITE); // 设置文字颜色（可选）
    add(wf);
    add(hf);
    add(wt);
    add(ht);
}
    private void addButton() {//在主界面加入了需要的按钮
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();
        JButton button4 = new JButton();
        JButton button5 = new JButton();
        JButton button6 = new JButton();
        JButton clear = new JButton();
        //DealFrame里面按钮的底图
        File imageFile = new File("..\\PROJECT\\RelatedPicture\\NumberButton.png");
        //System.out.println(imageFile.getAbsolutePath()); debug用这条程序
        Image image = null;
        try {
            image = ImageIO.read(imageFile).getScaledInstance(220, 100, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = image.getScaledInstance(120,55,Image.SCALE_DEFAULT);
        ImageIcon iconDeal = new ImageIcon(image);
        {
            button1.setBorderPainted(false);
            button1.setOpaque(false);
            button1.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button1.setIcon(iconDeal);
            JLabel label = new JLabel("更改尺寸");
            label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button1.setBounds(HEIGHT - 5, HEIGHT / 10 + 33, 200, 100);
            button1.add(label);
            button1.setLocation(190, HEIGHT-95);
            button1.setSize(120,55);
            button1.addActionListener((e) -> {
                int w = Integer.parseInt(wf.getText());
                int h = Integer.parseInt(hf.getText());
                Delete.find_energy(iconUpload);
                Delete.del(w,h);
                Delete.insertSeam(w,h);
                //最后得出来的矩阵通过Picture类画图
                hundred.Map[][] map= new hundred.Map[1005][1005];
                for (int i = 0;i <w;i++){
                    for (int j=0;j<h;j++){
                        map[i][j]= Delete.rgetMap(i,j);
                    }
                }
                PicturePaint showPic = new PicturePaint(map,w,h);
                iconUpload = showPic.getIcon();
//                imageLabel.setIcon(null);
//                imageLabel.setVisible(false);
//                panel.removeAll();
//                panel.revalidate();
                panel.repaint();
//                imageLabel = new JLabel(picProduct);
//                imageLabel.setBounds(0, 0, picProduct.getIconWidth(), picProduct.getIconHeight());
//                panel.add(imageLabel);
//                imageLabel.setVisible(true);
//                panel.setVisible(true);
                //最后还要显示出来需要的picture在界面上
                //还要清除两个point列存储的内容
                redPoints.clear();
                greenPoints.clear();
            });
            add(button1);
        }
        {//
            button2.setBorderPainted(false);
            button2.setOpaque(false);
            button2.setContentAreaFilled(false);//以上三行为将按钮原本的东西抹掉
            button2.setIcon(iconDeal);
            JLabel label = new JLabel("清除图片");
            label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button2.setBounds(HEIGHT - 5, HEIGHT / 10 + 167, 200, 100);
            button2.add(label);
            button2.setLocation(710, HEIGHT-95);
            button2.setSize(120,55);
            button2.addActionListener((e) -> {
              iconUpload = null;
              panel.repaint();
            });
            add(button2);
        }
        {
            button3.setBorderPainted(false);
            button3.setOpaque(false);
            button3.setContentAreaFilled(false);
            button3.setIcon(iconDeal);
            JLabel label = new JLabel("上传图片");
            label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button3.setBounds(HEIGHT - 5, HEIGHT / 10 + 304, 200, 100);
            button3.add(label);
            button3.setLocation(320, HEIGHT-95);
            button3.setSize(120,55);
            add(button3);
            button3.addActionListener(e -> {
                try {
                    uploadImage();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        {
            button4.setBorderPainted(false);
            button4.setOpaque(false);
            button4.setContentAreaFilled(false);
            button4.setIcon(iconDeal);
            JLabel label = new JLabel("保存图片");
            label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button4.setBounds(HEIGHT - 5, HEIGHT / 10 + 439, 200, 100);
            button4.add(label);
            button4.setLocation(450, HEIGHT-95);
            button4.setSize(120,55);
            add(button4);
            button4.addActionListener(e -> {
                saveImageToProjectFolder(iconUpload, "Product");
            });
        }
        {//用于加载图片
            button5.setBorderPainted(false);
            button5.setOpaque(false);
            button5.setContentAreaFilled(false);
            button5.setIcon(iconDeal);
            JLabel label = new JLabel("退出程序");
            label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button5.setBounds(HEIGHT - 5, HEIGHT / 10 + 575, 200, 100);
            button5.add(label);
            button5.setLocation(580, HEIGHT-95);
            button5.setSize(120,55);
            add(button5);
            button5.addActionListener(e -> {
                setVisible(false);
            });
        }
        {//用于扩展图片
            button6.setBorderPainted(false);
            button6.setOpaque(false);
            button6.setContentAreaFilled(false);
            button6.setIcon(iconDeal);
            JLabel label = new JLabel("清除画笔");
            label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
            label.setForeground(Color.WHITE); // 设置文字颜色（可选）
            label.setBounds(0, 0, 200, 100);
            button6.setBounds(HEIGHT - 5, HEIGHT / 10 + 575, 200, 100);
            button6.add(label);
            button6.setLocation(840, HEIGHT-95);
            button6.setSize(120,55);
            add(button6);
            button6.addActionListener(e -> {
                greenPoints.clear();
                redPoints.clear();
                panel.repaint();
                System.out.println(greenPoints);
                System.out.println(redPoints);
            });
        }
    }

    private void uploadImage() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "图片文件", "jpg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            iconUpload = ImageIO.read(selectedFile);
            panel.repaint();
//            iconIn = new ImageIcon(selectedFile.getAbsolutePath());
//            imageLabel = new JLabel(iconIn);
//            imageLabel.setOpaque(false);
//            imageLabel.setBounds(0, 0, iconIn.getIconWidth(), iconIn.getIconHeight());
//            panel.add(imageLabel);
//            panel.revalidate();
//            panel.repaint();
//            imageLabel.setVisible(true);
//            panel.setVisible(true);
        }
    }

    public void addRG(){
        JButton red = new JButton();
        red.setBounds(700,20,30,30);
        red.setSize(30,30);
        red.setOpaque(true);
        red.setBackground(Color.RED);
        red.setBorder(BorderFactory.createEmptyBorder());
        red.addActionListener(e -> isRed = true);
        JButton green = new JButton();
        green.setBounds(750,20,30,30);
        green.setSize(30,30);
        green.setOpaque(true);
        green.setBackground(Color.GREEN);
        green.setBorder(BorderFactory.createEmptyBorder());
        green.addActionListener(e -> isRed = false);
        add(red);
        add(green);
    }

    private boolean isPointInCircle(Point p, Point center, int radius) {
        int dx = p.x - center.x;
        int dy = p.y - center.y;
        return dx * dx + dy * dy <= radius * radius;
    }

    private void checkAndAddPoint(Point p) {
        // 假设这里有一个方法来判断点p是否在当前绘制的圆内
        //在这个地方减去了相对坐标，存入点方便一点
        boolean isInCircle = isPointInCircle(p,centrePoint, circleRadius);
        if (isInCircle && isRed == true){
            redPoints.add(p);

        } else if (isInCircle && isRed == false) {
            greenPoints.add(p);
        }
    }

    public static void saveImageToProjectFolder(BufferedImage image, String folderName) {
        String projectPath = System.getProperty("user.dir");
        File productFolder = new File(projectPath, folderName);
        if (!productFolder.exists()) {
            productFolder.mkdirs();
        }
        File[] pngFiles = productFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });

        int fileCount = (pngFiles != null) ? pngFiles.length : 0;
        // 构建新的文件名，格式为(数量+1).png
        String fileName = String.format("%d.png", fileCount + 1);
        File outputFile = new File(productFolder, fileName + "product");
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
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
    @Override
    public void onSizeChanged(int a, int b) {

    }
}

