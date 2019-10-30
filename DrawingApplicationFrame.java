/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame
{
    public JPanel firstLine = new JPanel();
    public JPanel secondLine = new JPanel();
    public JPanel topPanel = new JPanel();
    public JPanel drawPanel = new DrawPanel(); 
    public JButton undo = new JButton("Undo");
    public JButton clear = new JButton ("Clear");
    public JComboBox shapeBox = new JComboBox();
    public JCheckBox filledBox = new JCheckBox();
    public JCheckBox gradient = new JCheckBox();
    public JButton colorBox1 = new JButton("1st Color...");
    public JButton colorBox2 = new JButton("2nd Color...");
    public JTextField lineWidth = new JTextField("2",2);
    public JTextField dashLength = new JTextField("2",2);
    public JCheckBox dashed = new JCheckBox();
    public JLabel statusLabel = new JLabel(" ");
    public JLabel shape = new JLabel("Shape:");
    public JLabel filled1 = new JLabel("Filled");
    public JLabel useGradient = new JLabel("Use Gradient");
    public JLabel lineWidthLabel = new JLabel("Line Width:");
    public JLabel dashLengthLabel = new JLabel("Dash Length:");
    public JLabel dashed1 = new JLabel("Dashed");
            
    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.

    // create the widgets for the firstLine Panel.

    //create the widgets for the secondLine Panel.

    // Variables for drawPanel.

  

    static ArrayList<MyShapes> shapesDrawn = new ArrayList<>();
    public Color color1 = (Color.BLACK);
    public Color color2 = (Color.BLACK);    
    
    
    
    // add status label
  
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {
        // add widgets to 
        shapeBox.addItem("Rectangle");
        shapeBox.addItem("Oval");
        shapeBox.addItem("Line");
        firstLine.setLayout(new FlowLayout());
        secondLine.setLayout(new FlowLayout());
        firstLine.add(undo);
        firstLine.add(clear);
        firstLine.add(shape);
        firstLine.add(shapeBox);
        firstLine.add(filledBox);
        firstLine.add(filled1);
        secondLine.add(gradient);
        secondLine.add(useGradient);
        secondLine.add(colorBox1);
        secondLine.add(colorBox2);
        secondLine.add(lineWidth);
        secondLine.add(lineWidthLabel);
        secondLine.add(dashLengthLabel);
        secondLine.add(dashLength);
        secondLine.add(dashed);
        secondLine.add(dashed1);
        topPanel.setLayout(new BorderLayout());
        topPanel.add(firstLine, BorderLayout.NORTH);
        topPanel.add(secondLine, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
                
        
     
        
        // firstLine widgets

        // secondLine widgets

        // add top panel of two panels

        // add topPanel to North, drawPanel to Center, and statusLabel to South
        
        //add listeners and event handlers
        undo.addActionListener(new ButtonHandler());
        clear.addActionListener(new ButtonHandler());
        colorBox1.addActionListener(new ButtonHandler());
        colorBox2.addActionListener(new ButtonHandler());
    }

    // Create event handlers, if needed
        public class ButtonHandler implements ActionListener {

        public ButtonHandler() {
            
        }
            @Override
            public void actionPerformed(ActionEvent ae){
                if(ae.getSource() == undo) {
                     if (!shapesDrawn.isEmpty()) {
                   shapesDrawn.remove(shapesDrawn.size() - 1);
                   repaint();
                }
                }
                if (ae.getSource() == clear){ 
                    shapesDrawn.clear();
                    repaint();
                }
                if (ae.getSource() == colorBox1){
                    
                 color1 = JColorChooser.showDialog(DrawingApplicationFrame.this, "Select a color", color1);
              
                    
                }
                if (ae.getSource() == colorBox2){
                color2 = JColorChooser.showDialog(DrawingApplicationFrame.this, "Select a color", color2);
     
                }
                }
    // Create a private inner class for the DrawPanel.
            }
    private class DrawPanel extends JPanel
    {
        public DrawPanel(){
            MouseHandler handler = new MouseHandler();
            addMouseListener(handler);
            addMouseMotionListener(handler);
        }
  
        @Override
        public void paintComponent(Graphics g)
        {
            
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g);
            for (MyShapes shape: shapesDrawn){
                shape.draw(g2d);
            }
    
            
                
            }
            
                
 
            //loop through and draw each shape in the shapes arraylist


        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {
              
            @Override
            public void mousePressed(MouseEvent event)
            { 
                Stroke stroke;
                int shapeCount;
                Paint paint;
                float lineLength = Float.parseFloat(lineWidth.getText());
                float[] dashWidth = {Float.parseFloat(dashLength.getText())};
                int initialX = event.getX();
                int initialY = event.getY();
                Point initPoint = new Point(initialX, initialY);
                Boolean filled = false;
                
                if (gradient.isSelected()) {
                    paint = new GradientPaint(0,0, color1, 50, 50, color2, true);
                } else { 
                paint = color1;
            }
           
          
              if (dashed.isSelected()) {
                  stroke = new BasicStroke(lineLength, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashWidth, 0);
              }
              else{
                  stroke = new BasicStroke(lineLength, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                
            }
              if (filledBox.isSelected()){
                  filled = true;
              }
              shapeCount = shapeBox.getSelectedIndex();
              
              if (shapeCount==0){
                  shapesDrawn.add(new MyRectangle(event.getPoint(), event.getPoint(), paint, stroke, filled));
              }
              if (shapeCount ==1){
                  shapesDrawn.add( new MyOval(event.getPoint(), event.getPoint(), paint, stroke, filled));
              }
              if (shapeCount ==2){
                  shapesDrawn.add(new MyLine(event.getPoint(), event.getPoint(), paint, stroke));
              }
            }

            @Override
            public void mouseReleased(MouseEvent event)
            {
        
                String pos = "(" + event.getPoint().x + "," + event.getPoint().y + ")";
                statusLabel.setText(pos);
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                shapesDrawn.get(shapesDrawn.size()-1).setEndPoint(event.getPoint());
               String pos = "(" + event.getPoint().x + "," + event.getPoint().y + ")";
               statusLabel.setText(pos);
               repaint();
               
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                statusLabel.setText(String.format("(%d,%d)", event.getX(), event.getY()));
            }
        }

    }
}
