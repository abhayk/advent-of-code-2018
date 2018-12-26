package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day10
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day10.txt"));
        int[][] positions = new int[ input.size() ][2];
        int[][] velocities = new int[ input.size() ][2];

        int m=0;
        for( String line : input )
        {
            String[] pos = line.substring( line.indexOf("<") + 1, line.indexOf(">")).split(",");
            positions[m][0] = Integer.parseInt(pos[0].trim());
            positions[m][1] = Integer.parseInt(pos[1].trim());

            String[] vel = line.substring( line.lastIndexOf("<") + 1, line.lastIndexOf(">")).split(",");
            velocities[m][0] = Integer.parseInt(vel[0].trim());
            velocities[m][1] = Integer.parseInt(vel[1].trim());
            m++;
        }

        int minX = 0, maxX = 0, minY = 0, maxY = 0;
        for( int i=0; i<10511; i++ )
        {
            minX = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            minY = Integer.MAX_VALUE;
            maxY = Integer.MIN_VALUE;
            for( int j=0; j<input.size(); j++ )
            {
                positions[j][0] += velocities[j][0];
                positions[j][1] += velocities[j][1];
                minX = Math.min(minX, positions[j][0]);
                maxX = Math.max(maxX, positions[j][0]);
                minY = Math.min(minY, positions[j][1]);
                maxY = Math.max(maxY, positions[j][1]);
            }
            //System.out.println("Area for " + i + " = " + ((maxX - minX) * (maxY - minY)));
        }

        char[][] box = new char[maxX - minX + 1][maxY - minY + 1];

        for(char[] arr : box)
            Arrays.fill( arr, '.');

        for( int i=0; i<input.size(); i++ )
            box[positions[i][0] - minX][positions[i][1] - minY] = '#';

        for( int i=0; i<maxY - minY + 1; i++ )
        {
            for( int j=0; j<maxX - minX + 1; j++ )
            {
                System.out.print( box[j][i]);
            }
            System.out.println();
        }
    }
}
