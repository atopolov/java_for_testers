package ru.st.geometry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса Triangle")
public class TriangleTest {

        @Test
        @DisplayName("Проверка периметра треугольника (3,4,5)")
        void testPerimeter() {
            Triangle t1 = new Triangle(3, 4, 5);
            assertEquals(12.0, t1.getPerimeter(), 0.0001, "Периметр должен быть равен 12.0");
        }

        @Test
        @DisplayName("Проверка площади треугольника (3,4,5) по формуле Герона")
        void testArea() {
            Triangle t1 = new Triangle(3, 4, 5);
            assertEquals(6.0, t1.getArea(), 0.0001, "Площадь должна быть равна 6.0");
        }

        @Test
        @DisplayName("Проверка треугольника (6,8,10)")
        void testAnotherTriangle() {
            Triangle t3 = new Triangle(6, 8, 10);
            assertEquals(24.0, t3.getPerimeter(), 0.0001, "Периметр должен быть равен 24.0");
            assertEquals(24.0, t3.getArea(), 0.0001, "Площадь должна быть равна 24.0");
        }
    }