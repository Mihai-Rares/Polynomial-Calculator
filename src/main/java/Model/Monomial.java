package Model;

import java.math.BigDecimal;

public class Monomial implements  Comparable<Monomial>{
    public static final BigDecimal NONE=new BigDecimal("-1");
    public static final Monomial ZERO=new Monomial(BigDecimal.ZERO, null, 0);
    private final int exponent;
    private final BigDecimal coefficient;
    private final String variable;

    public Monomial(BigDecimal coefficient, String variable, int exponent){
        this.coefficient=coefficient;
        this.variable=((exponent>0)?variable:null);
        this.exponent=exponent;
    }

    public int getExponent() {
        return exponent;
    }
    public String getVariable(){
        return variable;
    }
    public BigDecimal getCoefficient() {
        return coefficient;
    }

    @Override
    public int compareTo(Monomial o) {
        if(variable==null){
            return -1*o.exponent;
        }
        if(o.variable==null||variable.equals(o.variable)){
            return exponent-o.exponent;
        }
        else {
            return variable.compareTo(o.variable);
        }
    }

    public Monomial negate(){
        return new Monomial(coefficient.negate(), variable, exponent);
    }

    @Override
    public String toString(){
        String str="";
        if(exponent==0){
            return coefficient+"";
        }
        if(coefficient.compareTo(BigDecimal.ZERO)<0){
            str+=(coefficient.compareTo(NONE)==0)?"-":coefficient;
        }
        else{
            str+="+"+((coefficient.compareTo(BigDecimal.ONE)==0)?"":coefficient);
        }

        if(exponent > 0){
            str+=variable;
            if(exponent!=1){
                str+="^"+exponent;
            }
        }
        return str;
    }
    public Monomial derivative(){
        if(exponent==0){
            return ZERO;
        }
        return new Monomial(coefficient.multiply(new BigDecimal(exponent)), variable, exponent-1);
    }
    public Monomial antiderivative(String variable){
        if(coefficient.equals(BigDecimal.ZERO)){
            return ZERO;
        }
        if(variable==null) { variable="x"; }
        return new Monomial(coefficient.divide(new BigDecimal(exponent+1)),
                ((exponent==0)?variable:this.variable), exponent+1);
    }
}
