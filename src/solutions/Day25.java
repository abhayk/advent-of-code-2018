package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day25
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day25.txt"));

        List<Point> points = new ArrayList<>();

        for( String line : input )
        {
            String[] split = line.split(",");
            Point point = new Point();
            point.a = Integer.parseInt( split[0].trim() );
            point.b = Integer.parseInt( split[1].trim() );
            point.c = Integer.parseInt( split[2].trim() );
            point.d = Integer.parseInt( split[3].trim() );
            points.add( point );
        }

        UnionFind uf = new UnionFind( points.size() );

        for( int i=0; i<points.size(); i++ )
        {
            for( int j=i+1; j<points.size(); j++ )
            {
                if( points.get(i).distance( points.get( j )) <= 3 )
                    uf.union( i, j);
            }
        }

        System.out.println(uf.count);
    }

    private static class UnionFind
    {
        int[] parents;
        int count;

        public UnionFind( int size )
        {
            this.parents = new int[ size ];
            for( int i=0; i<size; i++ )
                parents[i] = i;
            this.count = size;
        }

        public int find( int p )
        {
            while( p!= parents[p] )
            {
                parents[p] = parents[ parents[p] ];
                p = parents[p];
            }
            return p;
        }

        public void union( int p, int q )
        {
            int rootP = find( p );
            int rootQ = find( q );
            if( rootP == rootQ )
                return;
            parents[rootP] = rootQ;
            count--;
        }
    }

    private static class Point
    {
        int a, b, c, d;

        public int distance( Point p )
        {
            return Math.abs( p.a - a ) + Math.abs( p.b - b ) + Math.abs( p.c - c ) + Math.abs( p.d - d );
        }
    }
}
