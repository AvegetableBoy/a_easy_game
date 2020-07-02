package game;
import javax.swing.*;
import java.awt.*;

class Menu {
    JTextArea help = new JTextArea(14,50);
    public void open(){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(700,400);
        JPanel menu = new JPanel();
        Color BLACK = new Color(0,0,0);
        menu.setBackground(BLACK);//设置背景颜色
        frame.getContentPane().add(menu);

        JScrollPane scroller = new JScrollPane(help);
        help.setLineWrap(true);//换行

        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);//数值滚动条设置
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//水平滚动条设置

        menu.add(scroller);
        help.setBackground(Color.black);
        help.setForeground(Color.red);
        Font font = new Font("黑体",Font.PLAIN,20);

        help.setText("   欢迎玩老头大战史莱姆，为什么是老头呢?因为没有好的美工，难受\nA向左走;D向右走;j攻击;W或K跳跃，按H使用技能（把你变消失）耗费20点蓝\n  ");
        help.append("   打死怪物掉落金币,怪物金币掉落数量等于杀死的怪物数量，捡起来后才会刷新的怪\n");
        help.append("   可以在催化神石那花10金币改造一下体质\n");
        help.append("   各种品质概率如下:后天(黑色)60%;先天(蓝色)25%;灵体(紫色)10%;霸体(红色)5%\n");
        help.append("   白色的飞天阵按跳跃键能上二层，黑色的是怪兽刷新点\n");

        help.setFont(font);
        help.setEditable(false);
    }
}