package ro.tuc.tp;

import ro.tuc.tp.GUI.Controller;
import ro.tuc.tp.GUI.SimulationFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SimulationFrame frame = new SimulationFrame("Queue Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Controller controller = new Controller(frame);

    }
}
