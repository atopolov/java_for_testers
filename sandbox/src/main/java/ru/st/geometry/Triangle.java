package ru.st.geometry;

import java.util.Arrays;
import java.util.Objects;

public record Triangle(double a, double b, double c) {

    public Triangle {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new NegativeSideException("Стороны треугольника должны быть положительными");
        }

        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new InvalidTriangleException("Нарушено неравенство треугольника");
        }
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
                "Треугольник со сторонами: %.2f см, %.2f см, %.2f см (Периметр: %.2f см, Площадь: %.2f см²)",
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
