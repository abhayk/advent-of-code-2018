package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day17
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day17.txt"));

        int[][] grid = new int[2000][2000];

        for( String line : input )
        {
            int first = getSingleValue( line );
            int[] secondRange = getRangeValue( line );
            int x, y;
            for( int i=secondRange[0]; i<secondRange[1]; i++ )
            {
                x = line.startsWith("x=") ? first : i;
                y = line.startsWith("y=") ? first : i;
                grid[x][y] = 1;
            }
        }
        int waterX = 500, waterY = 0;

        while( true )
        {

        }
    }

    private static int getSingleValue(String line)
    {
        return Integer.parseInt( line.substring( line.indexOf("=") + 1, line.indexOf(",")));
    }

    private static int[] getRangeValue(String line)
    {
        return Arrays.stream( line.split(",")[1].trim().substring( line.indexOf("=") + 1).split("\\.\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

}
