package ru.st.geometry;

public record Triangle(double a, double b, double c) {

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

}
