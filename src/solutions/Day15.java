package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day15
{
    public static void main(String[] args) throws IOException
    {
        List<String> input = Files.readAllLines(Paths.get("resources//day15.txt"));

        part1( input );
    }

    private static void part1( List<String> input )
    {
        int gridX = input.size();
        int gridY = input.get( 0 ).length();

        char[][] grid = new char[gridX][gridY];

        int m=0;
        for( String line : input )
            grid[m++] = line.toCharArray();

        System.out.println( shortestPath( grid, new Point(1,1), new Point(5, 2), gridX, gridY ));

//        Map<Character, List<Player>> players = new HashMap<>();
//
//        for( int i=0; i<gridX; i++ )
//        {
//            for( int j=0; j<gridY; j++ )
//            {
//                if( grid[i][j] == 'E' || grid[i][j] == 'G' )
//                    players.computeIfAbsent( grid[i][j], val -> new ArrayList<>()).add( new Player( grid[i][j], i, j));
//            }
//        }
//
//        System.out.println( players.values().stream().flatMap( Collection::stream ).sorted().collect( Collectors.toList() ));
//
//        Comparator<Player> positionComparator = Comparator.comparing( p -> p.position );
//        Comparator<Player> hpComparator = Comparator.comparing( (Player p) -> p.hp )
//                .thenComparing( positionComparator );
//
//        while( true )
//        {
//            List<Player> playersInOrder = players.values().stream()
//                    .flatMap( Collection::stream )
//                    .sorted( positionComparator )
//                    .collect( Collectors.toList() );
//
//            for( Player player : playersInOrder )
//            {
//                char opposingTeam = player.team == 'E' ? 'G' : 'E';
//                List<Player> opposingPlayers = players.get( opposingTeam );
//
//                List<Player> playersInAttackRange = getPlayersInAttackRange( grid, gridX, gridY,
//                        player, opposingTeam, opposingPlayers );
//
//                if( !playersInAttackRange.isEmpty() )
//                {
//                    attack( grid, opposingPlayers, playersInAttackRange, hpComparator );
//                    continue;
//                }
//            }
//        }
    }

    private static int shortestPath(char[][] grid, Point source, Point dest, int gridX, int gridY )
    {
        boolean[][] visited = new boolean[gridX][gridY];
        int[][] distances = new int[gridX][gridY];
        for( int[] arr : distances )
            Arrays.fill( arr, -1);

        LinkedList<Point> queue = new LinkedList<>();
        queue.add( source );
        distances[source.x][source.y] = 0;
        visited[source.x][source.y] = true;

        int[][] offsets = new int[][]{ {-1,0}, {1,0}, {0,-1}, {0,1} };

        while( !queue.isEmpty() )
        {
            Point p = queue.removeLast();

            for( int[] offset : offsets )
            {
                int curX = p.x + offset[0];
                int curY = p.y + offset[1];

                if( curX >=0 && curX < gridX && curY >=0 && curY < gridY )
                {
                    if( !visited[curX][curY] )
                    {
                        visited[curX][curY] = true;
                        distances[curX][curY] = distances[p.x][p.y] + 1;
                        if( grid[curX][curY] == '.')
                        {
                            if( curX == dest.x && curY == dest.y )
                                return distances[curX][curY];
                            queue.add( new Point( curX, curY ));
                        }
                    }
                }
            }
        }
        return -1;
    }

    private static void move( char[][] grid, Player me, List<Player> opposingPlayers )
    {

    }

    private static void attack( char[][] grid, List<Player> opposingPlayers, List<Player> playersInAttackRange,
                                Comparator<Player> hpComparator )
    {
        playersInAttackRange.sort( hpComparator );
        Player target = playersInAttackRange.get( 0 );
        target.hp -= 3;

        if( target.hp <= 0 )
        {
            opposingPlayers.remove(target);
            grid[target.position.x][target.position.y] = '.';
        }
    }

    private static List<Player> getPlayersInAttackRange( char[][] grid, int gridX, int gridY,
                                                         Player me, char opposingTeam, List<Player> opposingPLayers )
    {
        List<Player> playersInAttackRange = new ArrayList<>();

        int[][] offsets = new int[][]{ {-1,0}, {1,0}, {0,-1}, {0,1} };

        for( int[] offset : offsets )
        {
            int curX = me.position.x + offset[0];
            int curY = me.position.y + offset[1];

            if( curX >=0 && curX < gridX && curY >=0 && curY < gridY )
            {
                if( grid[curX][curY] == opposingTeam )
                {
                    playersInAttackRange.add( opposingPLayers.stream()
                            .filter( player -> player.position.x == curX && player.position.y == curY )
                            .findFirst().get() );
                }
            }
        }
        return playersInAttackRange;
    }


    private static class Player
    {
        char team;
        Point position;
        int hp = 200;
        int attack = 3;

        Player( char team, int x, int y)
        {
            this.team = team;
            this.position = new Point( x, y );
        }

        @Override
        public String toString()
        {
            return "Player{position=" + position + ", hp=" + hp + ", attack=" + attack + '}';
        }
    }

    private static class Point implements Comparable<Point>
    {
        int x, y;

        Point( int x, int y )
        {
            this.x = x;
            this.y = y;
        }

        public int distance( Point p )
        {
            return Math.abs( p.x - x ) + Math.abs( p.y - y );
        }

        @Override
        public int compareTo(Point point)
        {
            return Comparator.comparing( (Point p) -> p.y ).thenComparing( p -> p.x ).compare( this, point );
        }

        @Override
        public String toString()
        {
            return "Point{x=" + x + ", y=" + y + '}';
        }
    }
}
