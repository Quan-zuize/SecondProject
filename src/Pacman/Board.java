package Pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {

    public Connection ketNoi() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/epj2_game", "root", "");
        return con;
    }

    int paclifes = 3;
    int ghosts = 10;

    Dimension d;
    Font smallfont = new Font("Helvetica", Font.BOLD, 14);

    FontMetrics fmsmall, fmlarge;
    Image ii;
    Color dotcolor = new Color(192, 192, 0);
    Color mazecolor;

    boolean ingame = false;
    boolean dying = false;

    final int blocksize = 24;
    final int nrofblocks = 15;
    final int pacanimdelay = 2;
    final int maxghosts = 20;
    final int pacmanspeed = 6;

    int pacanimcount = pacanimdelay;
    int pacmananimpos = 0;
    int nrofghosts = ghosts;
    int pacsleft, score;
    int[] dx, dy;
    int[] ghostx, ghosty, ghostdx, ghostdy, ghostspeed;

    Image ghost;
    Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    Image pacman3up, pacman3down, pacman3left, pacman3right;
    Image pacman4up, pacman4down, pacman4left, pacman4right;

    int pacmanx, pacmany, pacmandx, pacmandy;
    int reqdx, reqdy, viewdx, viewdy;

    final short leveldata[] = {
        19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        21, 0 , 0 , 0 , 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        21, 0 , 0 , 0 , 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 
        21, 0 , 0 , 0 , 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 18, 16, 16, 20, 0 , 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0 , 17, 16, 16, 16, 16, 24, 20,
        25, 16, 16, 16, 24, 24, 28, 0 , 25, 24, 24, 16, 20, 0 , 21,
        1 , 17, 16, 20, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 17, 20, 0 , 21,
        1 , 17, 16, 16, 18, 18, 22, 0 , 19, 18, 18, 16, 20, 0 , 21,
        1 , 17, 16, 16, 16, 16, 20, 0 , 17, 16, 16, 16, 20, 0 , 21,
        1 , 17, 16, 16, 16, 16, 20, 0 , 17, 16, 16, 16, 20, 0 , 21,
        1 , 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0 , 21,
        1 , 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0 , 21,
        1 , 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
        9 , 8 , 8 , 8 , 8 , 8 , 8 , 8 , 8 , 8 , 25, 24, 24, 24, 28};

    final int validspeeds[] = {1, 2, 3, 4, 6, 8};
    final int maxspeed = 6;

    int currentspeed = 3;
    short[] screendata;

    public Board() {
        screendata = new short[nrofblocks * nrofblocks];
        mazecolor = new Color(5, 100, 250);
        setFocusable(true);

        d = new Dimension(400, 400);
        setBackground(Color.black);
        setDoubleBuffered(true);

        ghostx = new int[maxghosts];
        ghostdx = new int[maxghosts];
        ghosty = new int[maxghosts];
        ghostdy = new int[maxghosts];
        ghostspeed = new int[maxghosts];
        dx = new int[4];
        dy = new int[4];
    }

    public void isDeath() {
        pacsleft--;
        if (pacsleft == 0) {
            ingame = false;
        }
        continueLevel();
    }

    public void playGame(Graphics2D g2d) {
        if (dying) {
            isDeath();
        } else {
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    public void movePacman() {
        int pos;
        short ch;

        if (reqdx == -pacmandx && reqdy == -pacmandy) {
            pacmandx = reqdx;
            pacmandy = reqdy;
            viewdx = pacmandx;
            viewdy = pacmandy;
        }

        if (pacmanx % blocksize == 0 && pacmany % blocksize == 0) {
            pos = pacmanx / blocksize + nrofblocks * (int) (pacmany / blocksize);
            ch = screendata[pos];

            if ((ch & 16) != 0) {
                screendata[pos] = (short) (ch & 15);
                score++;
            }

            if (reqdx != 0 || reqdy != 0) {
                if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                        || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                        || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                        || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {

                    pacmandx = reqdx;
                    pacmandy = reqdy;
                    viewdx = pacmandx;
                    viewdy = pacmandy;
                }
            }

            // Check for standstill
            if ((pacmandx == -1 && pacmandy == 0 && (ch & 1) != 0)
                    || (pacmandx == 1 && pacmandy == 0 && (ch & 4) != 0)
                    || (pacmandx == 0 && pacmandy == -1 && (ch & 2) != 0)
                    || (pacmandx == 0 && pacmandy == 1 && (ch & 8) != 0)) {
                pacmandx = 0;
                pacmandy = 0;
            }
        }
        pacmanx = pacmanx + pacmanspeed * pacmandx;
        pacmany = pacmany + pacmanspeed * pacmandy;
    }

    public void drawPacman(Graphics2D g2d) {
    }

    public void moveGhosts(Graphics2D g2d) {
        short i;
        int pos;
        int count;

        for (i = 0; i < nrofghosts; i++) {
            if (ghostx[i] % blocksize == 0 && ghosty[i] % blocksize == 0) {
                pos = ghostx[i] / blocksize + nrofblocks * (int) (ghosty[i] / blocksize);
                count = 0;
                if ((screendata[pos] & 1) == 0 && ghostdx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }
                if ((screendata[pos] & 2) == 0 && ghostdy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }
                if ((screendata[pos] & 4) == 0 && ghostdx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }
                if ((screendata[pos] & 8) == 0 && ghostdy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {
                    if ((screendata[pos] & 15) == 15) {
                        ghostdx[i] = 0;
                        ghostdy[i] = 0;
                    } else {
                        ghostdx[i] = -ghostdx[i];
                        ghostdy[i] = -ghostdy[i];
                    }
                } else {
                    count = (int) (Math.random() * count);
                    if (count > 3) {
                        count = 3;
                    }
                    ghostdx[i] = dx[count];
                    ghostdy[i] = dy[count];
                }
            }
            ghostx[i] = ghostx[i] + (ghostdx[i] * ghostspeed[i]);
            ghosty[i] = ghosty[i] + (ghostdy[i] * ghostspeed[i]);
            drawGhost(g2d, ghostx[i] + 1, ghosty[i] + 1);

            if (pacmanx > (ghostx[i] - 12) && pacmanx < (ghostx[i] + 12)
                    && pacmany > (ghosty[i] - 12) && pacmany < (ghosty[i] + 12)
                    && ingame) {
                dying = true;
            }
        }
    }

    public void checkMaze() {
        short i = 0;
        boolean finished = true;

        while (i < nrofblocks * nrofblocks && finished) {
            if ((screendata[i] < 28) != false) {
                finished = false;
            }
            i++;
        }

        if (finished) {
            System.out.println("You  win");
            score += 50;

            if (nrofghosts < maxghosts) {
                nrofghosts++;
            }
            if (currentspeed < maxspeed) {
                currentspeed++;
            }
            initLevel();
        }
    }

    public void initLevel() {
        int i;
        for (i = 0; i < nrofblocks * nrofblocks; i++) {
            screendata[i] = leveldata[i];
        }

        continueLevel();
    }

    public void continueLevel() {
        short i;
        int dx = 1;
        int random;

        for (i = 0; i < nrofghosts; i++) {
            ghosty[i] = 4 * blocksize;
            ghostx[i] = 4 * blocksize;
            ghostdy[i] = 0;
            ghostdx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentspeed + 1));
            if (random > currentspeed) {
                random = currentspeed;
            }
            ghostspeed[i] = validspeeds[random];
        }

        pacmanx = 7 * blocksize;
        pacmany = 11 * blocksize;
        pacmandx = 0;
        pacmandy = 0;
        reqdx = 0;
        reqdy = 0;
        viewdx = -1;
        viewdy = 0;
        dying = false;
    }

    public void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
