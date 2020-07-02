package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class shop{
    Player player;
    JLabel MAX_HP_label;
    JLabel attack_label;
    JPanel jp;

    shop(Player player,JPanel jp){
        this.player=player;
        this.jp=jp;
    }
    public void open(){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(500,300);
        JPanel sp = new JPanel();
        Color LightYellow  = new Color(255,248,220);
        sp.setBackground(LightYellow);
        frame.getContentPane().add(sp);

        MAX_HP_label = new JLabel("            最大生命值: "+player.MAX_HP+"           ");
        MAX_HP_label.setFont(new java.awt.Font("Dialog", 1, 24));

        if(player.MAX_HP<200)MAX_HP_label.setForeground(Color.black);
        if(player.MAX_HP>600&&player.MAX_HP<1000)MAX_HP_label.setForeground(Color.blue);
        Color purple = new Color(128,0,128);
        if(player.MAX_HP>3000&&player.MAX_HP<5000)MAX_HP_label.setForeground(purple);
        if(player.MAX_HP>10000)MAX_HP_label.setForeground(Color.red);
        sp.add(MAX_HP_label);

        JButton hp_button = new JButton("属性重置");
        sp.add(hp_button);
        hp_button.addActionListener(new hp_change());

        attack_label = new JLabel("            攻击力: "+player.attack+"               ");
        attack_label.setFont(new java.awt.Font("Dialog", 1, 24));
        if(player.attack<30)attack_label.setForeground(Color.black);
        if(player.attack>100&&player.attack<200)attack_label.setForeground(Color.blue);
        if(player.attack>500&&player.attack<800)attack_label.setForeground(purple);
        if(player.attack>2000)attack_label.setForeground(Color.red);
        sp.add(attack_label);

        JButton attack_button = new JButton("属性重置");
        sp.add(attack_button);
        attack_button.addActionListener(new attack_change());
    }


    class hp_change implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(player.money>=10){
                MAX_HP_label.setText("            最大生命值: "+player.MAX_HP+"          ");
                player.money=player.money-10;
                int quality =(int)(Math.random()*100);
                if(quality<60)player.MAX_HP=(int)(Math.random()*100+100);
                if(quality>=60&&quality<85)player.MAX_HP=(int)(Math.random()*400+600);
                if(quality>=85&&quality<95)player.MAX_HP=(int)(Math.random()*2000+3000);
                if(quality>=95&&quality<=100)player.MAX_HP=(int)(Math.random()*10000+10000);

                if(player.MAX_HP<=200)MAX_HP_label.setForeground(Color.black);
                if(player.MAX_HP>=600&&player.MAX_HP<=1000)MAX_HP_label.setForeground(Color.blue);
                Color purple = new Color(128,0,128);
                if(player.MAX_HP>=3000&&player.MAX_HP<=5000)MAX_HP_label.setForeground(purple);
                if(player.MAX_HP>=10000)MAX_HP_label.setForeground(Color.red);
                MAX_HP_label.setText("           最大生命值: "+player.MAX_HP+"          ");
            }else{
                MAX_HP_label.setText("      金币不足         ");
            }
            jp.repaint();
        }

    }
    class attack_change implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(player.money>=10){
                attack_label.setText("            攻击力: "+player.attack+"             ");
                player.money=player.money-10;
                int quality =(int)(Math.random()*100);
                if(quality<60)player.attack=(int)(Math.random()*30);
                if(quality>=60&&quality<85)player.attack=(int)(Math.random()*100+100);
                if(quality>=85&&quality<95)player.attack=(int)(Math.random()*300+500);
                if(quality>=95&&quality<=100)player.attack=(int)(Math.random()*2000+2000);

                if(player.attack<30)attack_label.setForeground(Color.black);
                if(player.attack>100&&player.attack<200)attack_label.setForeground(Color.blue);
                Color purple = new Color(128,0,128);
                if(player.attack>500&&player.attack<800)attack_label.setForeground(purple);
                if(player.attack>2000)attack_label.setForeground(Color.red);
                attack_label.setText("            攻击力: "+player.attack+"             ");
            }else{
                attack_label.setText("         金币不足         ");
            }
            jp.repaint();
        }
    }
}
