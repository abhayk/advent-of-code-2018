package solutions;

import java.util.ArrayList;
import java.util.List;

public class Day14
{
    public static void main(String[] args)
    {
        int input = 846601;
        int recipeCount = 10;

        List<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);

        int[] elfPositions = new int[2];
        elfPositions[1] = 1;
        int a;

        StringBuilder sb = new StringBuilder();
        sb.append(3);
        sb.append(7);

        for( int i=0; i<100000000; i++ )
        {
            int newRecipe = recipes.get( elfPositions[0] ) + recipes.get( elfPositions[1] );

            if( newRecipe >= 10 )
            {
                a = newRecipe / 10;
                recipes.add(a);
                sb.append(a);
            }

            a = newRecipe % 10;
            recipes.add( a );
            sb.append( a );

            elfPositions[0] = getNewPosition( recipes, elfPositions[0]);
            elfPositions[1] = getNewPosition( recipes, elfPositions[1]);
        }

        String s = sb.toString();
        System.out.println(s.substring( input, input + recipeCount));
        System.out.println(s.indexOf(String.valueOf(input)));
    }

    private static int getNewPosition( List<Integer> recipes, int currentPosition )
    {
        int newPosition = currentPosition + recipes.get( currentPosition ) + 1;
        while( newPosition >= recipes.size() )
            newPosition -= recipes.size();
        return newPosition;
    }
}
