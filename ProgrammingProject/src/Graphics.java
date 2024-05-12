package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Graphics extends JPanel implements ActionListener{
    private static final int CANVAS_Width = 600;
    private static final int CANVAS_HEIGHT = 400;
    private static final int TIMER_DELAY = 30;

    private List<Person> people;
    public Graphics(){
        this.people = new ArrayList<>();
        this.setPreferredSize(new Dimension (CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setBackground(Color.White);
        Timer timer= new Timer (TIMER_DELAY, this);
        timer.start();

        //Initialize a person object
        people.add(new Person());
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for (Person person : people){
            person.draw(g);
        }
    }

}
