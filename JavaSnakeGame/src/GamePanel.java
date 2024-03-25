import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener {

    static final int screenWidth=800;
    static final int screenHeight=800;

    static final int unitSize=25;
    static final int gameUnits=(screenWidth*screenHeight)/unitSize;
    static final int delay=125;

    final int x[]=new int[gameUnits];
    final int y[]=new int[gameUnits];

    int bodyparts=6;
    int appleEaten=0;
    int applex;
    int appley;
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;


    GamePanel(){
        random =new Random();
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new mykeyadaptor());

        StartGame();

    }
    public void StartGame(){
        newApple();
        running=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);
    }
    public void draw(Graphics g){

        if(running){

            for (int i=0;i<screenHeight/unitSize;i++){
                g.drawLine(i*unitSize,0,i*unitSize,screenHeight);
                g.drawLine(0,i*unitSize,screenHeight,i*unitSize);
            }
            g.setColor(Color.RED);
            g.fillOval(applex,appley,unitSize,unitSize);

            for (int i=0;i<bodyparts;i++){
                if(i==0){
                    g.setColor(Color.BLUE);
                    g.fillRect(x[i],y[i],unitSize,unitSize);
                }else {
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i],y[i],unitSize,unitSize);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("",1,40));
            FontMetrics metrix=getFontMetrics(g.getFont());
            g.drawString("Score - "+appleEaten,(screenWidth-metrix.stringWidth("Score - "))/2,g.getFont().getSize());


        }else {
            gameOver(g);
        }


    }
    public void newApple(){
        applex = random.nextInt((int)(screenWidth/unitSize))*unitSize;
        appley=random.nextInt((int)(screenHeight/unitSize))*unitSize;
    }

    public void move(){
        for (int i=bodyparts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U':
                y[0]=y[0]-unitSize;
                break;
            case 'D':
                y[0]=y[0]+unitSize;
                break;
            case 'L':
                x[0]=x[0]-unitSize;
                break;
            case 'R':
                x[0]=x[0]+unitSize;
                break;
        }
    }
    public void checkapple(){
            if((x[0]==applex)&&(y[0]==appley)){
                bodyparts++;
                appleEaten++;
                newApple();
            }
    }
    public void checkCollision(){
        for(int i=bodyparts;i>0;i--){
            if ((x[0]==x[i])&&(y[0]==y[i])){
                running=false;
            }
        }
        if (x[0]<0){
            running=false;
        }
        if (x[0]>screenWidth){
            running=false;
        }
        if (y[0]<0){
            running=false;
        }
        if (y[0]>screenHeight){
            running=false;
        }
        if (!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //game Over
        g.setColor(Color.RED);
        g.setFont(new Font("",1,75));
        FontMetrics metrix1=getFontMetrics(g.getFont());
        g.drawString("Game Over",(screenWidth-metrix1.stringWidth("Game Over"))/2,screenHeight/2);

//        score
        g.setColor(Color.RED);
        g.setFont(new Font("",1,40));
        FontMetrics metrix2=getFontMetrics(g.getFont());
        g.drawString("Score - "+appleEaten,(screenWidth-metrix2.stringWidth("Score - "))/2,g.getFont().getSize());


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkapple();
            checkCollision();

        }
        repaint();
    }
    public class mykeyadaptor extends KeyAdapter{
            public void keyPressed(KeyEvent e){
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_LEFT:
                            if (direction !='R'){
                                direction='L';
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction !='L'){
                                direction='R';
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (direction !='D'){
                                direction='U';
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction !='U'){
                                direction='D';
                            }
                            break;
                    }
            }
    }
}
