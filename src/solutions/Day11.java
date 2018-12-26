package solutions;

import java.util.stream.IntStream;

public class Day11
{
    public static void main(String[] args)
    {
        int[][] grid = new int[300][300];
        int serial = 7165;

        calculatePowerLevels( grid, serial );

        solve( grid, new int[]{3} );
        solve( grid, IntStream.range(3, 20).toArray());
    }

    private static void solve(int[][] grid, int[] offsets )
    {
        int x = 0, y = 0, o = 0;

        int maxEffectivePowerLevel = Integer.MIN_VALUE;

        for( int i=0; i<300; i++ )
        {
            for( int j=0; j<300; j++ )
            {
                for( int offset : offsets )
                {
                    int effectivePowerLevel = getEffectivePowerLevel( grid, i, j, offset );
                    if( effectivePowerLevel > maxEffectivePowerLevel )
                    {
                        maxEffectivePowerLevel = effectivePowerLevel;
                        x = i;
                        y = j;
                        o = offset;
                    }
                }
            }
        }
        //System.out.println(maxEffectivePowerLevel);
        System.out.println(x+1);
        System.out.println(y+1);
        System.out.println(o);
    }

    private static int getEffectivePowerLevel(int[][] grid, int x, int y, int offset )
    {
        int sum = 0;
        for( int i=0; i<offset; i++ )
        {
            for( int j=0; j<offset; j++ )
            {
                int curX = x+i;
                int curY = y+j;
                if( curX >=0 && curX < 300 && curY >=0 && curY < 300 )
                    sum += grid[curX][curY];
            }
        }
        return sum;
    }

    private static void calculatePowerLevels( int[][] grid, int serial )
    {
        for( int i=0; i<300; i++ )
        {
            for(int j=0; j<300; j++ )
            {
                grid[i][j] = powerLevel(i+1, j+1, serial);
            }
        }
    }

    private static int powerLevel( int x, int y, int serial )
    {
        int rackId = x + 10;
        int tmp = (rackId * y + serial) * rackId;
        return tmp / 100 > 0 ? ((tmp /100) % 10 ) - 5 : -5;
    }
}
