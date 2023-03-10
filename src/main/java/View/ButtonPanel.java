package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class ButtonPanel extends JPanel {
    private final JButton prev=new JButton("\u2baa");
    private final JButton next=new JButton("\u2bab");
    private final JButton pinButton=new JButton("\uD83D\uDCCC");
    private final JButton mltpButton=new JButton("\u00D7");
    private final JButton divButton=new JButton("/");
    private final JButton modButton=new JButton("%");
    private final JButton addButton=new JButton("+");
    private final JButton sbtrButton=new JButton("-");
    private final JButton intButton=new JButton("\u222Bdx");
    private final JButton diffButton=new JButton("d/dx");
    private final JButton equalButton=new JButton("=");
    private final HashMap<String, JButton> buttons=new HashMap<>(11);
    {
        buttons.put("Previous", prev);
        buttons.put("Next", next);
        buttons.put("Pin", pinButton);
        buttons.put("Multiplication", mltpButton);
        buttons.put("Division", divButton);
        buttons.put("Addition", addButton);
        buttons.put("Modulo", modButton);
        buttons.put("Subtraction", sbtrButton);
        buttons.put("Integration", intButton);
        buttons.put("Differentiation", diffButton);
        buttons.put("Equal", equalButton);
        for(Map.Entry<String, JButton> j: buttons.entrySet()){
            JButton b=j.getValue();
            b.setFont(b.getFont().deriveFont(b.getFont().getSize()*1.5f));
        }
    }
    public ButtonPanel(){

        GridBagLayout layout=new GridBagLayout();
        GridBagConstraints g=new GridBagConstraints();
        setLayout(layout);
        int height=0;
        g.fill=GridBagConstraints.BOTH;
        g.weightx=0.1;
        g.weighty=0.1;

        g.gridx=0; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=2;
        add(prev, g);

        g.gridx=2; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=2;
        add(next, g);

        g.gridx=4; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=1;
        add(pinButton, g);

        height++;

        g.gridx=0; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=1;
        add(mltpButton, g);

        g.gridx=1; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=1;
        add(divButton, g);

        g.gridx=2; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=1;
        add(modButton, g);

        g.gridx=3; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=1;
        add(addButton, g);

        g.gridx=4; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=1;
        add(sbtrButton, g);

        height++;

        g.gridx=0; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=2;
        add(intButton, g);

        g.gridx=2; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=2;
        add(diffButton, g);

        g.gridx=4; g.gridy=height;
        g.gridheight=1;
        g.gridwidth=1;
        add(equalButton, g);
    }
    public void addButtonListener(String button, ActionListener a){
        buttons.get(button).addActionListener(a);
    }
}
