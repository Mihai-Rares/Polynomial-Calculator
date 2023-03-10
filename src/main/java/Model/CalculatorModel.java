package Model;

import java.util.ArrayList;

public class CalculatorModel {
    private Operation operation;
    private final Operation addition=new Addition();
    private final Operation subtraction=new Subtraction();
    private final Operation multiplication=new Multiplication();
    private final Division division=new Division();
    private final Modulo modulo=new Modulo();
    private final Differentiation differentiation=new Differentiation();
    private final Integration integration=new Integration();
    private final Queue<Polynomial> list= new Queue<>(20);
    private final Queue<String[]> history=new Queue<>(10);
    int iterator=-1;
    public void setAddition(){
        operation=addition;
    }
    public void setSubtraction(){
        operation=subtraction;
    }
    public void setMultiplication(){
        operation=multiplication;
    }
    public void setDivision(){
        operation=division;
    }
    public void setModulo(){
        operation=modulo;
    }
    public void setDifferentiation(){
        operation=differentiation;
    }
    public void setIntegration(){
        operation=integration;
    }
    public String addPolynomial(String polynomial){
        Polynomial p=Polynomial.getInstance(polynomial);
        if(p==null){
            return null;
        }
        list.add(p);
        return p.toString();
    }
    public String getPrev(){
        if(list.getSize()==1){
            return list.get(0).toString();
        }
        if(iterator==-1){
            iterator=list.getSize()-2;
            return list.get(iterator).toString();
        }
        if(iterator==0){
            return list.get(0).toString();
        }
        iterator--;
        return list.get(iterator).toString();
    }
    public String getNext(){
        if(iterator==-1){
            return null;
        }
        if(iterator==list.getSize()-1){
            return list.get(iterator).toString();
        }
        iterator++;
        return list.get(iterator).toString();
    }
    public String execute(){
        if(operation==null) return null;
        String str= operation.perform();
        operation=null;
        iterator=list.getSize()-1;
        return  str;
    }
    public String fixPolynomial(){
        if(iterator==-1) return null;
        if(iterator==list.getSize()-1) return list.get(iterator).toString();
        Polynomial p=list.get(iterator);
        list.add(p);
        iterator=list.getSize()-1;
        return p.toString();
    }
    private interface Operation{
        String perform();
    }
    private class Addition implements Operation{
        @Override
        public String perform() {
            if(list.getSize()>=2){
                int index=list.getSize()-1;
                Polynomial p=list.get(index).add(list.get(index-1));
                String[] operationToString=new String[]{list.get(index-1).toString()+"\n+\n",
                        list.get(index).toString()+"\n=\n", p.toString()};
                history.add(operationToString);
                list.add(p);
                return p.toString();
            }
            return null;
        }
    }
    private class Subtraction implements Operation{
        @Override
        public String perform() {
            if(list.getSize()>=2){
                int index=list.getSize()-1;
                Polynomial p=list.get(index-1).subtract(list.get(index));
                String[] operationToString=new String[]{list.get(index-1).toString()+"\n-\n",
                        list.get(index).toString()+"\n=\n", p.toString()};
                history.add(operationToString);
                list.add(p);
                return p.toString();
            }
            return null;
        }
    }
    private class Multiplication implements Operation{
        @Override
        public String perform() {
            if(list.getSize()>=2){
                int index=list.getSize()-1;
                Polynomial p=list.get(index-1).multiply(list.get(index));
                String[] operationToString=new String[]{list.get(index-1).toString()+"\n\u00D7\n",
                        list.get(index).toString()+"\n=\n", p.toString()};
                history.add(operationToString);
                list.add(p);
                return p.toString();
            }
            return null;
        }
    }
    private class Division implements Operation{
        @Override
        public String perform() {
            if(list.getSize()>=2){
                int index=list.getSize()-1;
                Polynomial p=list.get(index-1).divide(list.get(index))[0];
                String[] operationToString=new String[]{list.get(index-1).toString()+"\n\u00F7\n",
                        list.get(index).toString()+"\n=\n", p.toString()};
                history.add(operationToString);
                list.add(p);
                return p.toString();
            }
            return null;
        }
    }
    private class Modulo implements Operation{
        @Override
        public String perform() {
            if(list.getSize()>=2){
                int index=list.getSize()-1;
                Polynomial p=list.get(index-1).divide(list.get(index))[1];
                String[] operationToString=new String[]{list.get(index-1).toString()+"\n%\n",
                        list.get(index).toString()+"\n=\n", p.toString()};
                history.add(operationToString);
                list.add(p);
                return p.toString();
            }
            return null;
        }
    }
    private class Differentiation implements  Operation{
        @Override
        public String perform() {
            if(list.getSize()>0){
                Polynomial p=list.get(list.getSize()-1).differentiate();
                String[] operationToString=new String[]{"d/d"+list.get(list.getSize()-1).getVariable()+" |\n",
                        list.get(list.getSize()-1).toString()+"\n=\n", p.toString(), ""};
                history.add(operationToString);
                list.add(p);
                return p.toString();
            }
            return null;
        }
    }
    private class Integration implements  Operation{
        @Override
        public String perform() {
            if(list.getSize()>0){
                Polynomial p=list.get(list.getSize()-1).integrate();
                String[] operationToString=new String[]{"\u222Bd"+list.get(list.getSize()-1).getVariable()+" |\n",
                        list.get(list.getSize()-1).toString()+"\n=\n", p.toString(), ""};
                history.add(operationToString);
                list.add(p);
                return p.toString();
            }
            return null;
        }
    }
    public String getHistory(){
        if(history.getSize()==0) return null;
        StringBuilder historyStr=new StringBuilder();
        for(int i=0; i<history.getSize(); i++){
            int numElm=0;
            for(String s: history.get(i)){
                numElm++;
                historyStr.append(" ");
                historyStr.append(s);
            }
            if(i!=(history.getSize()-1)) historyStr.append("\n\n");
            else if(numElm==4) historyStr.append("\n");
        }
        return historyStr.toString();
    }
    private static class Queue <T> {
        private final ArrayList<T> arr;
        private final int capacity;
        private int start=0;
        private int end=-1;
        private Queue(int capacity) {
            this.capacity = capacity;
            arr=new ArrayList<>(capacity);
            for(int i=0; i<capacity; i++){
                arr.add(null);
            }
        }
        public void add(T element){
            if(getSize()==capacity){
                start=(start+1)%capacity;
            }
            end=(end+1)%capacity;
            arr.set(end, element);
        }
        public T get(int index){
            return (index<0||index>(this.getSize()-1))?null:arr.get((start+index)%capacity);
        }
        public int getSize(){
            if(end==-1) return 0;
            return (end>=start)?(end-start+1):(end+capacity-start+1);
        }
    }
}
