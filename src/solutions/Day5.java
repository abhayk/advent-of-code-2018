package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day5.txt"));

        System.out.println( react( input.get( 0 )));
        System.out.println( part2( input.get( 0 )));
    }

    private static int part2( String input )
    {
        int least = Integer.MAX_VALUE;
        for( char i='a'; i<='z'; i++ )
        {
            String candidate = input.replaceAll( String.valueOf(i), "")
                    .replaceAll( String.valueOf(i).toUpperCase(), "");

            least = Math.min( least, react( candidate ));
        }
        return least;
    }

    private static int react( String input )
    {
        StringBuilder sb = new StringBuilder( input );
        boolean changed = true;
        int start = 0;

        while( changed )
        {
            changed = false;
            for( int i=start; i<sb.length() - 1; i++ )
            {
                if( Math.abs( sb.charAt(i) - sb.charAt(i+1)) == 32 )
                {
                    sb.delete(i, i+2);
                    changed = true;
                    start = i==0 ? 0 : i-1;
                    break;
                }
            }
        }
        return sb.length();
    }
}
