package ru.st.geometry;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Triangle class testing")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TriangleTest {

    @Test
    @Order(1)
    @DisplayName("Valid triangle (3,4,5)")
    void testValidTriangle() throws NegativeSideException, InvalidTriangleException {
        Triangle t = new Triangle(3, 4, 5);
        assertEquals(12.0, t.getPerimeter());
        assertEquals(6.0, t.getArea());
    }

    @Test
    @Order(5)
    @DisplayName("Negative side length should throw NegativeSideException")
    void testNegativeSide() {
        NegativeSideException exception = assertThrows(
                NegativeSideException.class, () -> new Triangle(-3, 4, 5));
        assertEquals("Triangle sides must be positive", exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Triangle inequality violation should throw InvalidTriangleException")
    void testTriangleInequalityViolation() {
        InvalidTriangleException exception = assertThrows(
                InvalidTriangleException.class, () -> new Triangle(1, 2, 10)
        );
        assertEquals("Triangle inequality violated", exception.getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("Check perimeter of triangle (3,4,5)")
    void testPerimeter() {
        Triangle t1 = new Triangle(3, 4, 5);
        assertEquals(12.0, t1.getPerimeter(), "Perimeter should be 12.0");
    }

    @Test
    @Order(3)
    @DisplayName("Check area of triangle (3,4,5) using Heron's formula")
    void testArea() {
        Triangle t1 = new Triangle(3, 4, 5);
        assertEquals(6.0, t1.getArea(), "Area should be 6.0");
    }

    @Test
    @Order(4)
    @DisplayName("Check another triangle (6,8,10)")
    void testAnotherTriangle() {
        Triangle t2 = new Triangle(6, 8, 10);
        assertEquals(24.0, t2.getPerimeter(), "Perimeter should be 24.0");
        assertEquals(24.0, t2.getArea(), "Area should be 24.0");
    }

    @Test
    @Order(7)
    @DisplayName("Check equality of triangles")
    void testEqualTriangles() {
        Triangle t1 = new Triangle(3, 4, 5);
        Triangle t2 = new Triangle(5, 4, 3);
        Triangle t3 = new Triangle(2, 4, 5);
        assertEquals(t1, t2, "Triangles should be equal");
        assertNotEquals(t1, t3, "Triangles should not be equal");
    }
}