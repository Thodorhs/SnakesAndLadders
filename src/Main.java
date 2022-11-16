import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static int musicplaying=1;
    public static void playmusic() {
        try {
            File musicpath = new File(System.getProperty("user.dir")+"\\music\\roll.wav");
            AudioInputStream audioin = AudioSystem.getAudioInputStream(musicpath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioin);
            clip.start();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        {
        }
    }
    static Clip clipp;
    public static void playbackroundmusic() {
        try {
            File musicpath1 = new File(System.getProperty("user.dir")+"\\music\\space.wav");
            AudioInputStream audioin = AudioSystem.getAudioInputStream(musicpath1);
            clipp = AudioSystem.getClip();
            clipp.open(audioin);
            clipp.start();
            clipp.loop(clipp.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        {
        }

    }

    public static int pos[] = new int[2];
    public static int turn;
    private static int lad[][] = new int[6][2];
    private static int sn[][] = new int[7][2];
    public static String players[] = new String[2];

    public static void main(String[] args) {
        pos[0] = 0;
        pos[1] = 0;
        System.out.println("Enter Players names :");
        Scanner scanner = new Scanner(System.in);
        players[0] = scanner.nextLine();
        scanner = new Scanner(System.in);
        players[1] = scanner.nextLine();
        make_lad();
        make_sn();
        Random rand = new Random();
        turn = rand.nextInt(2);
        gui(players, turn);
    }

    public static void make_lad() {
        lad[0][0] = 3;
        lad[0][1] = 51;
        lad[1][0] = 6;
        lad[1][1] = 27;
        lad[2][0] = 20;
        lad[2][1] = 70;
        lad[3][0] = 36;
        lad[3][1] = 55;
        lad[4][0] = 63;
        lad[4][1] = 95;
        lad[5][0] = 68;
        lad[5][1] = 98;
    }

    public static void make_sn() {
        sn[0][0] = 25;
        sn[0][1] = 5;
        sn[1][0] = 34;
        sn[1][1] = 1;
        sn[2][0] = 47;
        sn[2][1] = 19;
        sn[3][0] = 65;
        sn[3][1] = 57;
        sn[4][0] = 87;
        sn[4][1] = 57;
        sn[5][0] = 91;
        sn[5][1] = 61;
        sn[6][0] = 99;
        sn[6][1] = 69;
    }

    public static int dice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    //refreshing gui (changing place of pawns referensed from the pos[] array)
    public static void RefreshGui(int pos[], int turn) {
        if (turn == 0) {
            if ((pos[0] % 10) == 0) {
                p1.setBounds(14 + (9 * 80), 556 - ((pos[0] / 10) - 1) * 60, 60, 45);
            } else {
                p1.setBounds(14 + (((pos[0] % 10) - 1) * 80), 556 - (pos[0] / 10) * 60, 60, 45);
            }

        } else {
            if ((pos[1] % 10) == 0) {
                p2.setBounds(14 + (9 * 80), 556 - ((pos[1] / 10) - 1) * 60, 60, 45);
            } else {
                p2.setBounds(14 + (((pos[1] % 10) - 1) * 80), 556 - (pos[1] / 10) * 60, 60, 45);
            }
        }

    }
    //checking if we are on a ladder
    public static void checklad() {
        int flag = 0;
        for (int i = 0; i < 6; i++) {
            if (pos[turn] == lad[i][0]) {

                pos[turn] = lad[i][1];
                flag = 1;
            }
        }
        JLabel messages;
        messages = new JLabel();
        if (flag == 0) {
            messages.setVisible(false);
        } else {


            messages.setFont(new Font("Rockwell Extra Bold", 0, 25));
            messages.setBounds(800, 130, 250, 30);


            messages.setText("and hit a ladder");
            window.add(messages);
            messages.setVisible(true);
            window.validate();

        }
        window.validate();
    }
    //checking if we are on a snake
    public static void checksn() {
        int flag = 0;
        for (int i = 0; i < 7; i++) {
            if (pos[turn] == sn[i][0]) {
                pos[turn] = sn[i][1];
                flag = 1;
            }
        }
        JLabel messages;
        messages = new JLabel();
        if (flag == 0) {
            messages.setVisible(false);
        } else {

            messages.setFont(new Font("Rockwell Extra Bold", 0, 25));
            messages.setBounds(800, 130, 250, 30);


            messages.setText("and hit a snake");
            window.add(messages);
            messages.setVisible(true);
            window.validate();
        }
        window.validate();
    }


    /**
     * <b>constructor</b>: Creates a new Window frame and creates some buttons and panels <br />
     * <b>postconditions</b>: Creates a new Window frame and creates some buttons and panels
     * starting a new game.
     */
    public static String turnname = "";
    public static JFrame window;
    public static JLayeredPane board;
    public static JLabel background;
    public static JLabel player;
    public static JLabel p1;
    public static JLabel p2;
    public static JLabel ver;
    public static JLabel playersturn;
    public static JLabel rollmessages;
    public static JButton dice;
    public static JButton mute;
    public static JTextField text;

    private static void gui(String players[], int turnm) {
        playbackroundmusic();//starting music
        //initializing
        window = new JFrame("Ladders and snakes");
        board = new JLayeredPane();
        background = new JLabel();
        player = new JLabel(turnname);
        player.setFont(new Font("Rockwell Extra Bold", 0, 25));
        playersturn = new JLabel();
        playersturn.setFont(new Font("Rockwell Extra Bold", 0, 25));
        mute = new JButton();
        rollmessages = new JLabel();
        rollmessages.setFont(new Font("Rockwell Extra Bold", 0, 25));
        dice = new JButton("Roll Dice");
        text = new JTextField();
        p1 = new JLabel();
        p2 = new JLabel();
        ver=new JLabel();
        //window and board
        window.setBounds(0, 0, 1150, 750);
        board.setBounds(0, 0, 800, 610);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon("images_2020/board.png");
        Image img = image.getImage();
        Image inscaled = img.getScaledInstance(800, 610, Image.SCALE_SMOOTH);
        ImageIcon scaled = new ImageIcon(inscaled);
        background.setIcon(scaled);
        background.setOpaque(false);
        background.setBounds(0, 0, 800, 610);
        background.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));


        //players pawns adding images
        ImageIcon image1 = new ImageIcon("images_2020/blue.png");
        Image img1 = image1.getImage();
        Image inscaled1 = img1.getScaledInstance(60, 45, Image.SCALE_SMOOTH);
        ImageIcon scaled2 = new ImageIcon(inscaled1);
        p2.setIcon(scaled2);
        p2.setVisible(true);
        ImageIcon image2 = new ImageIcon("images_2020/red.png");
        Image img2 = image2.getImage();
        Image inscaled2 = img2.getScaledInstance(60, 45, Image.SCALE_SMOOTH);
        ImageIcon scaled3 = new ImageIcon(inscaled2);
        p1.setIcon(scaled3);
        p1.setVisible(true);
        ver.setBounds(10,684,500,25);
        ver.setFont(new Font("Rockwell Extra Bold", 0, 13));
        //buttons and scalling images
        dice.setBounds(920, 510, 80, 80);
        ImageIcon image5 = new ImageIcon("images_2020/dice.png");
        Image img5 = image5.getImage();
        Image inscaled5 = img5.getScaledInstance(85, 80, Image.SCALE_SMOOTH);
        ImageIcon scaled5 = new ImageIcon(inscaled5);
        dice.setIcon(scaled5);
        dice.setOpaque(true);
        dicelistener draw1 = new dicelistener();
        dice.addActionListener(draw1);

        ImageIcon image3 = new ImageIcon("images_2020/mute.png");
        Image img3 = image3.getImage();
        Image inscaled3 = img3.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon scaled4 = new ImageIcon(inscaled3);
        mute.setBounds(1100, 680, 25, 25);
        mute.setIcon(scaled4);
        mutelistener mute1=new mutelistener();
        mute.addActionListener(mute1);
        //text
        player.setBounds(800, 40, 150, 30);
        player.setOpaque(false);
        playersturn.setBounds(800, 5, 150, 30);
        playersturn.setOpaque(false);

        rollmessages.setBounds(800, 90, 250, 30);
        rollmessages.setVisible(true);
        //adding to window frame
        window.add(player);
        window.add(ver);
        window.add(playersturn);
        window.add(rollmessages);
        board.add(background);
        window.add(p2);
        window.add(p1);
        window.add(dice);
        window.add(mute);
        window.add(board);

        turnname = players[turnm];
        player.setText(turnname);
        //choosing to display red or blue name
        if (turn == 1) {
            player.setForeground(Color.blue);
        } else {
            player.setForeground(Color.red);
        }
        playersturn.setText("Now playing :");
        ver.setText("Version : Alpha 1.0");
        window.setVisible(true);

    }

    private static class dicelistener implements ActionListener {
        @Override
        /**
         * <b>Transformer</b>: when draw tile is clicked it checks if the player has already draw otherwise it  calls function
         * from board that draws 4 tiles from the bag and puts them in the board.
         * <b>Postcondition</b>: adds 4 tiles to the board
         */
        public void actionPerformed(ActionEvent e) {
            playmusic();
            int roll = dice();
            System.out.println(pos[turn]);
            rollmessages.setText(players[turn] + " rolled " + String.valueOf(roll));
            window.validate();
            pos[turn] = pos[turn] + roll;
            System.out.println(pos[turn]);
            if (pos[0] >= 100 || pos[1] >= 100) {
                dice.setVisible(false);
                JLabel winner;
                winner = new JLabel();
                JFrame winnerframe;
                winnerframe = new JFrame();
                winnerframe.setBounds(650, 400, 300, 150);
                winnerframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                winner.setFont(new Font("Rockwell Extra Bold", 0, 25));
                winner.setBounds(845, 510, 250, 50);
                winner.setText("Winner is " + players[turn] + " !!!");
                winner.setOpaque(true);

                winnerframe.add(winner);
                winnerframe.setVisible(true);
                winner.validate();
            }
            checklad();
            checksn();
            RefreshGui(pos, turn);
            if (turn == 1) {
                turn--;
                turnname = players[turn];
                player.setText(turnname);
                player.setForeground(Color.red);
                window.validate();
            } else {
                turn++;
                turnname = players[turn];
                player.setText(turnname);
                player.setForeground(Color.blue);
                window.validate();
            }
        }
    }

    private static class mutelistener implements ActionListener {
        @Override

        public void actionPerformed(ActionEvent e) {
            if(musicplaying==1){
                musicplaying=0;
                clipp.stop();
            }else{
                musicplaying=1;
                playbackroundmusic();
            }
        }
    }
}

