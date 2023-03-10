package Controller;

import Model.CalculatorModel;
import View.CalculatorView;

public class CalculatorController {
    public CalculatorController(CalculatorView view, CalculatorModel model){
        view.addButtonListener("Division", e -> model.setDivision());
        view.addButtonListener("Multiplication", e -> model.setMultiplication());
        view.addButtonListener("Modulo", e -> model.setModulo());
        view.addButtonListener("Addition", e -> model.setAddition());
        view.addButtonListener("Subtraction", e -> model.setSubtraction());
        view.addButtonListener("Integration", e -> model.setIntegration());
        view.addButtonListener("Differentiation", e -> model.setDifferentiation());
        view.addButtonListener("Pin", e ->{
            String p=model.fixPolynomial();
            view.setText(p);
        });
        view.addButtonListener("Previous", e -> {
            String str=model.getPrev();
            if(str!=null){
                view.setFont(CalculatorView.VALID_INPUT);
                view.setText(str);
            }
        });
        view.addButtonListener("Next", e -> {
            String str=model.getNext();
            if(str!=null){
                view.setFont(CalculatorView.VALID_INPUT);
                view.setText(str);
            }
        });
        view.addButtonListener("Equal", e -> {
            String str=model.execute();
            if(str!=null){
                view.setFont(CalculatorView.VALID_INPUT);
                view.setText(str);
                view.updateHistory(model.getHistory());
            }
        });
        view.addDisplayListener(e -> {
            String polynomial= model.addPolynomial(view.getText().replaceAll("\\s", ""));
            if(polynomial==null){
                polynomial=view.getText();
                view.setFont(CalculatorView.INVALID_INPUT);
                view.setText(polynomial);
            }
            else {
                view.setFont(CalculatorView.VALID_INPUT);
                view.setText(polynomial);
            }
        });
    }
    public static void start(){
        CalculatorView v=new CalculatorView();
        new CalculatorController(v, new CalculatorModel());
        v.setVisible(true);
    }
}
