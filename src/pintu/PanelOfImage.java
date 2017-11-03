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
class PaneButton extends JButton {// �̳а�ť��ʵ�ּ�ͼƬ�ķ���
    PaneButton(Icon icon) {// ���췽�����г�ʼ��������ͼ��
        super(icon);
        this.setSize(100, 100);// ����ÿ������Ĵ�С
    }
    public void move(String direction, int sleep) {// ������ƶ�
        if (direction == "UP") {// ���������ƶ�
            this.setLocation(this.getBounds().x, this.getBounds().y - 100);
        } else if (direction == "DOWN") {// ���������ƶ�
            this.setLocation(this.getBounds().x, this.getBounds().y + 100);
        } else if (direction == "LEFT") {// ���������ƶ�
            this.setLocation(this.getBounds().x - 100, this.getBounds().y);
        } else {// ���������ƶ�
            this.setLocation(this.getBounds().x + 100, this.getBounds().y);
        }
    }
}

class ImageCutUtil{     //�и�ͼƬ
	public boolean cutImage(File souceImage,int cutNumber,int ID,String saveImagePath){
		try{
			BufferedImage source = ImageIO.read(souceImage);
			int allWidth = source.getWidth();   //��ȡͼƬ�Ŀ��
			int allHeight = source.getHeight();  //��ȡͼƬ�ĸ߶�
			int width = (int)(allWidth*1.0/cutNumber);  //�и��ÿһ��ͼƬ�Ŀ��
			int height = (int)(allHeight*1.0/cutNumber); //�и��ÿһ��ͼƬ�ĸ߶�
			for(int i = 0;i<cutNumber;i++){
				for(int j = 0;j<cutNumber;j++){
					ImageIO.write(source.getSubimage(j*width, i*height, width, height),"JPEG",new File(saveImagePath+"\\pic_"+ID+"_"+(i*3+j+1)+".jpg"));
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("��ͼʧ��");
			return false;
		}
	}
}
public class PanelOfImage extends JPanel implements MouseListener {// ͼƬ�����ط������
    boolean hasAddActionListener = false;// ���÷������������ı�ʶ
    PaneButton pane[];// ��������   
    Rectangle nullPanel;// �����շ���û����ͼƬ
    public static int currentPID = 3;// ��ǰѡ���ͼƬ���
    ImageCutUtil imageCutUtil = new ImageCutUtil();  //���ú����и�ͼƬ 
    boolean flag = imageCutUtil.cutImage(new File("E://java-liu/pintu/picture/pic_"+currentPID+".jpg"),3,currentPID,"E://java-liu/pintu/cutImages");
    public PanelOfImage() {// ���췽�����г�ʼ��
        this.setLayout(null);// �������Ĳ���Ϊ��
        this.setSize(600, 365);// �������Ĵ�С
        nullPanel = new Rectangle(200, 200, 100, 100);// ���ÿշ����λ��
        pane = new PaneButton[9];// �����Ÿ�����
        Icon icon;// ����ͼ��
        for (int i = 0; i < 3; i++) {// ѭ��Ϊÿ���������ͼƬ
            for (int j = 0; j < 3; j++) {// ѭ����
                icon = new ImageIcon("cutImages/pic_" + currentPID + "_"
                        + (i * 3 + j + 1) + ".jpg");// ����ͼ��
                pane[i * 3 + j] = new PaneButton(icon);// ���������ڷ����м���ͼƬ
                pane[i * 3 + j].setLocation(j * 100, i * 100);// ���÷����λ��
                this.add(pane[i * 3 + j]);// �����ӷ���
                
            }
        }
//        this.remove(pane[(int)(Math.random()*8)]);// ����Ƴ�һ���ķ���
        this.remove(pane[8]);
    }

	public boolean isFinish() {// �ж��Ƿ�ƴ�ճɹ�
        for (int i = 0; i < 8; i++) {
            int x = pane[i].getBounds().x;
            int y = pane[i].getBounds().y;
            if (y / 100 * 3 + x / 100 != i)
                return false;
        }
        
        return true;
    }
    public void reLoadPictrue() {// ���¼���ͼƬ������ѡ��ͼƬʱ
        Icon icon;
        for (int i = 0; i < 3; i++) {// ѭ��Ϊÿ���������ͼƬ
            for (int j = 0; j < 3; j++) {
                icon = new ImageIcon("cutImages/pic_" + currentPID + "_"
                        + (i * 3 + j + 1) + ".jpg");
                pane[i * 3 + j].setIcon(icon);
            }
        }
    }
    public void showFirst(Graphics g) {  //��ʾԭͼ
    	Image image = null;
    	try{
    		image = ImageIO.read(new File("picture/yuantu_" + currentPID + ".jpg"));
    		g.drawImage(image,350,120,200,200,null);   //ԭͼ�İڷ�λ��
    		
    	}catch(Exception e){
    		e.printStackTrace(); 
    	}
    	
//    	JLabel l = new JLabel(new ImageIcon("picture/yuantu_" + currentPID + ".jpg"));
//    	this.add(l);
//    	this.setVisible(true);
//    	this.setBounds(350, 120, 200, 200);
    }
    public void breakRank() {// ���������������
        while (pane[0].getBounds().x <= 100 && pane[0].getBounds().y <= 100) {// ����һ����������Ͻǽ�ʱ
    		int x = nullPanel.getBounds().x;
            int y = nullPanel.getBounds().y;
            int direction = (int)(Math.random() * 4);// �������һ�����ֶ�Ӧ�շ�������������ƶ�
            if (direction == 0) {// �շ������ƶ�������෽�񻥻�λ�ã���෽�����ƶ�
                x -= 100;// ����������
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {// ѭ��Ѱ�����İ�ť
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {// ����Ѱ�����İ�ť
                            pane[j].move("RIGHT", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);// �������ÿշ����λ��
                            break;// ����ѭ��
                        }
                    }
                }
            } else if (direction == 1) {// �շ������ƶ�
                x += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("LEFT", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else if (direction == 2) {// �շ������ƶ�
                y -= 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("DOWN", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else {// �շ������ƶ�
                y += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("UP", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            }
        }
        if (!hasAddActionListener)// �ж��Ƿ���Ӷ����¼�
            for (int i = 0; i < 8; i++) {// ѭ��Ϊÿ��������Ӷ����¼�
                pane[i].addMouseListener(this);
            }
        hasAddActionListener = true;
    }
    
    public void resetRank() {// ��������
    	int m=50;
        while (m>0) {
        	m--;
    		int x = nullPanel.getBounds().x;
            int y = nullPanel.getBounds().y;
            int direction = (int)(Math.random() * 4);// �������һ�����ֶ�Ӧ�շ�������������ƶ�
            if (direction == 0) {// �շ������ƶ�������෽�񻥻�λ�ã���෽�����ƶ�
                x -= 100;// ����������
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {// ѭ��Ѱ�����İ�ť
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {// ����Ѱ�����İ�ť
                            pane[j].move("RIGHT", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);// �������ÿշ����λ��
                            break;// ����ѭ��
                        }
                    }
                }
            } else if (direction == 1) {// �շ������ƶ�
                x += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("LEFT", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else if (direction == 2) {// �շ������ƶ�
                y -= 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("DOWN", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            } else {// �շ������ƶ�
                y += 100;
                if (test(x, y)) {
                    for (int j = 0; j < 8; j++) {
                        if ((pane[j].getBounds().x == x)
                                && (pane[j].getBounds().y == y)) {
                            pane[j].move("UP", 100);// ���������ƶ�һ��
                            nullPanel.setLocation(x, y);
                            break;
                        }
                    }
                }
            }
        }
        if (!hasAddActionListener)// �ж��Ƿ���Ӷ����¼�
            for (int i = 0; i < 8; i++) {// ѭ��Ϊÿ��������Ӷ����¼�
                pane[i].addMouseListener(this);
            }
        hasAddActionListener = true;
    }
    
    private boolean test(int x, int y) {// ��ⷽ���Ƿ���ָ���ķ�Χ���ƶ�
        if ((x >= 0 && x <= 200) || (y >= 0 && y <= 200))
            return true;
        else
            return false;
    }
    public void mouseClicked(MouseEvent arg0) {// �����ʱ����
    }
    public void mouseEntered(MouseEvent arg0) {// �������������ʱ����
    }
    public void mouseExited(MouseEvent arg0) {// ������겻���ƶ������ķ�Χ
    }
    public void mouseReleased(MouseEvent arg0) {// ��갴����������ͷ�ʱ����
    }
    public void mousePressed(MouseEvent event) {// ��갴��ʱ����
        PaneButton button = (PaneButton) event.getSource();// �����갴�ķ���ť
        int x1 = button.getBounds().x;// ��ø÷���ť�ĺ�����
        int y1 = button.getBounds().y;// ��ø÷���ť��������
        int nullDir_X = nullPanel.getBounds().x;// �õ��շ���ĺ�����
        int nullDir_Y = nullPanel.getBounds().y;// �õ��շ����������
        if (x1 == nullDir_X && y1 - nullDir_Y == 100)// ���бȽϹ����������򽻻�
            button.move("UP", 100);// ���������ƶ�
        else if (x1 == nullDir_X && y1 - nullDir_Y == -100)
            button.move("DOWN", 100);// ���������ƶ�
        else if (x1 - nullDir_X == 100 & y1 == nullDir_Y)
            button.move("LEFT", 100);// ���������ƶ�
        else if (x1 - nullDir_X == -100 && y1 == nullDir_Y)
            button.move("RIGHT", 100);// ���������ƶ�
        else
            return;
        nullPanel.setLocation(x1, y1);// �������ÿշ����λ��
        this.repaint();// ���¼���
        if (this.isFinish()) {// �����Ƿ���ɵ��ж�
        	this.add(pane[8]);    //��ȱ�ٵ��ǿ鲹��ȥ
            JOptionPane.showMessageDialog(this, "��ϲ����ԭͼƬ�ɹ���");
            for (int i = 0; i < 8; i++) {// ѭ����������¼�
                pane[i].removeMouseListener(this);
            }
            hasAddActionListener = false;
        }
    }
}
