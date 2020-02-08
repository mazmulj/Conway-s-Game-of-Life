package Life;

import java.awt.Color;

public class Life {
    private static int x, y;
    private static int magnification;
    private static Picture pic;
    private static int[][] newCells;
    private static int[][] old;

    public static void main(String[] args) throws InterruptedException {
        int width = Integer.parseInt(args[0]);
        int iter = Integer.parseInt(args[1]);
        String type = args[2];

        int x = width;
        int y = width;
        int mag = 7;

        Life life = new Life(x, y, mag);

        if (type.equals("R")) {
            boardConfig(life);
        }
        else {
        	gosper(life);
        }
        life.Old();
        
        for (int a = 0; a < iter; a++) {
            Thread.sleep(250);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    life.updateCell(i, j);
                }
            }
            life.Old();
            life.show();
        }
    }

    public Life(int x, int y, int magnification) {
        this.x = x;
        this.y = y;
        this.magnification = magnification;
        newCells = new int[x][y];
        pic = new Picture(x * magnification, y * magnification);
        old = new int[x][y];
    }

    private static void birth(int i, int j) {
    	int r=0;
    	int g=0;
    	int b=0;
    	
        Color col = new Color(r,g,b);
        newCells[i][j] = 1;

        for (int X = 0; X < magnification; X++) {
            for (int Y = 0; Y < magnification; Y++) {
                pic.set((i * magnification) + X, (j * magnification) + Y, col);
            }
        }
    }

    private static void death(int i, int j) {
    	int r = 255;
    	int g = 255;
    	int b = 255;
    	
        Color col = new Color(r, g, b);
        newCells[i][j] = 0;

        for (int X = 0; X < magnification; X++) {
            for (int Y = 0; Y < magnification; Y++) {
                pic.set((i * magnification) + X, (j * magnification) + Y, col);
            }
        }
    }

    public static void boardConfig(Life life) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                int random = (int) (Math.random() * 100);
                if ((random % 6) == 0) {
                    life.birth(i, j);
                } else {
                    life.death(i, j);
                }
            }
        }
    }
    
    public static void gosper(Life life) {
        int[] xCord = new int[]{26, 24, 26, 14, 15, 22, 23, 36, 37, 13, 17, 22, 23, 36, 37, 2, 3, 12, 18, 22, 23, 2, 3, 12, 16, 18, 19, 24, 26, 12, 18, 26, 13, 17, 14, 15};
        int[] yCord = new int[]{2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 9, 9, 10, 10};
        for (int i = 0; i < x; i++)
            {
                for (int j = 0; j < x; j++)
                {
                life.death(i,j);            
                }        
            }  
        for (int i = 0; i < xCord.length; i++) {
        birth(xCord[i],yCord[i]);    
        }  
    }

    private void updateCell(int i, int j) {
        int birthCells = 0;
        boolean isbirth = false;
        if (old[i][j] == 1) {
            isbirth = true;
        }
        for (int a = -1; a < 2; a++) {
            for (int b = -1; b < 2; b++) {
                if (a == 0 & b == 0)
                    continue;
                int xCell = i + b;
                int yCell = j + a;
                if (xCell < 1 || xCell > (x - 1) || yCell < 1 || yCell > (y - 1))
                    return;
                if (old[xCell][yCell] == 1)
                    birthCells++;
            }
        }

        if (!isbirth && birthCells == 3) {
            birth(i, j);
        } else if (isbirth && birthCells < 2) {
            death(i, j);
        } else if (isbirth && (birthCells == 2 || birthCells == 3)) {
            birth(i, j);
        } else if (isbirth && (birthCells > 3)) {
            death(i, j);
        }
    }

    public void show() {
        pic.show();
    }
    
    
    
    public void Old(){
        for(int i = 0; i < x; i++) {
        	for(int j = 0; j < x; j++) {
        		old[i][j] = newCells[i][j];
        	}
        }
    }
}
