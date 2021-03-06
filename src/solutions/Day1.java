package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class Day1
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources\\day1.txt"));
        System.out.println( getEffectiveSum( input ) );
        System.out.println( getFirstRepeatedFrequency( input ) );
    }

    public static long getFirstRepeatedFrequency( List<String> input )
    {
        HashSet<Long> freqs = new HashSet<>();
        freqs.add( 0L );
        long sum = 0;

        while( true )
        {
            for( String line : input )
            {
                sum += Long.parseLong( line );
                if( !freqs.add( sum ) )
                    return sum;
            }
        }
    }

    public static long getEffectiveSum( List<String> input )
    {
        return input.stream().mapToLong( Long::parseLong ).sum();
    }
}
