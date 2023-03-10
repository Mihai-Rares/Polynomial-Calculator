package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class History extends JPanel {
    private final JPanel hideHistory=new JPanel();
    private final JPanel showHistory=new JPanel();
    private final JTextArea history=new JTextArea();
    private boolean hidden=true;
    public History(JFrame frame){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        hideHistory.setLayout(new GridLayout(1, 1));
        showHistory.setLayout(new BoxLayout(showHistory, BoxLayout.Y_AXIS));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));

        Dimension dimension=new Dimension(500, 15);

        hideHistory.setPreferredSize(dimension);
        hideHistory.setMinimumSize(dimension);
        hideHistory.setSize(dimension);
        hideHistory.setMaximumSize(dimension);

        buttonPanel.setPreferredSize(dimension);
        buttonPanel.setMinimumSize(dimension);
        buttonPanel.setSize(dimension);
        buttonPanel.setMaximumSize(dimension);

        Dimension historyDimension=new Dimension(500, 100);
        JScrollPane scrollPane=new JScrollPane(history, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(historyDimension);
        scrollPane.setMaximumSize(historyDimension);
        scrollPane.setMinimumSize(historyDimension);

        history.setEditable(false);
        history.setFont(history.getFont().deriveFont(Font.BOLD, 1.2f*history.getFont().getSize()));
        history.setForeground(Color.GRAY);

        JPanel pane= this;
        ActionListener actionListener= e -> {
            if(hidden){
                pane.remove(hideHistory);
                pane.add(showHistory);
            }
            else{
                pane.remove(showHistory);
                pane.add(hideHistory);
            }
            hidden=!hidden;
            frame.revalidate();
            frame.repaint();
            frame.pack();
        };
        JButton historyButton=new JButton("-");
        historyButton.addActionListener(actionListener);
        hideHistory.add(historyButton);

        historyButton=new JButton("-");
        historyButton.addActionListener(actionListener);
        buttonPanel.add(historyButton);

        showHistory.add(scrollPane);
        showHistory.add(buttonPanel);

        add(hideHistory);
    }
    public void setText(String text){
        history.setText(text);
    }
}
