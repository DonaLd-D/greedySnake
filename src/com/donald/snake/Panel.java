package com.donald.snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Panel extends JPanel implements KeyListener, ActionListener {
    ImageIcon title;
    ImageIcon snakeHead;
    ImageIcon snakeBody;
    ImageIcon snakeFood;
    private ArrayList<int[]> list;

    private boolean isStarted;
    private Timer t=new Timer(150,this);
    private char dir='r';

    private int[] food=new int[2];

    private Random rand=new Random();
    private int score;
    public void loadImage(){
        InputStream is;
        try{
            is=getClass().getClassLoader().getResourceAsStream("header.png");
            title=new ImageIcon(ImageIO.read(is));
            is=getClass().getClassLoader().getResourceAsStream("snake_body.png");
            snakeBody=new ImageIcon(ImageIO.read(is));
            is=getClass().getClassLoader().getResourceAsStream("snake_food.png");
            snakeFood=new ImageIcon(ImageIO.read(is));
            is=getClass().getClassLoader().getResourceAsStream("snake_head.png");
            snakeHead=new ImageIcon(ImageIO.read(is));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void init(){
        list=new ArrayList<>();
        list.add(new int[]{100,100});
        list.add(new int[]{85,100});
        list.add(new int[]{70,100});
        isStarted=false;
        dir='r';
        score=0;
        makeFood();
    }
    public void makeFood(){
        food[0]=25+25*rand.nextInt(34);
        food[1]=75+25*rand.nextInt(24);
    }
    public Panel(){
        this.setFocusable(true);
        this.addKeyListener(this);
        loadImage();
        init();
    }
    public void paint(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.pink);
        title.paintIcon(this,g,0,0);
        g.fillRect(25,75,850,600);
        for(int i=0;i< list.size();i++){
            int x=list.get(i)[0],y=list.get(i)[1];
            if(i==0) snakeHead.paintIcon(this,g,x,y);
            else snakeBody.paintIcon(this,g,x,y);
        }
        g.setColor(Color.orange);
        g.setFont(new Font("arial",Font.BOLD,20));
        StringBuilder sb=new StringBuilder();
        sb.append("总成绩是：");
        sb.append(score);
        g.drawString(sb.toString(),700,25);
        if(!isStarted){
            g.setFont(new Font("arial",Font.BOLD,40));
            g.drawString("按下空格键开始游戏！",250,300);
            t.stop();
        }else{
            snakeFood.paintIcon(this,g,food[0],food[1]);
            t.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int x=e.getKeyCode();
        if(x==KeyEvent.VK_SPACE){
            isStarted=!isStarted;
            repaint();
        }else if(x==KeyEvent.VK_RIGHT){
            if(dir=='l'){
                init();
                repaint();
            }else dir='r';
        }else if(x==KeyEvent.VK_LEFT){
            if(dir=='r'){
                init();
                repaint();
            }else dir='l';
        }else if(x==KeyEvent.VK_UP){
            if(dir=='d'){
                init();
                repaint();
            }else dir='u';
        }else if(x==KeyEvent.VK_DOWN){
            if(dir=='u'){
                init();
                repaint();
            }else dir='d';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int lx=0,ly=0;
        for(int i= list.size()-1;i>= 1;i--){
            if(i== list.size()-1){
                lx=list.get(i)[0];
                ly=list.get(i)[1];
            }
            list.set(i,list.get(i-1));
        }
        int x=list.get(0)[0],y=list.get(0)[1];
        if(dir=='r'){
            x+=15;
        }else if(dir=='l'){
            x-=15;
        }else if(dir=='u'){
            y-=15;
        }else{
            y+=15;
        }
        if(x>=860||x<=25||y<75||y>675) init();
        else{
            if(Math.abs(x-food[0])<8&&Math.abs(y-food[1])<8){
                list.add(new int[]{lx,ly});
                score++;
                makeFood();
            }
            list.set(0,new int[]{x,y});
        }
        repaint();
    }
}
