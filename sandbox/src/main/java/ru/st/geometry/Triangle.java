package ru.st.geometry;

import java.util.Arrays;

public record Triangle(double a, double b, double c) {

    public Triangle {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new NegativeSideException("Triangle sides must be positive");
        }

        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new InvalidTriangleException("Triangle sides must satisfy triangle inequality");
        }
    }

    public static void print(Triangle triangle) {
        System.out.println(triangle.toString());
    }



    public double getPerimeter() {
        return a + b + c;
    }

    public double getArea() {
        double p = getPerimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public String toString() {
        return String.format(
                "Triangle with sides: %.2f sm, %.2f sm, %.2f sm (Perimeter: %.2f sm, Area: %.2f sq. sm)",
                a, b, c, getPerimeter(), getArea()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Triangle other = (Triangle) obj;

        double[] sidesThis = {a, b, c};
        double[] sidesOther = {other.a, other.b, other.c};
        Arrays.sort(sidesThis);
        Arrays.sort(sidesOther);

        return Arrays.equals(sidesThis, sidesOther);
    }

    @Override
    public int hashCode() {
        double[] sides = {a, b, c};
        Arrays.sort(sides);
        return Arrays.hashCode(sides);
    }
}
