package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class panorama{

    Player player ;		//component
    Monster monster;
    scenery sce;

    panorama(){
        player = new Player();
        monster = new Monster();
    }


    public void draw_panorama(){		//create the window and get it the panel
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口关闭
        frame.setVisible(true);//设置窗口是否可见
        frame.setSize(1200,700);//设置窗口大小
        frame.setResizable(false);   //设置窗口大小是否固定

        sce = new scenery();
        frame.getContentPane().add(sce);//创建容器，添加控件
        frame.addKeyListener(sce); // 添加键盘监听
        frame.addMouseListener(sce);//添加鼠标监听

        monster.move(sce,player);
        player.recovery(sce);
    }

    public void fight(){//攻击判定
        if((player.y-monster.y)>=-30){
            if(player.left&&(player.x-monster.x)<=120&&(player.x-monster.x)>=-60){
                if(player.attacking){
                    monster.being_attacked = true;
                    sce.repaint();
                    monster.x=monster.x-10;
                    monster.HP=monster.HP-player.attack;
                    if(monster.HP<=0){
                        monster.death();
                        monster.HP=0;
                        player.killed++;
                    }
                }
            }
            if(!player.left&&(monster.x-player.x)>=-60&&(monster.x-player.x)<=120){
                if(player.attacking){
                    monster.being_attacked = true;
                    sce.repaint();
                    monster.x=monster.x+10;//击退效果
                    monster.HP=monster.HP-player.attack;
                    if(monster.HP<=0){
                        monster.HP=0;
                        monster.death();
                        player.killed++;
                    }
                }
            }

        }
    }

    public void  Skill(){
        if(player.skill&&player.MP>=20){
            monster.being_attacked = true;
            sce.repaint();
            monster.HP=0;
            player.MP=player.MP-20;
            monster.death();
            player.killed++;
        }



    }


    class scenery extends JPanel implements KeyListener,MouseListener{

        public void paintComponent(Graphics g){//绘制版面

            Graphics2D g2d = (Graphics2D)g;  			//background 画图工具
            Color skyblue = new Color(137,180,255);
            Color wheat = new Color(9,6,4);
            GradientPaint gradient = new GradientPaint(0,0,skyblue,0,700,wheat);
            g2d.setPaint(gradient);
            g2d.fillRect(0,0,this.getWidth(),this.getHeight());//填充天空

            Image menu = new ImageIcon("src/images/menu2.png").getImage();		//绘制菜单
            g.drawImage(menu,1060,20,this);

            Color grown = new Color(244,164,96); 		//设置颜色grown
            g.setColor(wheat);
            g.fillRect(0,575,this.getWidth(),this.getHeight());//填充地面

            Image platform = new ImageIcon("src/images/platform.png").getImage();	//draw platform
            g.drawImage(platform,500,250,this);//绘制空中浮萍

            Image spring = new ImageIcon("src/images/spring.png").getImage();	//draw spring
            g.drawImage(spring,750,490,this);//绘制地面的上的跳板

            Image house = new ImageIcon("src/images/stone.gif").getImage();	//draw the house
            g.drawImage(house,520,80,this);//绘制催化神石头

            Image cave = new ImageIcon("src/images/cave.png").getImage();	//draw the house
            g.drawImage(cave,900,400,this);//绘制魔法阵

            Image tree = new ImageIcon("src/images/tree.png").getImage();	//draw the house
            g.drawImage(tree,600,100,this);//绘制魔法阵

            if((player.x>=475&&player.x<=700)&&player.y==180){//当人物在这个位置的时候，弹出进入商店信息
                g.setFont(new Font("Tahoma", Font.BOLD,22));
                Color gray = new Color(105,105,105);
                g.setColor(gray);
                g.drawString("press S to come in",550,80);
            }



            Color grey = new Color(211,211,211);
            g.setFont(new Font("Tahoma", Font.BOLD,24));
            Color gray = new Color(105,105,105);
            g.setColor(gray);
            g.drawString("HP",10,50);
            g.drawString("MP",10,80);
            g.setColor(grey);
            g. fillRoundRect(50,30,500,20,15,15);//绘制空白血条fillRect
            g.fillRoundRect(50,60,500,20,15,15);//绘制空白蓝条
            Color red = new Color(220,48,35);
            g.setColor(red);
            g. fillRoundRect(50,30,(int)(500*(player.HP/(float)player.MAX_HP)),20,15,15);//绘制当前血条
            Color blue = new Color(0,0,255);
            g.setColor(blue);
            g.fillRoundRect(50,60,(int)(500*(player.MP/(float)player.MAX_MP)),20,15,15);//绘制蓝条
            g.setFont(new Font("Tahoma", Font.BOLD,15));
            g.setColor(gray);
            g.drawString(player.HP+"/"+player.MAX_HP,360,45);//打印当前血量信息
            g.drawString(player.MP+"/"+player.MAX_MP,360,75);//打印当前蓝量信息

            Image coin = new ImageIcon("src/images/coin.png").getImage();
            g.drawImage(coin,900,30,this);
            Color gold = new Color(255,165,0);
            g.setColor(gold);
            g.setFont(new Font("Tahoma", Font.BOLD,30));
            g.drawString(" :"+player.money,940,60);//绘制当前金钱数量

//
//            g.setColor(red);
//            g.drawString("killed: "+player.killed,150,130);//绘制当前击杀数

            player.pic = new ImageIcon("src/images/robot_"+player.pic_index+".png").getImage();

            g.drawImage(player.pic,player.x,player.y,this);//绘制主角

            monster.pic = new ImageIcon("src/images/monster_"+monster.pic_index+".png").getImage();
            g.drawImage(monster.pic,monster.x,monster.y,this);					//绘制怪兽

           if(monster.pic_index!=0){
                g.setColor(gray);
                g.fillRect(monster.x,monster.y-10,150,10);//绘制空白血量
                g.setColor(red);
                g.fillRect(monster.x,monster.y-10,(int)(150*(monster.HP/(float)monster.MAX_HP)),10);//绘制当前血量
            }

            if(monster.being_attacked){
                if(player.left){
                    Image hit= new ImageIcon("src/images/hit.png").getImage();	 //draw hit effect
                    g.drawImage(hit,monster.x+80,monster.y+50,this); //被击打效果绘制
                }else{
                    Image hit= new ImageIcon("src/images/hit.png").getImage();
                    g.drawImage(hit,monster.x,monster.y+50,this);
                }
            }


            if(monster.pic_index==0){						//eat coin
                if(monster.x-player.x>=-44&&monster.x-player.x<=44&&monster.y-player.y<=70){
                    player.money=player.money+player.killed;
                    monster.newone(this,player);
                }
            }

            if(monster.attacking){//怪兽攻击
                if(monster.left){
                    Image attack= new ImageIcon("src/images/attack_1.png").getImage();	 //绘制攻击图片
                    g.drawImage(attack,monster.x-100,monster.y+20,this);
                }else{
                    Image attack= new ImageIcon("src/images/attack_0.png").getImage();
                    g.drawImage(attack,monster.x+80,monster.y+20,this);
                }
            }

            if(player.skill){//技能:我要把你变消失

                    Image grost= new ImageIcon("src/images/grost.gif").getImage();	 //绘制技能
                    g.drawImage(grost,monster.x+10,monster.y+20,this);

            }


            if(player.death){

                player.pic_index=11;
                g.setColor(Color.red);
                g.setFont(new Font("Tahoma", Font.BOLD,150));
                g.drawString("Wasted",200,400);//绘制死亡界面
            }

        }

        public void keyTyped(KeyEvent e){ 	//deal with player's input

        }

        public void keyPressed(KeyEvent e){//检测键盘按键
            if(!player.death){
                int key = e.getKeyCode();
                if(key==KeyEvent.VK_D){
                    player.walk_right(this);
                    player.D_pressed=true;

                }
                if(key==KeyEvent.VK_A){
                    player.walk_left(this);
                    player.A_pressed=true;
                }
                if(key==KeyEvent.VK_W||key==KeyEvent.VK_K){		//ready to jump
                    player.jump(this,player);
                }

                if(key==KeyEvent.VK_J){
                    player.to_attacking();
                    if(monster.pic_index!=0)
                        fight();  //everytime press attack , judge fight;
                }

                if(key==KeyEvent.VK_H&&player.MP>=20){  //放大招
                    player.to_skill();
                    if(monster.pic_index!=0)
                        Skill();  //everytime press attack , judge fight;
                }

                if(key==KeyEvent.VK_S&&player.x>=475&&player.x<=700&&player.y==180){
                    shop sp = new shop(player,this);
                    sp.open();
                }//进入商店

                repaint();
            }
        }

        public void keyReleased(KeyEvent e){//检测松开按键的情况
            int key = e.getKeyCode();
            if(key==KeyEvent.VK_K||key==KeyEvent.VK_W){
                //jump ,no rush to stand standard
            }else if(key==KeyEvent.VK_A||key==KeyEvent.VK_D){
                player.A_pressed=false;		player.D_pressed=false;

                // stand there keep its position
            }else if (key==KeyEvent.VK_J){
                player.stand();
                player.attacking =false;
                monster.being_attacked=false;
            }else if(key==KeyEvent.VK_H){
                player.stand();
                player.skill=false;
                monster.being_attacked=false;
            }
            else{
                player.stand();
            }

            repaint();//重新绘制画面
        }

        public void mouseClicked(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            System.out.println("人物x坐标:"+player.x+" "+"人物y坐标:" +player.y+"怪兽的坐标"+monster.x+"怪兽的坐标y"+monster.y);
            if(x>=981&&x<=1176&&y>=57&&y<=398){
                Menu menu = new Menu();
                menu.open();
            }

        }

        public void mousePressed(MouseEvent e){
        }
        public void mouseReleased(MouseEvent e){
        }
        public void mouseEntered(MouseEvent e){
        }
        public void mouseExited(MouseEvent e){
        }

    }
}
