package game;
import javax.swing.*;

class Monster extends character{
    Monster(){
        pic_index=1;
        x=800;y=440;
        left=true;
        attack =5;
        MAX_HP=30;
        HP=MAX_HP;

    }

    public void death(){
        pic_index=0;	// change to coin
        x=x+30;
        y=y+70;
    }

    public void move(JPanel jp,Player player){
        Runnable thread_job = new monster_move(jp,player);
        Thread mon = new Thread(thread_job);
        mon.start();
    }

    public void newone(JPanel jp,Player player){

        pic_index=(int)(Math.random()*10)%3+1;//随机产生一个怪物
        MAX_HP=(int)(MAX_HP*1.2);
        HP=MAX_HP;
        x=800;y=440;//440
        left=true;
        attack = (int)(attack*1.2);
        move(jp,player);
    }

    class monster_move implements Runnable{
        JPanel jp;
        Player player;
        monster_move(JPanel jp , Player player){
            this.jp=jp;
            this.player=player;
        }
        public void run(){
            while(pic_index!=0){
                if(left){
                    x=x-2;
                    jp.repaint();
                    if(x<=0){
                        x=0;
                        left =false;
                    }
                    try{
                        Thread.sleep(10);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
                if(!left){
                    x=x+2;
                    jp.repaint();
                    if(x>1000){
                        x=1000;
                        left=true;
                    }
                    try{
                        Thread.sleep(10);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }

                }
                if(((int)(Math.random()*100))>=99){		//attack

                    attacking = true;
                    if((player.y-y)>=-30){
                        if((left&&(x-player.x)>=-60&&(x-player.x)<=120)||(!left&&(x-player.x)>=-60&&(x-player.x)<=120)){
                            player.HP=player.HP-attack;
                            if(player.HP<=0){
                                player.HP=0;		//death here;
                                player.death=true;
                            }
                        }
                    }

                    for(int i = 0;i<10;++i){
                        if(left)x=x-2;
                        else x=x+2;
                        jp.repaint();
                        try{
                            Thread.sleep(30);
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    attacking = false;
                }


            }

        }
    }
}
