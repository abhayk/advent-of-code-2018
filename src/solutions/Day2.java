package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day2
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources\\day2.txt"));

        System.out.println( part1( input ));

        System.out.println( part2( input ));
    }

    private static int part1( List<String> input )
    {
        int twice = 0;
        int thrice = 0;

        for( String line : input )
        {
            Map<Character, AtomicInteger> charCountMap = new HashMap<>();

            for( Character c : line.toCharArray() )
                charCountMap.computeIfAbsent(c, key -> new AtomicInteger()).incrementAndGet();

            boolean twiceDone = false;
            boolean thriceDone = false;

            for( Map.Entry<Character, AtomicInteger> entry : charCountMap.entrySet() )
            {
                if( entry.getValue().get() == 2 && !twiceDone )
                {
                    twice++;
                    twiceDone = true;
                }
                if( entry.getValue().get() == 3 && !thriceDone )
                {
                    thrice++;
                    thriceDone = true;
                }
            }
        }
        return twice * thrice;
    }

    private static String part2( List<String> input )
    {
        for( int i=0; i<input.size() - 1; i++ )
        {
            for(int j=i; j<input.size(); j++ )
            {
                if( getLetterDifferenceCount( input.get(i), input.get(j)) == 1 )
                {
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < input.get(i).length(); k++)
                    {
                        if( input.get( i ).charAt( k ) == input.get( j ).charAt( k ))
                            sb.append( input.get( i ).charAt(k));
                    }
                    return sb.toString();
                }
            }
        }
        return null;
    }

    private static int getLetterDifferenceCount( String word1, String word2 )
    {
        int count = 0;

        for( int i=0; i<word1.length(); i++ )
        {
            if( word1.charAt(i) != word2.charAt(i))
                count++;
        }
        return count;
    }
}
