package pintu;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
public class ImagePieceTogether extends JFrame implements ActionListener {// 操作实现拼图的游戏的类
    PanelOfImage imagePanel;// 声明图片面板
    PanelOfImage imageFirst;// 声明原图面板
//    PanelOfImage stepCount;   //步数面板
    JPanel panelOfSouth, panelOfLook;// //声明南侧面板和查看面板
    Button startButton;// 声明开始按钮
    Button chooseButton;// 选择按钮
    Button resetButton;  //重置按钮
    Button lookButton;  //查看按钮
    int flag;    //开始按钮只在开始起作用
    Container container;// 容器，得到内容面板
 
    public ImagePieceTogether() {// 构造方法进行初始化
        container = this.getContentPane();// 获得内容面板
        startButton = new Button("开始");// 创建开始按钮
        startButton.addActionListener(this);// 添加监听事件
        chooseButton = new Button("选择");
        chooseButton.addActionListener(this);
        resetButton = new Button("重置");
        resetButton.addActionListener(this);
        lookButton = new Button("查看");
        lookButton.addActionListener(this);
        flag = 1;
        panelOfLook = new JPanel();// 创建查看面板
//        Icon iconFirst = new ImageIcon("picture/yuantu_" + PanelOfImage.currentPID + ".jpg");
//        JLabel labelFirst = new JLabel(iconFirst); //创建原图图标
//        labelFirst.setBounds(320,100,200,200);  //设置原图标签的位置
//        panelOfLook.add(labelFirst);
        panelOfSouth = new JPanel();// 创建南侧面板
        panelOfSouth.setBackground(Color.red);// 设置背景颜色
        panelOfSouth.add(startButton);// 添加开始按钮
        panelOfSouth.add(chooseButton);// 添加选择按钮
        panelOfSouth.add(resetButton);  //添加重置按钮
        panelOfSouth.add(lookButton);   //添加查看按钮
        imagePanel = new PanelOfImage();// 创建图片面板
        imageFirst = new PanelOfImage();   //创建原图面板
//        stepCount = new PanelOfImage();  //创建计时面板
        container.add(imagePanel, BorderLayout.CENTER);
        container.add(panelOfSouth, BorderLayout.SOUTH);
        container.add(imageFirst,BorderLayout.EAST);
//        container.add(stepCount,BorderLayout.NORTH);
//        container.add(timeCount,BorderLayout.EAST);
        this.setTitle("Java拼图小游戏");// 设置标题
        this.setLocation(300, 200);// 设置位置
        this.setSize(600, 365);// 设置大小
        this.setResizable(false);// 设置是否可以通过某个用户操作调整
        this.setVisible(true);// 设置可视
        this.setDefaultCloseOperation(3);// 设置默认关闭操作
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {// 按钮触发的事件
        Button button = (Button) event.getSource();// 获得事件按钮源
        if (button == startButton) {// 如果是开始按钮
            if(flag == 1){
            	imagePanel.breakRank();// 调用图片方格打乱方法
            	imageFirst.showFirst(getGraphics());  //显示原图
            	flag = 0;
            }
        } 
        else if (button == resetButton) {  //如果是重置按钮
        	if(flag==0)  //开始游戏才能点击重置按钮
        		imagePanel.resetRank();// 调用图片方格打乱方法
        }
        else if (button == lookButton) {   //如果是查看按钮
        	if(flag==0)	//开始游戏才能点击重置按钮
        		imageFirst.showFirst(getGraphics());  //显示原图
        }
        else if (button == chooseButton) {// 如果是选择按钮
            Choice choice = new Choice();// 创建选择器 
            choice.add("--水果--");// 添加列表项
            choice.add("--美女--");
            choice.add("--数字--");
            int i = JOptionPane.showConfirmDialog(this, choice, "选择图片", JOptionPane.OK_CANCEL_OPTION);// 弹出对话框
            if (i == JOptionPane.YES_OPTION) {// 选择对话框的确定按钮
                PanelOfImage.currentPID = choice.getSelectedIndex() + 1;// 获得列表项的编号
                imagePanel.reLoadPictrue();// 图片重载
                imageFirst.showFirst(getGraphics());   //显示原图
                Icon icon = new ImageIcon("picture/pic_" + PanelOfImage.currentPID + ".jpg");// 获得图片图标
//                Icon iconFirst = new ImageIcon("picture/yuantu_" + PanelOfImage.currentPID + ".jpg");
                JLabel label = new JLabel(icon);// 根据图标设置标签
//                JLabel labelFirst = new JLabel(iconFirst);
                label.setBounds(0, 0, 300, 300);// 设置标签的方位
//                labelFirst.setBounds(350,100,200,200);
                panelOfLook.removeAll();
                panelOfLook.add(label);
//                panelOfLook.add(labelFirst);
                imagePanel.resetRank();// 调用图片方格打乱方法
                panelOfLook.repaint();
            }
        }
    }
 
    public static void main(String[] args) {// java程序主入口处
        new ImagePieceTogether();// 实例化对象
    }
}