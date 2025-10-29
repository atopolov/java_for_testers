package ru.st.geometry;

public class Main {
    public static void main(String[] args) {
        Triangle t1 = new Triangle(3, 4, 5);
        Triangle t2 = new Triangle(6, 8, 10);

        System.out.println(t1);
        System.out.println("Периметр: " + t1.getPerimeter());
        System.out.println("Площадь: " + t1.getArea());
        System.out.println();

        System.out.println(t2);
        System.out.println("Периметр: " + t2.getPerimeter());
        System.out.println("Площадь: " + t2.getArea());
    }
}
