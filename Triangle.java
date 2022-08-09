public class Triangle {

    public static void main( String[] args )
    {
        double a;

        a = triangleArea(3, 3, 3);
        System.out.println("A triangle with sides 3,3,3 has an area of:" + a);

        a = triangleArea(3, 4, 5);
        System.out.println("A triangle with sides 3,4,5 has an area of:" + a);

        a = triangleArea(9, 9, 9);
        System.out.println("A triangle with sides 9,9,9 has an area of:" + a );

    }

    public static double triangleArea( int a, int b, int c )
    {
        double pp = (a + b + c) / 2.0;  // полупериметр
        return Math.sqrt(pp * (pp - a) * (pp - b) * (pp - c)); // прямо по формуле Герона из вики
    }

}
