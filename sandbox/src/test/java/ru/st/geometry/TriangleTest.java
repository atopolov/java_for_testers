package ru.st.geometry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса Triangle")
public class TriangleTest {

    @Test
    @DisplayName("Корректный треугольник (3,4,5)")
    void testValidTriangle() throws NegativeSideException, InvalidTriangleException {
        Triangle t = new Triangle(3, 4, 5);
        assertEquals(12.0, t.getPerimeter());
        assertEquals(6.0, t.getArea());
    }

    @Test
    @DisplayName("Отрицательная длина стороны должна вызывать NegativeSideException")
    void testNegativeSide() {
        NegativeSideException exception = assertThrows(
                NegativeSideException.class, () -> new Triangle(-3, 4, 5));
        assertEquals("Стороны треугольника должны быть положительными", exception.getMessage());
    }

    @Test
    @DisplayName("Нарушение неравенства треугольника должно вызывать InvalidTriangleException")
    void testTriangleInequalityViolation() {
        InvalidTriangleException exception = assertThrows(
                InvalidTriangleException.class, () -> new Triangle(1, 2, 10)
        );
        assertEquals("Нарушено неравенство треугольника", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка периметра треугольника (3,4,5)")
    void testPerimeter() {
        Triangle t1 = new Triangle(3, 4, 5);
        assertEquals(12.0, t1.getPerimeter(), "Периметр должен быть равен 12.0");
    }

    @Test
    @DisplayName("Проверка площади треугольника (3,4,5) по формуле Герона")
    void testArea() {
        Triangle t1 = new Triangle(3, 4, 5);
        assertEquals(6.0, t1.getArea(), "Площадь должна быть равна 6.0");
    }

    @Test
    @DisplayName("Проверка треугольника (6,8,10)")
    void testAnotherTriangle() {
        Triangle t3 = new Triangle(6, 8, 10);
        assertEquals(24.0, t3.getPerimeter(), "Периметр должен быть равен 24.0");
        assertEquals(24.0, t3.getArea(),"Площадь должна быть равна 24.0");
    }
}