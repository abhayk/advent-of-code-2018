package solutions;

import java.util.Arrays;

public class Day9
{
    public static void main(String[] args)
    {
        solve("462 players; last marble is worth 71938 points");
        solve("462 players; last marble is worth 7193800 points");
    }

    private static void solve( String input )
    {
        String[] split = input.split(";");

        int playerCount = Integer.parseInt(split[0].split(" ")[0]);
        int lastMarble = Integer.parseInt(split[1].trim().split(" ")[4]);

        long[] players = new long[ playerCount ];

        Node current = new Node(0);
        current.next = current;
        current.prev = current;

        int currentMarble = 0, currentPlayer = 0;

        while( currentMarble != lastMarble )
        {
            currentMarble++;
            if( currentMarble % 23 == 0 )
            {
                players[ currentPlayer ] += currentMarble;
                Node toRemove = current;
                for( int i=0; i<7; i++ )
                    toRemove = toRemove.prev;
                players[ currentPlayer ] += toRemove.val;

                Node left = toRemove.prev;
                Node right = toRemove.next;

                left.next = right;
                right.prev = left;

                current = right;
            }
            else
            {
                Node left = current.next;
                Node right = current.next.next;

                Node newNode = new Node( currentMarble );

                left.next = newNode;
                newNode.prev = left;
                newNode.next = right;
                right.prev = newNode;

                current = newNode;
            }

            currentPlayer++;
            if( currentPlayer == playerCount )
                currentPlayer = 0;
        }

        System.out.println(Arrays.stream( players ).max().getAsLong() );
    }

    private static class Node
    {
        int val;
        Node next;
        Node prev;

        public Node(int val) { this.val = val; }
    }
}
