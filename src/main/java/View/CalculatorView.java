package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

public class CalculatorView extends JFrame {
    public final static int INVALID_INPUT=1;
    public static final int VALID_INPUT=0;
    private final JTextField display=new JTextField();
    private final Font basicFont=display.getFont().deriveFont(display.getFont().getSize()*2.0f);
    private final Font errorFont=basicFont.deriveFont(Font.BOLD | Font.ITALIC);
    private final Font validFont;
    private boolean processedInput=false;
    private final ButtonPanel buttonPanel=new ButtonPanel();
    private final History history;

    {
        Map attributes = basicFont.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        validFont=basicFont.deriveFont(attributes);

        display.setFont(basicFont);
    }

    public CalculatorView(){

        display.setFocusable(true);
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(processedInput&&e.getKeyCode()!=KeyEvent.VK_ENTER){
                    display.setForeground(Color.BLACK);
                    display.setFont(basicFont);
                    processedInput=false;
                }
            }
        });
        JPanel displayPanel=new JPanel(new GridLayout(1, 1));
        Dimension dimension=new Dimension(500, 70);
        displayPanel.setPreferredSize(dimension);
        displayPanel.setMaximumSize(dimension);
        displayPanel.setMaximumSize(dimension);
        displayPanel.add(display);

        history=new History(this);

        dimension=new Dimension(500, 180);
        buttonPanel.setPreferredSize(dimension);
        buttonPanel.setMaximumSize(dimension);
        buttonPanel.setMinimumSize(dimension);

        Container panel=this.getContentPane();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(displayPanel);
        panel.add(history);
        panel.add(buttonPanel);

        this.pack();
        setTitle("Polynomial Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    public void addButtonListener(String button, ActionListener l){
        buttonPanel.addButtonListener(button, l);
    }
    public void setText(String text){
        display.setText(text);
    }
    public void addDisplayListener(ActionListener l){
        display.addActionListener(l);
    }
    public String getText(){ return display.getText(); }
    public void setFont(int type){
        if(type==INVALID_INPUT){
            setErrorFont();
        }
        else if(type==VALID_INPUT){
            setValidFont();
        }
    }
    public void updateHistory(String newHistory){history.setText(newHistory);}
    public void setErrorFont(){ display.setForeground(Color.RED); processedInput=true; display.setFont(errorFont); }
    public void setValidFont(){ display.setForeground(Color.BLACK); processedInput=true; display.setFont(validFont); }
}
