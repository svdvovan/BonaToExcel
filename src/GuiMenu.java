/**
 * Created by SretenskyVD on 28.08.2018.
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GuiMenu extends JFrame {




        private static final long serialVersionUID = 1L;

        private JTextField textField;
        private JButton    button1;
        private JButton    button2;
        private JButton    button3;

        public GuiMenu() {
            super("Выбор категории для парсинга");
            createGUI();
        }

        public void createGUI() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            button1 = new JButton("Лосины");
            button1.setActionCommand("Button 1 was pressed!");
            panel.add(button1);

            button2 = new JButton("Топы");
            button2.setActionCommand("Button 2 was pressed!");
            panel.add(button2);

            button3 = new JButton("Комбинезоны");
            button3.setActionCommand("Button 3 was pressed!");
            panel.add(button3);

            textField = new JTextField();
            textField.setColumns(23);
            panel.add(textField);

            ActionListener actionListener = new TestActionListener();

            button1.addActionListener(actionListener);
            button2.addActionListener(actionListener);
            button3.addActionListener(actionListener);

            getContentPane().add(panel);
            setPreferredSize(new Dimension(320, 100));
        }

        public class TestActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                textField.setText(e.getActionCommand());
            }
        }
}
