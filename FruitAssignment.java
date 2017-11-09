/*Benjamin Lee
ID: 950578554
11/7/2017
Assignment 4 Fruit
Description: This program reads a txt file containing names of fruits and their vitamins. The program displays a GUI and displays a 
shuffled fruit name. the user then enters the correct name of the fruit. Whrn the user is correct, the image of the fruit is displayed
along with the vitamins .

NOTE: I use custom fruit images, and I added 2 new fruits to the original txt file (strawberry and kiwi)
*/

import java.util.List;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;   
import java.io.*;   
import javax.swing.border.*;

//my Fruit class.
class Fruit {
   String name;
   String vitamins;
   String minerals;
   String calories;
   
   @Override
   public String toString() {
        return (this.name+ " " + this.vitamins + " " + this.minerals + " " + this.calories);
   }
}
// the mainframe class that extends the JFrame and allows for the views to be created.
class MainFrame extends JFrame {
   // my variables I use
   JFrame f;
   JPanel iPanel;
   JPanel mainPanel;
   JPanel sPanel;
   JLabel introBackground;
   JLabel background;
   JLabel correctBackground;
   JLabel correctFruitImage;
   JLabel shuffledWordLabel; 
   JLabel correctFruitLabel;
   JLabel timeLabel;
   JLabel vitaminALabel;
   JLabel vitaminBLabel;
   JLabel vitaminCLabel;
   JLabel incorrectLabel;
   JTextField textField;
   JButton playButton;
   JButton checkButton;
   JButton clearButton;
   JButton continueButton;
   ButtonListener bListener;
   ContinueListener cListener;
   
   ArrayList<Fruit> fruits = new ArrayList<Fruit>();
   List<String> shuffledLetters = new ArrayList<String>();
   HashMap<String, String> fruitImages = new HashMap<String, String>();

   String randomFruit;
   String shuffledName;
   int randomIndex;
   
   double startTime;
   double endTime;
   double totalTime() {
      return (endTime - startTime);
   }
   
   Font large() {
      Font f = new Font("Bradley Hand", Font.PLAIN, 67);
      return f;
   }
   
   Font medium() {
      Font f = new Font("Bradley Hand", Font.PLAIN, 50);
      return f;
   }
   
   Font small() {
      Font f = new Font("Bradley Hand", Font.PLAIN, 30);
      return f;
   }
   
   // this gets implemented in the main method.
   public MainFrame() throws FileNotFoundException {
      f = new JFrame("Welcome to Fruit");
      int W = 800;
      int H = 900;
      f.setSize(W,H);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // I rea the file and get the image paths
      readFile();
      getImagePaths();
      //I display the intro screen 
      displayIntro();
      f.setVisible(true);
   }
   // this method displays the introduction screen when the program runs for the first time.
   private void displayIntro() {
      iPanel = new JPanel();
      iPanel.setLayout(null);
      
      introBackground = new JLabel();
      introBackground.setBounds(0,0,800,900);
      introBackground.setIcon(new ImageIcon("intro.png"));
      
      playButton = new JButton("PLAY");
      playButton.setBounds(216, 582, 369, 111);
      playButton.setFont(medium());
      playButton.setBorder(null);
      playButton.setOpaque(false);
      
      bListener = new ButtonListener();
      //this button action handles when the user htis the play button.
      playButton.addActionListener(bListener);
      
      iPanel.add(playButton);
      iPanel.add(introBackground);
      
      f.add(iPanel);
   }
   
   // this method gets called when the user presses the play button. It displays the main interface for the game.
   private void displayMainInterface() {
      iPanel.setVisible(false);
      mainPanel = new JPanel();
      mainPanel.setLayout(null);
      shuffleFruit();
      startTimer();
      
      background = new JLabel();
      background.setIcon(new ImageIcon("background.png"));
      background.setBounds(0,0,800,900);
      
      shuffledWordLabel = new JLabel(shuffledName, SwingConstants.CENTER);
      shuffledWordLabel.setBounds(115, 251, 570, 88);
      shuffledWordLabel.setFont(large());
      
      textField = new JTextField();
      textField.setBounds(158, 418, 488, 87);
      textField.setFont(large());
      textField.setHorizontalAlignment(SwingConstants.CENTER);
      textField.setBackground(new Color(0,0,0,0));
      textField.setBorder(null);
      
      checkButton = new JButton("CHECK");
      checkButton.setBounds(424, 624, 265, 80);
      checkButton.setOpaque(false);
      checkButton.setBorder(null);
      checkButton.setFont(medium());
      
      clearButton = new JButton("CLEAR");
      clearButton.setBounds(115, 624, 265, 80);
      clearButton.setOpaque(false);
      clearButton.setBorder(null);
      clearButton.setFont(medium());
      
      incorrectLabel = new JLabel("That's wrong :(", SwingConstants.CENTER);
      incorrectLabel.setBounds(150, 531, 500, 50);
      incorrectLabel.setFont(small());
      incorrectLabel.setVisible(false);
      
      bListener = new ButtonListener();
      checkButton.addActionListener(bListener);
      clearButton.addActionListener(bListener);
         
      mainPanel.add(incorrectLabel);
      mainPanel.add(clearButton);
      mainPanel.add(checkButton);
      mainPanel.add(textField);
      mainPanel.add(shuffledWordLabel);
      mainPanel.add(background);
      
      f.add(mainPanel);
   }
   // this is the method to handle reading the txt file.
   private void readFile() throws FileNotFoundException {
      Scanner input = new Scanner(new File("fruit.txt"));
      while (input.hasNext()) {
         Fruit f = new Fruit();
         f.name = input.next();
         f.vitamins = input.next();
         f.minerals = input.next();
         f.calories = input.next();
         fruits.add(f);
      }
   }
   // this is the method to handle getting the image paths.
   private void getImagePaths() {
      String fname;
      String fpath;
      for (int i = 0; i < fruits.size(); i++ ) {
         fname = fruits.get(i).name;
         fpath = fname.toLowerCase() + ".png";
         fruitImages.put(fname, fpath);
      }
   }
   // my timer methods. these get called when the user starts the game and when the user checks their answer.
   private void startTimer() {
      startTime = System.currentTimeMillis();
   }
   
