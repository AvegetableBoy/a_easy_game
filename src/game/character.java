package game;
import java.awt.*;

//人物模型的基本属性
public class character {
    int x;  	//横向位置
    int y;      //纵向位置
    boolean left;  // 标记他的移动方式
    int pic_index;	// player:0 for right stand, 1 for left ; 2 and 3 for right walk,
    Image pic;		//4 and 5 for left ; 6 for right attack ,7 for left; 8 for right jump, 9 for left ;
    boolean being_attacked;//被攻击
    boolean attacking;//攻击
    boolean skill;//是否在释放技能

    int attack;    // attribute of character
    int MAX_HP;    //蓝量
    int HP;//血量


}
