package Model.Test;

import Model.Polynomial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void divide() {
        assertEquals("-3-4x+x^2", Polynomial.getInstance("x^3-6x^2+5x-3").divide(
                Polynomial.getInstance("x-2")
        )[0].toString());
        assertEquals("-9", Polynomial.getInstance("x^3-6x^2+5x-3").divide(
                Polynomial.getInstance("x-2")
        )[1].toString());
    }

    @Test
    void add() {
        assertEquals("-5+6x-6x^2+x^3", Polynomial.getInstance("x^3-6x^2+5x-3").add(
                Polynomial.getInstance("x-2")
        ).toString());
        assertEquals("-4+6x-6x^2+x^3", Polynomial.getInstance("x^3-6x^2+5x-3").add(
                Polynomial.getInstance("x-2+1")
        ).toString());
    }

    @Test
    void subtract() {
        assertEquals("-1+4x-6x^2+x^3", Polynomial.getInstance("x^3-6x^2+5x-3").subtract(
                Polynomial.getInstance("x-2")
        ).toString());
    }

    @Test
    void multiply() {
        assertEquals(Polynomial.getInstance("6x^2 + 7x + 8".replaceAll("\\s", "")).multiply(
                Polynomial.getInstance("2x + 1".replaceAll("\\s", ""))
        ).toString(), Polynomial.getInstance("12 x^3 + 20 x^2 + 23 x + 8".replaceAll("\\s", "")).toString());
        assertEquals(Polynomial.getInstance("6x^2 + 7x + 8".replaceAll("\\s", "")).multiply(
                Polynomial.getInstance("0".replaceAll("\\s", ""))
        ).toString(), Polynomial.getInstance("0".replaceAll("\\s", "")).toString());

    }

    @Test
    void differentiate() {
        assertEquals(Polynomial.getInstance("12 x^3 + 20 x^2 + 23 x + 8".replaceAll("\\s", "")).differentiate().toString(),
        Polynomial.getInstance("23 + 40 x + 36 x^2".replaceAll("\\s", "")).toString());
    }

    @Test
    void integrate() {
        assertEquals(Polynomial.getInstance("23 + 40 x + 36 x^2".replaceAll("\\s", "")).integrate().toString(),
        Polynomial.getInstance("12 x^3 + 20 x^2 + 23 x".replaceAll("\\s", "")).toString());

    }
}