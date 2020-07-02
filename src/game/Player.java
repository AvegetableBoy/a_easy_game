package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
class Player extends character{

    boolean on_platform;
    boolean A_pressed;
    boolean D_pressed;
    boolean death;

    int MAX_MP;
    int MP;
    int MAX_XP;
    int XP;
    int money ;
    double HP_recovery;
    double MP_recovery;
    int killed;
    int body;//体质



    Player(){		//角色的初始属性
        x=30;
        y=490;
        pic_index=0;
        MAX_HP=100;
        HP=MAX_HP;
        MAX_MP=100;
        MP=0;
        attack=10;
        money = 20;
        HP_recovery=0.01;//恢复率
        MP_recovery=0.02;//恢复率
    }

    public void recovery(JPanel jp){//血量恢复进程
        Runnable thread_job = new player_recovery(jp);
        Thread recover = new Thread(thread_job);
        recover.start();
    }



    class player_recovery implements Runnable{
        JPanel jp;
        player_recovery(JPanel jp){
            this.jp=jp;
        }
        public void run(){
            while(HP!=0){
                HP=(int)(HP+HP_recovery*MAX_HP);
                MP=(int)(MP+MP_recovery*MAX_MP);
                if(HP>=MAX_HP)HP=MAX_HP;
                if(MP>=MAX_MP)MP=MAX_MP;
                jp.repaint();
                try{
                    Thread.sleep(2000);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public void stand(){
        if(left) pic_index=1;
        else pic_index=0;
    }
    public void walk_right(JPanel jp){
        x=x+10;
        if(x>1130)x=1130;	//in case of over the boundary
        left = false;
        pic_index++;
        if(pic_index>3||pic_index<2)pic_index=2;
//        pic_index=3;
        if(x>900&&on_platform){				//fall down from the platform
            on_platform = false;
            fall_platform(this,jp);
        }
    }
    public void walk_left(JPanel jp){
        x=x-10;
        if(x<-50)x=-50;	//in case of over the boundary
        left = true;
        pic_index++;
        if(pic_index>5||pic_index<4)pic_index=4;
//        pic_index=2;
        if(x<475&&on_platform){
            on_platform=false;
            fall_platform(this,jp);
        }
    }
    public void to_attacking(){
        attacking = true;
        if(left)pic_index=7;
        else pic_index=6;
    }

    public void to_skill(){
        skill = true;
         pic_index=10;
    }


    public void jump(JPanel jp,Player player){
        class jump extends TimerTask{
            boolean down;
            public void run(){
                if(player.on_platform){			//on the platform
                    if(A_pressed)player.x=x-5;
                    if(x<-50)x=-50;
                    if(D_pressed)player.x=x+5;
                    if(x>900)x=900;
                    if(down==false){//升空过程
                        player.y=player.y-10;
                        jp.repaint();
                        if(player.y<=55)down=true;
                    }else {
                        player.y=player.y+10;
                        jp.repaint();
                        if(player.y>=180){

                            if(player.left){
                                player.pic_index=1;
                            }else{
                                player.pic_index=0;
                            }
                            down = false;
                            if(x<475||x>895){
                                on_platform=false;
                                fall_platform(player,jp);
                            }
                            else{
                                player.y=180;
                            }
                            jp.repaint();
                            this.cancel();
                        }
                    }
                }else{
                    if(player.x<710||player.x>880){		//in the ground
                        if(A_pressed)player.x=x-5;
                        if(x<-50)x=-50;
                        if(D_pressed)player.x=x+5;
                        if(x>1130)x=1130;
                        if(down==false){
                            player.y=player.y-10;
                            jp.repaint();
                            if(player.y<=230)down=true;
                        }else {
                            player.y=player.y+10;
                            jp.repaint();
                            if(player.y>=490){
                                player.y=490;//到地面了
                                if(player.left){
                                    player.pic_index=1;
                                }else{
                                    player.pic_index=0;
                                }
                                down = false;
                                jp.repaint();
                                this.cancel();
                            }
                        }

                    }else{				//在弹簧上
                        if(player.D_pressed==false&&player.A_pressed==false){
                            if(down==false){
                                player.y=player.y-10;
                                jp.repaint();
                                if(player.y<=100)down=true;
                            }else{
                                player.y=player.y+10;
                                jp.repaint();
                                if(player.y>=180){
                                    player.y=180;//平台高度
                                    if(player.left){
                                        player.pic_index=1;
                                    }else{
                                        player.pic_index=0;
                                    }
                                    down = false;
                                    jp.repaint();
                                    player.on_platform =true;
                                    this.cancel();
                                }
                            }
                        }else{

                            if(A_pressed)player.x=x-5;
                            if(x<-50)x=-50;
                            if(D_pressed)player.x=x+5;
                            if(x>1130)x=1130;
                            if(down==false){
                                player.y=player.y-10;
                                jp.repaint();
                                if(player.y<=230)down=true;
                            }else {
                                player.y=player.y+10;
                                jp.repaint();
                                if(player.y>=490){
                                    player.y=490;
                                    if(player.left){
                                        player.pic_index=1;
                                    }else{
                                        player.pic_index=0;
                                    }
                                    down = false;
                                    jp.repaint();
                                    this.cancel();
                                }
                            }

                        }
                    }
                }
            }
        }
        if(player.left)player.pic_index=9;
        else player.pic_index=8;
        Timer jump = new Timer();
        jump.schedule(new jump(),0,15);//定时执行
    }

    public void fall_platform(Player player , JPanel jp){
        class fall extends TimerTask{
            public void run(){
                if(A_pressed)player.x=x-5;
                if(x<-50)x=-50;
                if(D_pressed)player.x=x+5;
                if(x>1130)x=1130;

                player.y=player.y+10;
                jp.repaint();
                if(player.y>=490){
                    player.y=490;//掉到地面
                    if(player.left){
                        player.pic_index=1;
                    }else{
                        player.pic_index=0;
                    }
                    jp.repaint();
                    this.cancel();
                }
            }
        }

        if(player.left)player.pic_index=9;
        else player.pic_index=8;
        Timer fall = new Timer();
        fall.schedule(new fall(),0,15);
    }
}