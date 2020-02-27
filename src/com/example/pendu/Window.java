package com.example.pendu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;


public class Window extends JFrame {
        public Window() {
                this.setTitle("My_Pendu_Hub");
                this.setSize(1100, 700);
                this.setLocationRelativeTo(null);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(new Panel());
                this.setBackground(Color.gray);
                this.setVisible(true);
        }
}
