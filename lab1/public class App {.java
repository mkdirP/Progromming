public class App {
    public static void x(int a, int[] b, int[] c) {
        a = 10;
        b[0] = 20;
        c = new int[] { 30 } ;
    }

    public static void main(String[]args) {
        int a = 1;
        int[] b = new int[] { 2 };
        int[] c = new int[] { 3 };
        System.out.println(a);
        System.out.println(b[0]);
        System.out.println(c[0]);

        x(a, b, c);

        System.out.println(a);
        System.out.println(b[0]);
        System.out.println(c[0]);
    }
}

Java Language arguements all pass by value, for primitive type pass the value itself, for object like arrays pass the value of reference.

1
2 
3 
1 
20
3