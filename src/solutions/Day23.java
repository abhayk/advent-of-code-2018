package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day23
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day23.txt"));

        List<Point> points = new ArrayList<>();
        long largestRadius = Long.MIN_VALUE;
        Point pointWithLargestRadius = null;
        for( String line : input )
        {
            String[] split = line.substring(5, line.indexOf(">")).split(",");
            Point point = new Point();
            point.x = Long.parseLong( split[0] );
            point.y = Long.parseLong( split[1] );
            point.z = Long.parseLong( split[2] );
            point.radius = Long.parseLong( line.substring( line.indexOf("r=") + 2));
            points.add( point );

            if( point.radius > largestRadius )
            {
                largestRadius = point.radius;
                pointWithLargestRadius = point;
            }
        }

        int count = 0;
        for( Point point : points )
            count += pointWithLargestRadius.distance( point ) <= pointWithLargestRadius.radius ? 1 : 0;

        System.out.println(count);
    }

    private static class Point
    {
        long x, y, z, radius;

        public long distance( Point p )
        {
            return Math.abs( p.x - x ) + Math.abs( p.y - y ) + Math.abs( p.z - z );
        }

        @Override
        public String toString()
        {
            return "Point{" + "x=" + x + ", y=" + y + ", z=" + z + ", radius=" + radius + '}';
        }
    }
}
