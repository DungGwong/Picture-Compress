package UI;
import hundred.Delete;
import hundred.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

//将map[][]转换成图片
public class PicturePaint extends JComponent implements Serializable{
    public BufferedImage getIcon() {
        return icon;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    BufferedImage icon;
    public static int rw,rh;
    public hundred.Map[][] MAP = new Map[1005][1005];
    public PicturePaint(hundred.Map[][] mp,int w,int h) {
        rw=w;
        rh=h;
        for (int i = 0;i < h;i++){
            for (int j = 0;j < w;j++){
                this.MAP[j][i]=new hundred.Map();
                this.MAP[j][i] = mp[j][i];
            }
        }
        //this.MAP = map;
        icon = showPic(MAP);
    }
//    private ImageIcon showPic(hundred.Map[][] mp){
//        if (mp == null) {
//            throw new IllegalArgumentException("Input array is null");
//        }
//        int wid = mp.length;
//        BufferedImage ans = new BufferedImage(wid, (wid > 0 ? mp[0].length : 0), BufferedImage.TYPE_BYTE_GRAY);
//        for (int i = 0; i < wid; i++) {
//            if (mp[i] == null) {
//                throw new IllegalArgumentException("Row " + i + " is null");
//            }
//            for (int j = 0; j < mp[i].length; j++) {
//                if (mp[i][j] == null) {
//                    throw new IllegalArgumentException("Element at position [" + i + "][" + j + "] is null");
//                }
//                int rgb = mp[i][j].getRgb();
//                ans.setRGB(i, j, rgb);
//            }
//        }
//        return new ImageIcon(ans);
//    }
    private BufferedImage showPic(hundred.Map[][] mp){
        BufferedImage ans=new BufferedImage(rw,rh,BufferedImage.TYPE_INT_RGB);
        for(int i=0;i<rh;i++) {
            for(int j=0;j<rw;j++) {
                int rgb=mp[j][i].getRgb();
                ans.setRGB(j,i,rgb);
            }
        }
        //Map类预期包含rgb参数，该方法应当实现将Map[][]转换成ImageIcon,该段方法留空回头写
        return ans;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (icon != null) {
            g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
