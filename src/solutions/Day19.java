package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;

public class Day19
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day19.txt"));

        long start = System.currentTimeMillis();
        solve( input, new int[6] );
        System.out.println( System.currentTimeMillis() - start);

        int[] registers = new int[6];
        registers[0] = 1;
        solve( input, registers);
    }

    private static void solve( List<String> input, int[] registers )
    {
        int insRegister = Integer.parseInt( input.get(0).split(" ")[1] );
        input.remove(0);
        int insPointer = 0;

        Map<String, BiConsumer<int[], int[]>> functionMap = Day16.getFunctionMap();
        Map<Integer, String[]> splitIns = new HashMap<>();
        Map<Integer, int[]> cmds = new HashMap<>();
        int m = 0;
        for( String line : input )
        {
            String[] split = line.split(" ");
            splitIns.put(m, split);
            int[] cmd = new int[4];
            for( int i=1; i<4; i++)
                cmd[i] = Integer.parseInt( split[i] );
            cmds.put(m, cmd);
            m++;
        }
        while( true )
        {
            if( insPointer < 0 || insPointer >= input.size() )
                break;
            System.out.println(insPointer);
            String[] split = splitIns.get( insPointer );
            String op = split[0];
            int[] cmd = cmds.get( insPointer );
            registers[ insRegister ] = insPointer;
            functionMap.get( op ).accept( registers, cmd );
            insPointer = registers[ insRegister ] + 1;
            System.out.println( Arrays.toString( registers));
        }
        System.out.println(Arrays.toString( registers ));
    }
}
