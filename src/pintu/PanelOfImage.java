package pintu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;
class PaneButton extends JButton {// 继承按钮类实现加图片的方格
    PaneButton(Icon icon) {// 构造方法进行初始化，设置图标
        super(icon);
        this.setSize(100, 100);// 设置每个方格的大小
    }
    public void move(String direction, int sleep) {// 方格的移动
        if (direction == "UP") {// 方格向上移动
            this.setLocation(this.getBounds().x, this.getBounds().y - 100);
        } else if (direction == "DOWN") {// 方格向下移动
            this.setLocation(this.getBounds().x, this.getBounds().y + 100);
        } else if (direction == "LEFT") {// 方格向左移动
            this.setLocation(this.getBounds().x - 100, this.getBounds().y);
        } else {// 方格向右移动
            this.setLocation(this.getBounds().x + 100, this.getBounds().y);
        }
    }
}

class ImageCutUtil{     //切割图片
	public boolean cutImage(File souceImage,int cutNumber,int ID,String saveImagePath){
		try{
			BufferedImage source = ImageIO.read(souceImage);
			int allWidth = source.getWidth();   //获取图片的宽度
			int allHeight = source.getHeight();  //获取图片的高度
			int width = (int)(allWidth*1.0/cutNumber);  //切割的每一份图片的宽度
			int height = (int)(allHeight*1.0/cutNumber); //切割的每一份图片的高度
			for(int i = 0;i<cutNumber;i++){
				for(int j = 0;j<cutNumber;j++){
					ImageIO.write(source.getSubimage(j*width, i*height, width, height),"JPEG",new File(saveImagePath+"\\pic_"+ID+"_"+(i*3+j+1)+".jpg"));
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("切图失败");
			return false;
		}
	}
}
public class PanelOfImage extends JPanel implements MouseListener {// 图片面板加载方格对象
    boolean hasAddActionListener = false;// 设置方格动作监听器的标识
    PaneButton pane[];// 声明方格   
    Rectangle nullPanel;// 声明空方格，没有添图片
    public static int currentPID = 3;// 当前选择的图片编号
    ImageCutUtil imageCutUtil = new ImageCutUtil();  //调用函数切割图片 
    boolean flag = imageCutUtil.cutImage(new File("E://java-liu/pintu/picture/pic_"+currentPID+".jpg"),3,currentPID,"E://java-liu/pintu/cutImages");
    public PanelOfImage() {// 构造方法进行初始化
        this.setLayout(null);// 设置面板的布局为空
        this.setSize(600, 365);// 设置面板的大小
        nullPanel = new Rectangle(200, 200, 100, 100);// 设置空方格的位置
        pane = new PaneButton[9];// 创建九个方格
        Icon icon;// 声明图标
        for (int i = 0; i < 3; i++) {// 循环为每个方格加载图片
            for (int j = 0; j < 3; j++) {// 循环列
                icon = new ImageIcon("cutImages/pic_" + currentPID + "_"
                        + (i * 3 + j + 1) + ".jpg");// 创建图标
                pane[i * 3 + j] = new PaneButton(icon);// 创建方格在方格中加载图片
                pane[i * 3 + j].setLocation(j * 100, i * 100);// 设置方格的位置
                this.add(pane[i * 3 + j]);// 面板添加方格
                
            }
        }
//        this.remove(pane[(int)(Math.random()*8)]);// 随机移除一个的方格
        this.remove(pane[8]);
    }

	public boolean isFinish() {// 判断是否拼凑成功
        for (int i = 0; i < 8; i++) {
            int x = pane[i].getBounds().x;
            int y = pane[i].getBounds().y;
            if (y / 100 * 3 + x / 100 != i)
                return false;
        }
        
        return true;
    }
    public void reLoadPictrue() {// 重新加载图片在重新选择图片时
        Icon icon;
        for (int i = 0; i < 3; i++) {// 循环为每个方格加载图片
            for (int j = 0; j < 3; j++) {
                icon = new ImageIcon("cutImages/pic_" + currentPID + "_"
                        + (i * 3 + j + 1) + ".jpg");
                pane[i * 3 + j].setIcon(icon);
            }
        }
    }
    public void showFirst(Graphics g) {  //显示原图
    	Image image = null;
    	try{
    		image = ImageIO.read(new File("picture/yuantu_" + currentPID + ".jpg"));
    		g.drawImage(image,350,120,200,200,null);   //原图的摆放位置
    		
    	}catch(Exception e){
    		e.printStackTrace(); 
    	}
    	
//    	JLabel l = new JLabel(new ImageIcon("picture/yuantu_" + currentPID + ".jpg"));
//    	this.add(l);
//    	this.setVisible(true);
//    	this.setBounds(350, 120, 200, 200);
    }
    public void breakRank() {// 方格打乱重新排序
        while (pane[0].getBounds().x <= 100 && pane[0].getBounds().y <= 100) {// 当第一个方格距左上角近时
    		int x = nullPanel.getBounds().x;
            int y = nullPanel.getBounds().y;
            int direction = (int)(Math.random() * 4);// 随机产生一个数字对应空方格的上下左右移动
            if (direction == 0) {// 空方格左移动，与左侧方格互换位置，左侧方格右移动
                x -= 100;// 空主格左移
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {// 循环寻找左侧的按钮
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {// 依次寻找左侧的按钮
                            pane[j].move("RIGHT", 100);// 方格向右移动一格
                            nullPanel.setLocation(x, y);// 重新设置空方格的位置
                            break;// 跳出循环
                        }
                    }
                }
            } else if (direction == 1) {// 空方格右移动
                x += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("LEFT", 100);// 方格向左移动一格
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else if (direction == 2) {// 空方格上移动
                y -= 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("DOWN", 100);// 方格向下移动一格
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else {// 空方格下移动
                y += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("UP", 100);// 方格向上移动一格
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            }
        }
        if (!hasAddActionListener)// 判断是否添加动作事件
            for (int i = 0; i < 8; i++) {// 循环为每个方格添加动作事件
                pane[i].addMouseListener(this);
            }
        hasAddActionListener = true;
    }
    
    public void resetRank() {// 方格重置
    	int m=50;
        while (m>0) {
        	m--;
    		int x = nullPanel.getBounds().x;
            int y = nullPanel.getBounds().y;
            int direction = (int)(Math.random() * 4);// 随机产生一个数字对应空方格的上下左右移动
            if (direction == 0) {// 空方格左移动，与左侧方格互换位置，左侧方格右移动
                x -= 100;// 空主格左移
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {// 循环寻找左侧的按钮
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {// 依次寻找左侧的按钮
                            pane[j].move("RIGHT", 100);// 方格向右移动一格
                            nullPanel.setLocation(x, y);// 重新设置空方格的位置
                            break;// 跳出循环
                        }
                    }
                }
            } else if (direction == 1) {// 空方格右移动
                x += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("LEFT", 100);// 方格向左移动一格
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else if (direction == 2) {// 空方格上移动
                y -= 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("DOWN", 100);// 方格向下移动一格
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else {// 空方格下移动
                y += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("UP", 100);// 方格向上移动一格
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            }
        }
        if (!hasAddActionListener)// 判断是否添加动作事件
            for (int i = 0; i < 8; i++) {// 循环为每个方格添加动作事件
                pane[i].addMouseListener(this);
            }
        hasAddActionListener = true;
    }
    
    private boolean test(int x, int y) {// 检测方格是否在指定的范围内移动
        if ((x >= 0 && x <= 200) || (y >= 0 && y <= 200))
            return true;
        else
            return false;
    }
    public void mouseClicked(MouseEvent arg0) {// 鼠标点击时调用
    }
    public void mouseEntered(MouseEvent arg0) {// 鼠标进入组件区域时调用
    }
    public void mouseExited(MouseEvent arg0) {// 控制鼠标不能移动出面板的范围
    }
    public void mouseReleased(MouseEvent arg0) {// 鼠标按键在组件上释放时调用
    }
    public void mousePressed(MouseEvent event) {// 鼠标按下时调用
        PaneButton button = (PaneButton) event.getSource();// 获得鼠标按的方格按钮
        int x1 = button.getBounds().x;// 获得该方格按钮的横坐标
        int y1 = button.getBounds().y;// 获得该方格按钮的纵坐标
        int nullDir_X = nullPanel.getBounds().x;// 得到空方格的横坐标
        int nullDir_Y = nullPanel.getBounds().y;// 得到空方格的纵坐标
        if (x1 == nullDir_X && y1 - nullDir_Y == 100)// 进行比较果满足条件则交换
            button.move("UP", 100);// 方格向上移动
        else if (x1 == nullDir_X && y1 - nullDir_Y == -100)
            button.move("DOWN", 100);// 方格向下移动
        else if (x1 - nullDir_X == 100 & y1 == nullDir_Y)
            button.move("LEFT", 100);// 方格向左移动
        else if (x1 - nullDir_X == -100 && y1 == nullDir_Y)
            button.move("RIGHT", 100);// 方格向右移动
        else
            return;
        nullPanel.setLocation(x1, y1);// 重新设置空方格的位置
        this.repaint();// 重新加载
        if (this.isFinish()) {// 进行是否完成的判断
        	this.add(pane[8]);    //把缺少的那块补上去
            JOptionPane.showMessageDialog(this, "恭喜，还原图片成功！");
            for (int i = 0; i < 8; i++) {// 循环撤消鼠标事件
                pane[i].removeMouseListener(this);
            }
            hasAddActionListener = false;
        }
    }
}