   private void endTimer() {
      endTime = System.currentTimeMillis();
   }
   
   // this is the method to randomize the order of the fruit, and the name of the fruit.
   private void shuffleFruit() {
      Random rand = new Random();
      randomIndex = rand.nextInt(fruits.size());
      randomFruit = fruits.get(randomIndex).name.toUpperCase();      
      shuffledLetters = Arrays.asList(randomFruit.split(""));
      Collections.shuffle(shuffledLetters);
      
      for (int i =0; i < shuffledLetters.size(); i++) {
         shuffledName = String.join("",shuffledLetters);
      }
   }
   
   // this method handles when the user is correct. Im not sure if creating a new panel each time the user is correct is the right way to do this.
   // I think this might take up more memory than needs. But im not sure exactly how to optimize. I think that a new panel is being added over and over and over, 
   // but Im not sure.
   private void correct() {
       endTimer();
       mainPanel.setVisible(false);
       incorrectLabel.setVisible(false);

       sPanel = new JPanel();
       sPanel.setLayout(null);
       
       background = new JLabel();
       background.setBounds(0,0,800,900);
       background.setIcon(new ImageIcon("correct.png"));
       background.setLayout(null);
       
       correctFruitImage = new JLabel();
       correctFruitImage.setBounds(313, 208, 175, 175);
       correctFruitImage.setIcon(new ImageIcon(fruitImages.get(randomFruit)));
       
       correctFruitLabel = new JLabel(randomFruit, SwingConstants.CENTER);
       correctFruitLabel.setBounds(141, 440, 518, 88);
       correctFruitLabel.setFont(large());
       
       timeLabel = new JLabel("TIME: " +totalTime()/1000.0 + " s", SwingConstants.CENTER);
       timeLabel.setBounds(211, 522, 377, 50);
       timeLabel.setFont(small());
       
       vitaminALabel = new JLabel("VITAMIN A: " + fruits.get(randomIndex).vitamins, SwingConstants.CENTER);
       vitaminALabel.setBounds(188, 562, 424, 50);
       vitaminALabel.setFont(small());
       
       vitaminBLabel = new JLabel("VITAMIN B: " + fruits.get(randomIndex).minerals, SwingConstants.CENTER);
       vitaminBLabel.setBounds(188, 604, 424, 50);
       vitaminBLabel.setFont(small());
       
       vitaminCLabel = new JLabel("VITAMIN C: " + fruits.get(randomIndex).calories, SwingConstants.CENTER);
       vitaminCLabel.setBounds(188, 647, 424, 50);
       vitaminCLabel.setFont(small());
       
       continueButton = new JButton("CONTINUE");
       continueButton.setBounds(188, 728, 424, 80);
       continueButton.setFont(medium());
       continueButton.setOpaque(false);
       continueButton.setBorder(null);
       
       cListener = new ContinueListener();
       continueButton.addActionListener(cListener);
       
       sPanel.add(timeLabel);
       sPanel.add(continueButton);
       sPanel.add(vitaminCLabel);
       sPanel.add(vitaminBLabel);
       sPanel.add(vitaminALabel);
       sPanel.add(correctFruitLabel);
       sPanel.add(correctFruitImage);
       sPanel.add(background);
       f.add(sPanel);
   }
   
   // this method handles when the user is wrong.
   private void incorrect() {
      incorrectLabel.setVisible(true);
   }
   // this method handles when the user hits the clear button.
   private void clear() {
      textField.setText("");
      incorrectLabel.setVisible(false);
   }
   // this method handles when the user checks their answer.
   private void check() {
      String text = textField.getText();
      if (text.toUpperCase().equals(randomFruit)) {
         correct();
      } else {
         incorrect();
      }
   }
   // this method handles when the user presses the continue button after the correct answer has been checked.
   private void continueButtonAction() {
      startTimer();
      mainPanel.setVisible(true);
      sPanel.setVisible(false);
      shuffleFruit();
      shuffledWordLabel.setText(shuffledName);
      textField.setText("");
   }
   
   // my button listener, these are connected to the checkButton, clearButton, and the playButton.
   class ButtonListener implements ActionListener{  
      public void actionPerformed(ActionEvent action) {
         if (action.getSource() == checkButton) {
            check();
         } else if (action.getSource() == clearButton) {
            clear();
         }  else if (action.getSource() == playButton) {
            displayMainInterface();
         }
      }
   }
   // this is the action listener connected to the continue button.
   class ContinueListener implements ActionListener {
      public void actionPerformed(ActionEvent action) {
         continueButtonAction();
      }
   }  
}
// main 
public class FruitAssignment {
   public static void main(String[] args) throws FileNotFoundException {
      new MainFrame();
   }
}