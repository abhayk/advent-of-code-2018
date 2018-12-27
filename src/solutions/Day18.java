package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day18
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day18.txt"));

        //Part 1
        solve( input, 10);

        //Part 2. After 513 iterations the values repeat every 28 times.
        solve( input, 513 + (1000000000-513) % 28 );
    }

    private static void solve( List<String> input, long minutes )
    {
        int maxX = input.size();
        int maxY = input.get(0).length();

        char[][] grid = new char[maxX][maxY];

        int m=0;
        for( String line : input )
            grid[m++] = line.toCharArray();

        char[][] tmp = new char[maxX][maxY];

        for( long k=0; k<minutes; k++ )
        {
            for( int i=0; i<maxX; i++ )
            {
                for(int j=0; j<maxY; j++ )
                    tmp[i][j] = getNextArea( grid, i, j, maxX, maxY );
            }
            for( int i=0; i<maxX; i++ )
            {
                for(int j=0; j<maxY; j++ )
                    grid[i][j] = tmp[i][j];
            }
        }
        System.out.println( getCount( grid, '|', maxX, maxY) * getCount( grid, '#', maxX, maxY));
    }

    private static int getCount( char[][] grid, char c, int maxX, int maxY )
    {
        int sum = 0;
        for( int i=0; i<maxX; i++ )
        {
            for( int j=0; j<maxY; j++ )
            {
                sum += grid[i][j] == c ? 1 : 0;
            }
        }
        return sum;
    }

    private static char getNextArea(char[][] grid, int x, int y, int maxX, int maxY )
    {
        int tree = 0, lumberyard = 0;
        int curX, curY;
        for( int i=-1; i<=1; i++ )
        {
            for( int j=-1; j<=1; j++ )
            {
                if( i == 0 && j == 0 )
                    continue;
                curX = x+i;
                curY = y+j;
                if( curX >=0 && curX < maxX && curY >=0 && curY < maxY )
                {
                    if( grid[curX][curY] == '|') tree++;
                    if( grid[curX][curY] == '#') lumberyard++;
                }
            }
        }
        char result = grid[x][y];
        if( result == '.' && tree >= 3) result = '|';
        else if( result == '|' && lumberyard >= 3) result = '#';
        else if( result == '#' && (tree == 0 || lumberyard == 0)) result = '.';

        return result;
    }
}
