package Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private static final Polynomial ZERO;
    private static final BigDecimal EPSILON = new BigDecimal("0.0001");

    static {
        ArrayList<Monomial> arr = new ArrayList<>(1);
        arr.add(Monomial.ZERO);
        ZERO = new Polynomial(arr);
    }

    private static final Pattern pattern = Pattern.compile(
            "[+-]((?<coef>[1-9][0-9]*(\\.[0-9]+)?|0\\.[0-9]+)\\*?)?(?<var>[a-zA-Z][0-9a-zA-Z]*)(\\^(?<exp>[1-9][0-9]*))?|[+-](?<number>[1-9][0-9]*(\\.[0-9]+)?|0\\.[0-9]+)");
    private final String string;
    private final String variable;
    private final ArrayList<Monomial> monomials;

    private Polynomial(ArrayList<Monomial> arr) {
        if (arr.size() == 0) throw new RuntimeException();
        StringBuilder str = new StringBuilder();
        monomials = arr;
        for (Monomial m : arr) {
            str.append(m.toString());
        }
        if (str.toString().startsWith("+")) {
            str = new StringBuilder(str.substring(1));
        }
        variable = arr.get(arr.size() - 1).getVariable();
        string = str.toString();
    }

    public static Polynomial getInstance(String input) {
        if(input.equals("0")) return Polynomial.ZERO;
        if (input.charAt(0) != '-' && input.charAt(0) != '+') {
            input = '+' + input;
        }
        if (input.matches(
                "([+-](([1-9][0-9]*(\\.[0-9]+)?|0\\.[0-9]+)\\*?)?([a-zA-Z][0-9a-zA-Z]*(\\^([1-9][0-9]*))?)|([+-][1-9][0-9]*(\\.[0-9]+)?|[+-]0\\.[0-9]+))+"
        )) {
            ArrayList<Monomial> monomials = new ArrayList<>();
            Matcher matcher = pattern.matcher(input);
            BigDecimal coefficient;
            String variable = null;
            int exponent;
            while (matcher.find()) {
                if (matcher.group("number") == null) {
                    if (variable != null && !matcher.group("var").equals(variable)) {
                        return null;
                    }
                    variable = matcher.group("var");
                    coefficient = ((matcher.group("coef") == null) ? BigDecimal.ONE : new BigDecimal(matcher.group("coef"))).multiply(
                            ((matcher.group(0).charAt(0) == '+') ? BigDecimal.ONE : Monomial.NONE));
                    exponent = ((matcher.group("exp") == null) ? 1 : Integer.parseInt(matcher.group("exp")));
                    monomials.add(new Monomial(coefficient, variable, exponent));
                } else {
                    coefficient = (new BigDecimal(matcher.group("number"))).multiply(((matcher.group(0).charAt(0) == '+') ? BigDecimal.ONE : Monomial.NONE));
                    monomials.add(new Monomial(coefficient, null, 0));
                }
            }
            return formatPolynomial(monomials);
        }
        return null;
    }

    private static Polynomial formatPolynomial(ArrayList<Monomial> monomials) {
        Collections.sort(monomials);
        int exp = monomials.get(0).getExponent();
        String variable = monomials.get(0).getVariable();
        BigDecimal coeff = monomials.get(0).getCoefficient();
        int j = 0;
        for (int i = 1; i < monomials.size(); i++) {
            if (monomials.get(i).getExponent() != exp) {
                if (coeff.abs().compareTo(EPSILON) >= 0) {
                    monomials.set(j, new Monomial(coeff, variable, exp));
                    j++;
                }
                exp = monomials.get(i).getExponent();
                variable = monomials.get(i).getVariable();
                coeff = monomials.get(i).getCoefficient();
            } else {
                coeff = coeff.add(monomials.get(i).getCoefficient());
                if (variable == null) {
                    variable = monomials.get(i).getVariable();
                }
            }
        }
        if (coeff.abs().compareTo(EPSILON) >= 0) {
            monomials.set(j, new Monomial(coeff, variable, exp));
            j++;
        }
        while (j < monomials.size()) {
            monomials.remove(monomials.size() - 1);
        }
        return ((monomials.size() > 0) ? new Polynomial(monomials) : Polynomial.ZERO);
    }

    public int getDegree() {
        return monomials.get(monomials.size() - 1).getExponent();
    }

    public Monomial getLead() {
        return monomials.get(monomials.size() - 1);
    }

    public Polynomial[] divide(Polynomial p) {
        if (p != ZERO && (variable == null || p.variable == null || variable.equals(p.variable))) {
            Polynomial q = ZERO;
            Polynomial r = this;
            String var = (variable == null) ? p.variable : variable;
            Monomial d = p.getLead();
            ArrayList<Monomial> arr = new ArrayList<>(1);
            arr.add(null);
            while (r != ZERO && r.getDegree() >= p.getDegree()) {
                Monomial t = new Monomial(r.getLead().getCoefficient().divide(d.getCoefficient(), 5, RoundingMode.HALF_UP).stripTrailingZeros(),
                        var, r.getLead().getExponent() - d.getExponent());
                arr.set(0, t);
                Polynomial tpolynomial = new Polynomial(arr);
                q = q.add(tpolynomial);
                r = r.subtract(tpolynomial.multiply(p));
            }
            return new Polynomial[]{q, r};
        }
        return null;
    }

    @Override
    public String toString() {
        return string;
    }

    public Polynomial add(Polynomial polynomial) {
        if (variable == null || polynomial.variable == null || variable.equals(polynomial.variable)) {
            ArrayList<Monomial> max = polynomial.monomials;
            int it = 0, i = 0;
            ArrayList<Monomial> newPolynomial = new ArrayList<>(max.size());
            while (i < monomials.size() && it < polynomial.monomials.size()) {
                Monomial m = monomials.get(i);
                if (m.getExponent() < max.get(it).getExponent()) {
                    newPolynomial.add(m);
                    i++;
                } else {
                    if (m.getExponent() > max.get(it).getExponent()) {
                        newPolynomial.add(max.get(it));
                        it++;
                    } else {
                        BigDecimal bd = m.getCoefficient().add(max.get(it).getCoefficient());
                        it++;
                        i++;
                        if (bd.abs().compareTo(EPSILON) >= 0) {
                            newPolynomial.add(new Monomial(bd, m.getVariable(), m.getExponent()));
                        }
                    }
                }
            }
            while (i < monomials.size()) {
                newPolynomial.add(monomials.get(i));
                i++;
            }
            while (it < polynomial.monomials.size()) {
                newPolynomial.add(polynomial.monomials.get(it));
                it++;
            }
            return (newPolynomial.size() > 0) ? new Polynomial(newPolynomial) : ZERO;
        }
        return null;
    }

    public Polynomial subtract(Polynomial p) {
        if (variable == null || p.variable == null || variable.equals(p.variable)) {
            ArrayList<Monomial> arr = new ArrayList<>(p.monomials.size());
            for (Monomial m : p.monomials) {
                arr.add(m.negate());
            }
            return add(new Polynomial(arr));
        }
        return null;
    }

    public Polynomial multiply(Polynomial p) {
        if (variable == null || p.variable == null || variable.equals(p.variable)) {
            ArrayList<Monomial> m = new ArrayList<>(monomials.size() * p.monomials.size());
            for (Monomial m1 : monomials) {
                for (Monomial m2 : p.monomials) {
                    m.add(new Monomial(m1.getCoefficient().multiply(m2.getCoefficient()),
                            ((m1.getVariable() == null) ? m2.getVariable() : m1.getVariable()),
                            m1.getExponent() + m2.getExponent()));
                }
            }
            return formatPolynomial(m);
        }
        return null;
    }

    public Polynomial differentiate() {
        ArrayList<Monomial> newPolynomial = new ArrayList<>(monomials.size());
        for (Monomial monomial : monomials) {
            if ((monomial.getExponent() > 0)) {
                newPolynomial.add(monomial.derivative());
            }
        }
        return (monomials.size() > 0) ? new Polynomial(newPolynomial) : Polynomial.ZERO;
    }

    public Polynomial integrate() {
        ArrayList<Monomial> newPolynomial = new ArrayList<>(monomials.size());
        for (Monomial monomial : monomials) {
            newPolynomial.add(monomial.antiderivative(variable));
        }
        return (monomials.size() > 0) ? new Polynomial(newPolynomial) : Polynomial.ZERO;
    }

    public String getVariable() {
        return (variable == null) ? "x" : variable;
    }
}
