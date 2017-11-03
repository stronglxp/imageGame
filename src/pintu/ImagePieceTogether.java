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
 
public class ImagePieceTogether extends JFrame implements ActionListener {// ����ʵ��ƴͼ����Ϸ����
    PanelOfImage imagePanel;// ����ͼƬ���
    PanelOfImage imageFirst;// ����ԭͼ���
//    PanelOfImage stepCount;   //�������
    JPanel panelOfSouth, panelOfLook;// //�����ϲ����Ͳ鿴���
    Button startButton;// ������ʼ��ť
    Button chooseButton;// ѡ��ť
    Button resetButton;  //���ð�ť
    Button lookButton;  //�鿴��ť
    int flag;    //��ʼ��ťֻ�ڿ�ʼ������
    Container container;// �������õ��������
 
    public ImagePieceTogether() {// ���췽�����г�ʼ��
        container = this.getContentPane();// ����������
        startButton = new Button("��ʼ");// ������ʼ��ť
        startButton.addActionListener(this);// ��Ӽ����¼�
        chooseButton = new Button("ѡ��");
        chooseButton.addActionListener(this);
        resetButton = new Button("����");
        resetButton.addActionListener(this);
        lookButton = new Button("�鿴");
        lookButton.addActionListener(this);
        flag = 1;
        panelOfLook = new JPanel();// �����鿴���
//        Icon iconFirst = new ImageIcon("picture/yuantu_" + PanelOfImage.currentPID + ".jpg");
//        JLabel labelFirst = new JLabel(iconFirst); //����ԭͼͼ��
//        labelFirst.setBounds(320,100,200,200);  //����ԭͼ��ǩ��λ��
//        panelOfLook.add(labelFirst);
        panelOfSouth = new JPanel();// �����ϲ����
        panelOfSouth.setBackground(Color.red);// ���ñ�����ɫ
        panelOfSouth.add(startButton);// ��ӿ�ʼ��ť
        panelOfSouth.add(chooseButton);// ���ѡ��ť
        panelOfSouth.add(resetButton);  //������ð�ť
        panelOfSouth.add(lookButton);   //��Ӳ鿴��ť
        imagePanel = new PanelOfImage();// ����ͼƬ���
        imageFirst = new PanelOfImage();   //����ԭͼ���
//        stepCount = new PanelOfImage();  //������ʱ���
        container.add(imagePanel, BorderLayout.CENTER);
        container.add(panelOfSouth, BorderLayout.SOUTH);
        container.add(imageFirst,BorderLayout.EAST);
//        container.add(stepCount,BorderLayout.NORTH);
//        container.add(timeCount,BorderLayout.EAST);
        this.setTitle("JavaƴͼС��Ϸ");// ���ñ���
        this.setLocation(300, 200);// ����λ��
        this.setSize(600, 365);// ���ô�С
        this.setResizable(false);// �����Ƿ����ͨ��ĳ���û���������
        this.setVisible(true);// ���ÿ���
        this.setDefaultCloseOperation(3);// ����Ĭ�Ϲرղ���
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {// ��ť�������¼�
        Button button = (Button) event.getSource();// ����¼���ťԴ
        if (button == startButton) {// ����ǿ�ʼ��ť
            if(flag == 1){
            	imagePanel.breakRank();// ����ͼƬ������ҷ���
            	imageFirst.showFirst(getGraphics());  //��ʾԭͼ
            	flag = 0;
            }
        } 
        else if (button == resetButton) {  //��������ð�ť
        	if(flag==0)  //��ʼ��Ϸ���ܵ�����ð�ť
        		imagePanel.resetRank();// ����ͼƬ������ҷ���
        }
        else if (button == lookButton) {   //����ǲ鿴��ť
        	if(flag==0)	//��ʼ��Ϸ���ܵ�����ð�ť
        		imageFirst.showFirst(getGraphics());  //��ʾԭͼ
        }
        else if (button == chooseButton) {// �����ѡ��ť
            Choice choice = new Choice();// ����ѡ���� 
            choice.add("--ˮ��--");// ����б���
            choice.add("--��Ů--");
            choice.add("--����--");
            int i = JOptionPane.showConfirmDialog(this, choice, "ѡ��ͼƬ", JOptionPane.OK_CANCEL_OPTION);// �����Ի���
            if (i == JOptionPane.YES_OPTION) {// ѡ��Ի����ȷ����ť
                PanelOfImage.currentPID = choice.getSelectedIndex() + 1;// ����б���ı��
                imagePanel.reLoadPictrue();// ͼƬ����
                imageFirst.showFirst(getGraphics());   //��ʾԭͼ
                Icon icon = new ImageIcon("picture/pic_" + PanelOfImage.currentPID + ".jpg");// ���ͼƬͼ��
//                Icon iconFirst = new ImageIcon("picture/yuantu_" + PanelOfImage.currentPID + ".jpg");
                JLabel label = new JLabel(icon);// ����ͼ�����ñ�ǩ
//                JLabel labelFirst = new JLabel(iconFirst);
                label.setBounds(0, 0, 300, 300);// ���ñ�ǩ�ķ�λ
//                labelFirst.setBounds(350,100,200,200);
                panelOfLook.removeAll();
                panelOfLook.add(label);
//                panelOfLook.add(labelFirst);
                imagePanel.resetRank();// ����ͼƬ������ҷ���
                panelOfLook.repaint();
            }
        }
    }
 
    public static void main(String[] args) {// java��������ڴ�
        new ImagePieceTogether();// ʵ��������
    }
}