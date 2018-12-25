package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Day8
{
    public static void main(String[] args) throws IOException
    {
        String[] input = Files.readAllLines(Paths.get("resources//day8.txt")).get( 0 ).split(" ");

        AtomicInteger totalMetadataSum = new AtomicInteger();
        part1( input, 0, totalMetadataSum );
        System.out.println( totalMetadataSum.get() );
    }

    private static int part1( String[] input, int pointer, AtomicInteger totalMetadataSum )
    {
        int sum = 0;
        int childNodeCount = Integer.parseInt( input[pointer] );
        int metadataCount = Integer.parseInt( input[++pointer] );

        for( int i=0; i<childNodeCount; i++ )
            pointer = part1( input, ++pointer,  totalMetadataSum );

        for( int i=0; i<metadataCount; i++ )
            sum += Integer.parseInt( input[++pointer]);

        totalMetadataSum.set( totalMetadataSum.get() + sum );

        return pointer;
    }

}
