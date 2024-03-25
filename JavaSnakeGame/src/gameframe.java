import javax.swing.*;

public class gameframe extends JFrame {
    gameframe(){
        GamePanel panel=new GamePanel();
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocation(null);

    }
}
