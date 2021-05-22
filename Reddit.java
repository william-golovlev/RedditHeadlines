package com.WilliamGolovlev.me;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reddit extends JFrame {

    static JPanel mainPanel = new JPanel();
    static JTextField textField = new JTextField(20);
    static JButton search = new JButton("Search");
    static JButton[] numbers = new JButton[28];
    static JButton[] headlines = new JButton[28];
    static JLabel[] votes = new JLabel[28];
    static JLabel title = new JLabel("\tHeadlines from r/", SwingConstants.CENTER);

    public Reddit(){
        mainPanel.setLayout(null);
        setUpComponents();

        title.setVisible(false);

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                title.setVisible(true);
                String str = textField.getText();

                title.setText("");
                title.setText("\tHeadline from r/"+str);

                getSubReddit(str);


            }
        });

        add(mainPanel);
        setTitle("Reddit Headline Search CIS36B");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void setUpComponents(){

        JLabel redditIcon = new JLabel();
        redditIcon.setBounds(10, 10, 100, 100);
        resizeRedditLogo(redditIcon);
        mainPanel.add(redditIcon);

        textField.setBounds(160, 20, 250, 30);
        search.setBounds(415, 23, 85, 25);
        textField.setBackground(Color.decode("#a30000"));

        //Border roundedBorder = new LineBorder(new Color(210,210,210), 1, true);
        //textField.setBorder(roundedBorder);

        search.setOpaque(true);
        search.setBorderPainted(false);
        search.setBackground(Color.decode("#fa3c16"));
        search.setForeground(Color.WHITE);
        search.setBorder(BorderFactory.createRaisedBevelBorder());

        int y = 120;

        for (int i = 0; i < numbers.length;i++){
            numbers[i] = new JButton(String.valueOf(i+1));
            numbers[i].setBounds(20, y, 40,30);
            numbers[i].setBackground(Color.decode("#fa3c16"));
            numbers[i].setOpaque(true);
            numbers[i].setForeground(Color.WHITE);
            numbers[i].setBorder(BorderFactory.createLoweredSoftBevelBorder());

            y+= 40;

            numbers[i].setBorderPainted(false);
            mainPanel.add(numbers[i]);
        }

        int y2 = 120;

        for (int i = 0; i < headlines.length;i++){

            headlines[i] = new JButton("");
            headlines[i].setBounds(70, y2, 650,30);
            headlines[i].setBackground(Color.decode("#000000"));
            headlines[i].setOpaque(true);
            headlines[i].setForeground(Color.WHITE);
            headlines[i].setBorder(BorderFactory.createLoweredSoftBevelBorder());
            y2+= 40;
            headlines[i].setBorderPainted(false);
            mainPanel.add(headlines[i]);

        }

        int y3 = 120;

        JLabel voteTitle = new JLabel("Votes");
        voteTitle.setFont(new Font("Monaco", Font.BOLD, 33));
        voteTitle.setForeground(Color.WHITE);
        voteTitle.setBounds(755, 70, 120, 30);
        mainPanel.add(voteTitle);

        for (int i = 0; i < votes.length;i++){

            votes[i] = new JLabel("");
            votes[i].setBounds(750, y3, 120,30);
            votes[i].setForeground(Color.WHITE);
            votes[i].setHorizontalAlignment(SwingConstants.CENTER);
            votes[i].setBorder(BorderFactory.createRaisedBevelBorder());
            y3+= 40;
            votes[i].setBorder(BorderFactory.createRaisedBevelBorder());
            mainPanel.add(votes[i]);
        }

        textField.setForeground(Color.WHITE);

        title.setBorder(BorderFactory.createRaisedBevelBorder());
        title.setBounds(105, 70, 605, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        mainPanel.add(title);
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.add(textField);
        mainPanel.add(search);

    }

    public static void getSubReddit(String sub){


        try
        {
            Document doc = Jsoup.connect("https://old.reddit.com/r/"+sub).get();
            Elements el = doc.select("p.title");
            Elements score = doc.select("div.score.unvoted");

            int i,j;

            for (i=0,j=0;i<el.size() && j<score.size();i++,j++){
                headlines[i].setText(el.get(i).text());
                votes[j].setText(score.get(j).text());
            }

        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public static void resizeRedditLogo(JLabel label){

        try {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon("../RedditGUI/src/reddit1.png").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            label.setIcon(imageIcon);
            label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public static void main(String[] args) {
        new Reddit();
    }



}