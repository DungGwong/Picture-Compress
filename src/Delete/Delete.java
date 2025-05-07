package hundred;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;
public class Delete {
    public static hundred.Map[][] map=new Map[1005][1005];//记得调整大小！
    public static int fdel_l(int wid, int hei) {
        double minn = MAXI;
        int tag = -1;
        for (int i = 0; i < wid; i++) {
            if (!usedSeams[hei - 1][i] && M_l[hei - 1][i] < minn) {
                tag = i;
                minn = M_l[hei - 1][i];
            }
        }
        return tag;
    }
    public static int fdel_r(int wid, int hei) {
        double minn = MAXI;
        int tag = -1;
        for (int i = 0; i < hei; i++) {
            if (!usedSeams[i][wid - 1] && M_r[i][wid - 1] < minn) {
                tag = i;
                minn = M_r[i][wid - 1];
            }
        }
        return tag;
    }
    public static boolean[][] usedSeams = new boolean[1005][1005]; // 假设这是足够大的尺寸
    public static void insertSeam(int m_wid,int m_hei) {
        while (rwid < m_wid) {
            check_Ml(rwid, rhei);
            int minSeamIndex = fdel_l(rwid, rhei);
            insertVerticalSeam(minSeamIndex);
            rwid++;
        }

        while (rhei < m_hei) {
            check_Mr(rwid, rhei);
            int minSeamIndex = fdel_r(rwid, rhei);
            insertHorizontalSeam(minSeamIndex);
            rhei++;
        }
    }
    public static void markSeamsUsed(int seamIndex, boolean isVertical) {
        if (isVertical) {
            for (int i = 0; i < rhei; i++) {
                usedSeams[i][seamIndex] = true; // 标记此接缝为已用
                if (seamIndex + 1 < rwid) usedSeams[i][seamIndex + 1] = true; // 标记相邻的接缝
            }
        } else {
            for (int j = 0; j < rwid; j++) {
                usedSeams[seamIndex][j] = true; // 标记此接缝为已用
                if (seamIndex + 1 < rhei) usedSeams[seamIndex + 1][j] = true; // 标记相邻的接缝
            }
        }
    }
    public static void insertVerticalSeam(int seamIndex) {
        for (int i = 0; i < rhei; i++) {
            // 创建一个新的Map对象作为要插入的接缝
            Map newMap = new Map();
            newMap=map[i][seamIndex];
            // 将接缝右侧的所有元素向右移动一位
            for (int j = rwid; j > seamIndex; j--) {
                map[i][j] = map[i][j - 1];
            }
            // 插入新接缝
            map[i][seamIndex + 1] = newMap;
        }
        markSeamsUsed(seamIndex, true);
    }
    public static void insertHorizontalSeam(int seamIndex) {
        for (int j = 0; j < rwid; j++) {
            // 创建一个新的Map对象作为要插入的接缝
            Map newMap = new Map();
            newMap=map[seamIndex][j];
            // 将接缝下方的所有元素向下移动一位
            for (int i = rhei; i > seamIndex; i--) {
                map[i][j] = map[i - 1][j];
            }
            // 插入新接缝
            map[seamIndex + 1][j] = newMap;
        }
        markSeamsUsed(seamIndex, false);
    }
    public static Map rgetMap(int x, int y) {
        return map[x][y];
    }
    public static double MAXI=40000;
    public static double[][] M_l=new double[1005][1005];//纵向
    public static double[][] M_r=new double[1005][1005];//横向
    public static int rwid,rhei,mw,mh;
    public static void check_Ml(int wid,int hei) {//从上到下
        for(int i=1;i<hei;i++) {
            double fminn=0;
            for(int j=0;j<wid;j++) {
                if(j==0)
                    fminn=Math.min(M_l[j][i-1],M_l[j+1][i-1]);
                else if(j==wid-1)
                    fminn=Math.min(M_l[j-1][i-1],M_l[j][i-1]);
                else
                    fminn=Math.min(Math.min(M_l[j-1][i-1],M_l[j][i-1]),M_l[j+1][i-1]);
                M_l[j][i]=map[j][i].getValue()+fminn;
            }
        }
    }
    public static void check_Mr(int wid,int hei) {//从左到右
        for(int i=1;i<wid;i++) {
            double fminn=0;
            for(int j=0;j<hei;j++) {
                if(j==0)
                    fminn=Math.min(M_r[i-1][j],M_r[i-1][j+1]);
                else if(j==wid-1)
                    fminn=Math.min(M_r[i-1][j-1],M_r[i-1][j]);
                else
                    fminn=Math.min(Math.min(M_r[i-1][j-1],M_r[i-1][j]),M_r[i-1][j+1]);
                M_r[i][j]=map[i][j].getValue()+fminn;
            }
        }
    }
    public static double energy(int i,int j,int x,int y) {
        if(i-x<0||j-y<0||i+x>=rwid||j+y>=rhei)
            return MAXI;//边界极大值？
        int red=map[i-x][j-y].getRed()-map[i+x][j+y].getRed();
        int green=map[i-x][j-y].getGreen()-map[i+x][j+y].getGreen();
        int blue=map[i-x][j-y].getBlue()-map[i+x][j+y].getBlue();
        return (double)(red^2+green^2+blue^2);
    }
    public static int Fdel_l(int wid,int hei){//删除某列
        double minn=M_l[hei-1][0];
        int tag=0;
        for(int i=1;i<wid;i++) {
            if(M_l[hei-1][i]<minn){
                tag=i;
                minn=M_l[hei-1][i];
            }
        }
        return tag;
    }
    public static int Fdel_r(int wid,int hei) {//删除某行
        double minn=M_r[wid-1][0];
        int tag=0;
        for(int i=1;i<hei;i++) {
            if(M_r[wid-1][i]<minn){
                tag=i;
                minn=M_r[wid-1][i];
            }
        }
        return tag;
    }
    public static void del_l(int r,int l) {//第l行第r列纵向删除
        if(l==0)
            return;
        double minn=M_l[r][l]-map[r][l].getValue();
        change(l,r,'l');
        if(M_l[r][l-1]==minn)
            del_l(r,l-1);
        else if(r>0&&M_l[r-1][l-1]==minn)
            del_l(r-1,l-1);
        else
            del_l(r+1,l-1);
    }
    public static void del_r(int r,int l) {//第l行第r列横向删除
        if(r==0)
            return;
        double minn=M_l[r][l]-map[r][l].getValue();
        change(l,r,'r');
        if(l>0&&M_l[r-1][l-1]==minn)
            del_r(r-1,l-1);
        else if(M_r[r-1][l]==minn)
            del_r(r-1,l);
        else
            del_l(r-1,l+1);
    }
    public static void change(int l,int r,char a){
        if(a=='l'){//纵向删除，也就是横向左移
            do {
                map[r][l]=map[r+1][l];
                r++;
            }while(r<mw);
        }
        else{
            do {
                map[r][l]=map[r][l+1];
                l++;
            }while(l<mh);
        }
    }
    public static void del(int m_wid,int m_hei) {
        mw=m_wid;
        mh=m_hei;
        while(rwid>m_wid) {//现在宽纵向删除
            check_Ml(rwid,rhei);
            int minn_l=Fdel_l(rwid,rhei);
            del_l(minn_l,rhei-1);
            rwid--;
            //System.out.println("KKKKKKKKKKkk");
        }
        while(rhei>m_hei) {
            check_Mr(rwid,rhei);
            int minn_r=Fdel_r(rwid,rhei);
            del_r(rwid-1,minn_r);
            rhei--;
        }
    }
    public static void find_energy(BufferedImage image) {
        //ImageIO.read(new File("C:/Users/QXY/Desktop/屏幕截图 2024-04-15 180640.png"));
        rwid = image.getWidth();
        rhei = image.getHeight();
        for (int i = 0; i < rwid; i++) {//i行j列
            for (int j = 0; j < rhei; j++) {
                int st = image.getRGB(i, j);
                map[i][j]=new Map();
                map[i][j].setRgb(st);
                map[i][j].setRed((st >> 16) & 0xFF);
                map[i][j].setGreen((st >> 8) & 0xFF);
                map[i][j].setBlue(st & 0xFF);

            }
        }
        for (int i = 0; i < rwid; i++) {
            for (int j = 0; j < rhei; j++) {
                map[i][j].setValue(energy(i, j, 0, 1),energy(i, j, 1, 0));
            }
        }
    }
    public static void toMax(Set<Point> nx) {
        while(!nx.isEmpty()) {
            Point a=nx.iterator().next();
            map[a.x][a.y].value=MAXI;
        }
    }
    public static void toMin(Set<Point> nx) {
        while(!nx.isEmpty()) {
            Point a=nx.iterator().next();
            map[a.x][a.y].value=-MAXI;
        }
    }
//    public static void main(String[] args){
//        rhei=rwid=10;
//        for (int i = 0; i < rwid; i++) {//i行j列
//            for (int j = 0; j < rhei; j++) {
//                map[i][j]=new Map();
//                map[i][j].setRgb(100);
//                map[i][j].setRed(100);
//                map[i][j].setGreen(100);
//                map[i][j].setBlue(100);
//            }
//        }
//        for (int i = 0; i < rwid; i++) {
//            for (int j = 0; j < rhei; j++) {
//                if(i==0||j==0||i==rwid-1||j==rhei-1)
//                    map[i][j].value=MAXI;
//                else
//                    map[i][j].setValue(energy(i, j, 0, 1),energy(i, j, 1, 0));
//            }
//        }
////        del(9,9);
//        insertSeam(9,9);
//    }
}