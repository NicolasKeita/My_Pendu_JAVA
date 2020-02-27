package com.example.pendu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.*;
import java.io.*;

public class Panel extends JPanel {

    public String word = "", secretWord = "";
    public char[] tabChar;
    public int nbreCoup = 0;
    public JButton[] bouton;

    public Panel() {
    }
    public void paintComponent(Graphics g) {
        Font font = new Font("Courier", Font.PLAIN, 24);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Le Jeu du Pendu", 450, 100);
        this._printImage(1, g);
        this._printSecretWord(g);

        char[] carac = {'a', 'b','c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r',
                's', 't' ,'u', 'v', 'w', 'x',
                'y', 'z'};

        Dimension dim = new Dimension(410, 200);
        Dimension buttonDimension = new Dimension(50,30);
        JPanel body = new JPanel();
        body.setPreferredSize(dim);
        body.setBackground(Color.white);

        this.bouton = new JButton[26];
        int i = 0;
        for(char c : carac){
            this.bouton[i] = new JButton(String.valueOf(c).toUpperCase());
            //bouton[i].addActionListener(bl);
            bouton[i].setPreferredSize(buttonDimension);
            body.add(bouton[i]);
            i++;
        }
    }
    public void _printImage(int id, Graphics g)
    {
        String path = "assets/" + Integer.toString(id) + ".jpg";
        try {
            Image img = ImageIO.read(new File(path));
            g.drawImage(img, 0, 0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void _printSecretWord(Graphics g)
    {
        Font font = new Font("Courier", Font.PLAIN, 32);
        g.setFont(font);
        g.setColor(Color.BLUE);
        this._generateRandomWord();
        System.out.println(this.secretWord);
        System.out.println(this.word);
        g.drawString(this.secretWord, 450, 200);
    }

    public void _generateRandomWord()
    {
        int i = (int)(Math.random()*336529);

        try {
            LineNumberReader fnr = new LineNumberReader(new FileReader(new File("assets/dictionnaire.txt")));
            int carac;
            this.word = "";
            this.secretWord= "";
            while((carac = fnr.read()) != -1){
                if(fnr.getLineNumber() == (i+1))
                    break;

                else{
                    if(fnr.getLineNumber() == i){
                        this.word += (char)carac;
                    }
                }
            }

            this.word = this.word.trim().toUpperCase();

            for(int j = 0; j < this.word.length(); j++)
            {
                this.secretWord += (this.word.charAt(j) != '\'' && this.word.charAt(j) != '-') ? "*" : this.word.charAt(j);
            }

            fnr.close();
            this.nbreCoup = 0;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erreur de chargement depuis le fichier de mots !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur de chargement depuis le fichier de mots !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }

        this.tabChar = this.secretWord.toCharArray();
    }


}

/*
class BoutonListener implements ActionListener {
    public void actionPerformed(ActionEvent e){
        ((JButton)e.getSource()).setEnabled(false);
        controler.control(((JButton)e.getSource()).getText().charAt(0));
    }
}*/



