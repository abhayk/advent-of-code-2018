package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;

public class Day16
{
    public static void main(String[] args) throws IOException
    {
        List<String> input1 = Files.readAllLines( Paths.get("resources//day16_1.txt") );
        List<String> input2 = Files.readAllLines( Paths.get("resources//day16_2.txt") );

        ListIterator<String> iterator = input1.listIterator();
        List<BiConsumer<int[], int[]>> functions = getFunctionList();
        int matchMoreThanThree = 0;
        Map<Integer, Integer> cmdFunctionIndex = new HashMap<>();

        while( iterator.hasNext() )
        {
            int[] register = toArray( iterator.next().substring(9, 19), ",");
            int[] cmd = toArray( iterator.next(), " ");
            int[] output = toArray( iterator.next().substring(9, 19), ",");

            Set<Integer> matched = new HashSet<>();
            for( int i=0; i<functions.size(); i++ )
            {
                int[] registerCopy = Arrays.copyOf( register, register.length );
                functions.get( i ).accept( registerCopy, cmd );
                if( Arrays.equals( registerCopy, output ))
                    matched.add(i);
            }
            matchMoreThanThree += matched.size() >=3 ? 1 : 0;

            matched.removeAll( cmdFunctionIndex.values() );

            if( matched.size() == 1 )
                cmdFunctionIndex.put(cmd[0], matched.iterator().next());

            iterator.next();
        }
        System.out.println(matchMoreThanThree);

        int[] register = new int[4];
        for( String line : input2 )
        {
            int[] cmd = toArray( line, " ");
            functions.get( cmdFunctionIndex.get( cmd[0])).accept( register, cmd);
        }
        System.out.println(Arrays.toString( register));
    }

    private static int[] toArray(String input, String separator )
    {
        return Arrays.stream( input.split( separator ) )
                .mapToInt( item -> Integer.parseInt( item.trim()))
                .toArray();
    }

    private static List<BiConsumer<int[], int[]>> getFunctionList()
    {
        return new ArrayList<>( getFunctionMap().values() );
    }

    public static Map<String, BiConsumer<int[], int[]>> getFunctionMap()
    {
        Map<String, BiConsumer<int[], int[]>> functions = new HashMap<>();

        functions.put("addr", (register, cmd) -> register[cmd[3]] = register[cmd[1]] + register[cmd[2]]);
        functions.put("addi", (register, cmd) -> register[cmd[3]] = register[cmd[1]] + cmd[2]);
        functions.put("mulr", (register, cmd) -> register[cmd[3]] = register[cmd[1]] * register[cmd[2]]);
        functions.put("muli", (register, cmd) -> register[cmd[3]] = register[cmd[1]] * cmd[2]);
        functions.put("banr", (register, cmd) -> register[cmd[3]] = register[cmd[1]] & register[cmd[2]]);
        functions.put("bani", (register, cmd) -> register[cmd[3]] = register[cmd[1]] & cmd[2]);
        functions.put("borr", (register, cmd) -> register[cmd[3]] = register[cmd[1]] | register[cmd[2]]);
        functions.put("bori", (register, cmd) -> register[cmd[3]] = register[cmd[1]] | cmd[2]);
        functions.put("setr", (register, cmd) -> register[cmd[3]] = register[cmd[1]]);
        functions.put("seti", (register, cmd) -> register[cmd[3]] = cmd[1]);
        functions.put("gtir", (register, cmd) -> register[cmd[3]] = cmd[1] > register[cmd[2]] ? 1 : 0);
        functions.put("gtri", (register, cmd) -> register[cmd[3]] = register[cmd[1]] > cmd[2] ? 1 : 0);
        functions.put("gtrr", (register, cmd) -> register[cmd[3]] = register[cmd[1]] > register[cmd[2]] ? 1 : 0);
        functions.put("eqir", (register, cmd) -> register[cmd[3]] = cmd[1] == register[cmd[2]] ? 1 : 0);
        functions.put("eqri", (register, cmd) -> register[cmd[3]] = register[cmd[1]] == cmd[2] ? 1 : 0);
        functions.put("eqrr", (register, cmd) -> register[cmd[3]] = register[cmd[1]] == register[cmd[2]] ? 1 : 0);

        return functions;
    }
}
