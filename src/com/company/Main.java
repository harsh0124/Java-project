package com.company;

public  class  Main {

    private static int reward = -1;
    private static int flag = 0;
    private static int[][] grid;
    private static int[][] grid_policy;
    private static double[][] grid_val1;
    private static double[][] grid_val2;

    public static void main(String[] args) {

        System.out.println("Hello");
        int it =0;
        grid = new int[][]{{0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 9, 10, 11},
                {12, 13, 14, 0}};
        grid_val1 = new double[][]{{0.0,0.0,0.0,0.0},
                {0.0,0.0,0.0,0.0},
                {0.0,0.0,0.0,0.0},
                {0.0,0.0,0.0,0.0}};

        grid_val2 = new double[][]{{0.0,0.0,0.0,0.0},
                {0.0,0.0,0.0,0.0},
                {0.0,0.0,0.0,0.0},
                {0.0,0.0,0.0,0.0}};

        grid_policy = new int[][]{{1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},};

        while(flag == 0)
        {
            start();
            System.out.println("Iteration :" + ++it);
            for(int a=0;a<4;a++)
            {
                for(int b =0;b<4;b++)
                {
                    System.out.print(grid_val1[a][b] + " ");
                }

                System.out.print("\n");
            }
            update_policy();

            for(int x =0;x<14;x++)
            {
                for(int y=0;y<4;y++)
                {
                    System.out.print(grid_policy[x][y] + " ");
                }
                System.out.println();
            }

            System.out.println();
            System.out.println();
            System.out.println();
        }


    }

    private static void start()
    {
        int i,j;
        for(i=0;i<4;i++)
        {
            for(j=0;j<4;j++)
            {

                if(i == 0 && j ==0)
                {
                    j++;
                    double temp = state_value(i, j);
                    grid_val2[i][j] = temp;
                }
                else if(i==3 && j==3 )
                {
                    continue;
                }
                else
                {
                    double temp = state_value(i, j);
                    grid_val2[i][j] = temp;
                }

            }
        }

        for(int a=0;a<4;a++)
        {
            for(int b =0;b<4;b++)
            {
                if(check_converge())
                {
                    grid_val1[a][b] = grid_val2[a][b];
                    grid_val2[a][b]=0;
                }
                else
                {
                    flag = 1;
                }
            }
        }

    }


    static double state_value(int i, int j) {
        if (i == 0 && j !=3) {
            
            double x = (4 * reward + grid_val1[i][j+1] + grid_val1[i+1][j] + grid_val1[i][j-1] + grid_val1[i][j]) / 4.0;
            return x;
        }

        else if (i == 3 && j !=0) {

            
            double x = (4 * reward + grid_val1[i][j+1] + grid_val1[i-1][j] + grid_val1[i][j-1] + grid_val1[i][j]) / 4.0;
            return x;
        }

        else if (j == 0 && i!=3) {
            
            double x = (4 * reward + grid_val1[i+1][j] + grid_val1[i][j+1] + grid_val1[i-1][j] + grid_val1[i][j]) / 4.0;
            return x;
        }
        else if (j == 3 && i!=0) {
            
            double x = (4 * reward + grid_val1[i+1][j] + grid_val1[i][j-1] + grid_val1[i-1][j] + grid_val1[i][j]) / 4.0;
            return x;
        }
        else if(i==3 && j==0)
        {
            
            double x = (4 * reward + grid_val1[i][j+1] + grid_val1[i-1][j] + grid_val1[i][j] + grid_val1[i][j]) / 4.0;
            return x;
        }
        else if(i==0 && j==3)
        {
            
            double x = (4 * reward + grid_val1[i][j-1] + grid_val1[i+1][j] + grid_val1[i][j] + grid_val1[i][j]) / 4.0;
            return x;
        }

        else
        {
            
            double x = (4 * reward + grid_val1[i][j-1] + grid_val1[i][j+1] + grid_val1[i-1][j] + grid_val1[i+1][j]) / 4.0;
            return x;
        }

    }

    private static void update_policy() {

        double[] temp = new double[4];
        for(int i=0;i<4;i++)
        {
            for(int j =0;j<4;j++)
            {
                if(i==0 && j==0)
                {
                    j++;
                }
                if(i==0 && j<3)
                {

                    int k=0;
                    temp[k++] = grid_val1[i][j+1];
                    temp[k++] = grid_val1[i][j-1];
                    temp[k++] = grid_val1[i][j];
                    temp[k] = grid_val1[i+1][j];
                    find_policy(temp, i,j);
                }
                else if(i==3 && 0<j && j<3)
                {
                    int k=0;
                    temp[k++] = grid_val1[i][j+1];
                    temp[k++] = grid_val1[i][j-1];
                    temp[k++] = grid_val1[i-1][j];
                    temp[k] = grid_val1[i][j];
                    find_policy(temp, i,j);
                }
                else if(j == 0 && i<3)
                {

                    int k=0;
                    temp[k++] = grid_val1[i][j+1];
                    temp[k++] = grid_val1[i][j];
                    temp[k++] = grid_val1[i-1][j];
                    temp[k] = grid_val1[i+1][j];
                    find_policy(temp, i,j);
                }
                else if(j==3 && i>0 && i<3)
                {
                    int k=0;
                    temp[k++] = grid_val1[i][j];
                    temp[k++] = grid_val1[i][j-1];
                    temp[k++] = grid_val1[i-1][j];
                    temp[k] = grid_val1[i+1][j];
                    find_policy(temp, i,j);
                }
                else if(i==3 && j ==0)
                {
                    int k=0;
                    temp[k++] = grid_val1[i][j+1];
                    temp[k++] = grid_val1[i][j];
                    temp[k++] = grid_val1[i-1][j];
                    temp[k] = grid_val1[i][j];
                    find_policy(temp, i,j);
                }
                else if(i==0 && j==3)
                {
                    int k=0;
                    temp[k++] = grid_val1[i][j];
                    temp[k++] = grid_val1[i][j-1];
                    temp[k++] = grid_val1[i][j];
                    temp[k] = grid_val1[i+1][j];
                    find_policy(temp, i,j);
                }

                else if(i==3 && j==3)
                    continue;

                else
                {
                    int k=0;
                    temp[k++] = grid_val1[i][j+1];
                    temp[k++] = grid_val1[i][j-1];
                    temp[k++] = grid_val1[i-1][j];
                    temp[k] = grid_val1[i+1][j];
                    find_policy(temp, i,j);
                }
            }
        }


    }

    private static void find_policy(double[] temp, int i, int j) {

        double max = temp[0];
        for(int k=0;k<4;k++)
        {
            if(temp[k]>max)
                max = temp[k];
        }

        for(int k=0;k<4;k++)
        {
            if(temp[k] < max)
                grid_policy[grid[i][j]-1][k] = 0;
        }
    }

    private static boolean check_converge() {

        int i, j;
        boolean temp = false;
        for(i=0;i<4;i++)
        {
            for(j=0;j<4;j++)
            {
                if(grid_val1[i][j] != grid_val2[i][j])
                {
                    return true;
                }
            }
        }
        return false;


    }
}

