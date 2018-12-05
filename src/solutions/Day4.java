package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Day4
{
    public static void main(String[] args) throws ParseException, IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day4.txt"));

        solve( input );
    }

    private static void solve( List<String> input ) throws ParseException
    {
        List<Record> records = input.stream()
                .map(Record::new)
                .sorted(Comparator.comparing(o -> o.date))
                .collect(Collectors.toList());

        Map<Integer, List<int[]>> guardTimes = new HashMap<>();
        Map<Integer, Integer> guardTotalSleep = new HashMap<>();

        int currentGuard = 0;
        int sleepStart = 0;
        int sleepEnd;
        int[] times = null;
        Calendar calendar = Calendar.getInstance();

        for( Record record : records )
        {
            if( record.state == STATE.BEGIN )
            {
                currentGuard = record.guard;
                times = new int[60];
                guardTimes.computeIfAbsent( currentGuard, val -> new ArrayList<>() ).add( times );
                guardTotalSleep.putIfAbsent( currentGuard, 0);
            }
            if( record.state == STATE.SLEEP )
            {
                calendar.setTime( record.date );
                sleepStart = calendar.get( Calendar.MINUTE );
            }
            if( record.state == STATE.WAKE )
            {
                 calendar.setTime( record.date );
                 sleepEnd = calendar.get( Calendar.MINUTE );

                 for( int i=sleepStart; i<sleepEnd; i++ )
                     times[i] = 1;

                 guardTotalSleep.put( currentGuard, guardTotalSleep.get( currentGuard ) + (sleepEnd - sleepStart));
            }
        }

        int mostSleepyGuard = guardTotalSleep.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue)).get().getKey();

        List<int[]> timesOfMostSleepyGuard = guardTimes.get( mostSleepyGuard );

        int[] freq = getFrequency( timesOfMostSleepyGuard );

        int maxIndex = getMaxAndItsIndex( freq ).getValue();

        System.out.println( mostSleepyGuard * maxIndex );

        int maxAmongAll = Integer.MIN_VALUE;
        int mostProbableGuard = 0;
        int indexOfMax = 0;

        for(Map.Entry<Integer, List<int[]>> entry : guardTimes.entrySet() )
        {
            int[] freqOfGuard = getFrequency( entry.getValue() );

            AbstractMap.SimpleEntry<Integer, Integer> maxAndIndex = getMaxAndItsIndex( freqOfGuard );

            if( maxAndIndex.getKey() > maxAmongAll )
            {
                maxAmongAll = maxAndIndex.getKey();
                mostProbableGuard = entry.getKey();
                indexOfMax = maxAndIndex.getValue();
            }
        }
        System.out.println( indexOfMax * mostProbableGuard );

    }

    private static int[] getFrequency( List<int[]> times )
    {
        int[] freq = new int[60];
        for(int[] arr : times )
        {
            for( int i=0; i<arr.length; i++ )
            {
                if( arr[i] == 1)
                    freq[i]++;
            }
        }
        return freq;
    }

    private static AbstractMap.SimpleEntry<Integer, Integer> getMaxAndItsIndex(int[] arr )
    {
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;

        for( int i=0; i<60; i++ )
        {
            if( arr[i] > max )
            {
                max = arr[i];
                maxIndex = i;
            }
        }
        return new AbstractMap.SimpleEntry<>(max, maxIndex);
    }

    enum STATE
    {
        BEGIN, SLEEP, WAKE
    }

    static class Record
    {
        Date date;
        int guard;
        STATE state;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        Record( String input )
        {
            ParsePosition pos = new ParsePosition( 1 );
            this.date = sdf.parse( input, pos );
            String data = input.substring( pos.getIndex() + 2 );
            switch(data)
            {
                case "falls asleep": { this.state = STATE.SLEEP; break; }
                case "wakes up": { this.state = STATE.WAKE; break; }
                default:
                {
                    this.state = STATE.BEGIN;
                    this.guard = Integer.parseInt(data.split(" ")[1].substring( 1 ));
                    break;
                }
            }
        }
    }
}
