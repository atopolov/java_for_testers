package ru.st.geometry;


import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Random random = new Random();

        Supplier<Triangle> randomTriangle = () -> {
            while (true) {
                int a = random.nextInt(99) + 1;
                int b = random.nextInt(99) + 1;
                int c = random.nextInt(99) + 1;

                try {
                    return new Triangle(a, b, c);
                } catch (NegativeSideException | InvalidTriangleException e) {
                }
            }
        };

        var triangles = Stream.generate(randomTriangle)
                .limit(5)
                .toList();

        triangles.forEach(Triangle::print);
    }
}


