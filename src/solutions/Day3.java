package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day3
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day3.txt"));
        solve(input);
    }

    public static void solve(List<String> input )
    {
        long tx = 1000;
        long ty = 1000;

        Map<Long, Integer> plotClaimMap = new HashMap<>();
        Set<Long> disputed = new HashSet<>();
        HashSet<Integer> validClaims = new HashSet<>();

        long claimed = 0;

        for( String line : input )
        {
            String[] split = line.split(" ");

            int claim = Integer.parseInt(split[0].substring(1));

            String[] secondSplit = split[2].split(",");

            int x = Integer.parseInt(secondSplit[0]);
            int y = Integer.parseInt(secondSplit[1].substring(0, secondSplit[1].lastIndexOf(":")));

            String[] thirdSplit = split[3].split("x");

            int l = Integer.parseInt(thirdSplit[0]);
            int b = Integer.parseInt(thirdSplit[1]);

            if( x + l >= tx || y + b > ty )
                throw new IllegalStateException(" x - " + x + " y - " + y + " l - " + l + " b + " + b);

            List<Integer> disputedClaims = new ArrayList<>();

            for( int i=x; i<x+l; i++ )
            {
                for( int j=y; j<y+b; j++ )
                {
                    long loc = tx * j + i;

                    if( plotClaimMap.containsKey( loc ) )
                    {
                        disputedClaims.add( plotClaimMap.get( loc ) );
                        if( disputed.add( loc ) )
                        {
                            claimed++;
                            continue;
                        }
                    }
                    plotClaimMap.put( loc, claim );
                }
            }
            if( disputedClaims.isEmpty() )
                validClaims.add( claim );

            validClaims.removeAll( disputedClaims );
        }
        System.out.println( claimed );
        System.out.println( validClaims.toString() );
    }
}
