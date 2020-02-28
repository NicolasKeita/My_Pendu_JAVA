package com.example.pendu;

import com.sun.deploy.util.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.*;
import java.io.*;

public class Panel extends JPanel implements ActionListener {

    public String word = "", secretWord = "";
    public char[] tabChar;
    public int nbreCoup = 0;
    public JButton[] bouton;
//    public Graphics g;
    public int wrong_tries = 0;

    public void actionPerformed(ActionEvent e) {
        ((JButton)e.getSource()).setEnabled(false);
        char letter = ((JButton)e.getSource()).getText().charAt(0);
        System.out.println(letter);
        if (this._verifyWord(letter) == 1) {
            System.out.println("MOT TROUVE !");
        }
        else if (this._isInsideWord(letter) == 1) {
            int index = this.word.indexOf(letter);
            char d = this.secretWord.charAt(index);
            StringBuilder new_secretWord = new StringBuilder(this.secretWord);
            new_secretWord.setCharAt(index, letter);
            this.secretWord = new_secretWord.toString();

            this.repaint();
        }
        else {
            this.wrong_tries += 1;
            if (this.wrong_tries == 8) {
                System.out.println("You lost !");
                System.exit(0);
            }
            this.repaint();
            System.out.println("CHERCHE ENCORE  !");
        }
    }

    public int _isInsideWord(char c) {
        int index = this.word.indexOf(c);
        if (index == -1)
            return 0;
        else
            return 1;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static int countMatches(String text, String str) {
        if (isEmpty(text) || isEmpty(str)) {
            return 0;
        }

        int index = 0, count = 0;
        while (true) {
            index = text.indexOf(str, index);
            if (index != -1) {
                count ++;
                index += str.length();
            } else {
                break;
            }
        }

        return count;
    }

    public int _verifyWord(char c)
    {
        int occurenceNumber = countMatches(secretWord, "*");
        if (occurenceNumber > 1)
            return 0;
        int index = secretWord.indexOf('*');
        if (word.charAt(index)!= c)
            return 0;
        else
            return 1;
    }

    public Panel() {

        this._generateRandomWord();
        this.setLayout(new FlowLayout());

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

        body.setLayout(new GridLayout(5, 2));
        this.bouton = new JButton[26];
        int i = 0;
        for(char c : carac) {
            this.bouton[i] = new JButton(String.valueOf(c).toUpperCase());
            bouton[i].addActionListener(this);
            bouton[i].setPreferredSize(buttonDimension);
            body.add(bouton[i]);
            i++;
        }
        this.add(body);
//        this.add(body, FlowLayout.CENTER);
    }

    public void paintComponent(Graphics g) {
//        this.g = g;
        Font font = new Font("Courier", Font.PLAIN, 24);
        g.setFont(font);
        g.setColor(Color.white);
        g.fillRect(450,300,1920, 1080);
//        g.clearRect(450, 300, 200, 100);
        g.drawString("Le Jeu du Pendu", 450, 300);
        this._printImage(this.wrong_tries + 1, g);
        this._printSecretWord(g);
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
        //this._generateRandomWord();
        System.out.println(this.secretWord);
        System.out.println(this.word);
        g.drawString(this.secretWord, 450, 400);
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

