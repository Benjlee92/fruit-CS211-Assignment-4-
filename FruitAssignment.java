import java.util.List;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.*;   
import java.io.*;   
import javax.swing.border.*;

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

class MainFrame extends JFrame {
   JFrame f;
   JPanel mainPanel;
   JPanel sPanel;
   JLabel background;
   JLabel correctBackground;
   JLabel correctFruitImage;
   JLabel shuffledWordLabel; 
   JLabel correctFruitLabel;
   JLabel timeLabel;
   JLabel vitaminsLabel;
   JLabel mineralsLabel;
   JLabel caloriesLabel;
   JLabel incorrectLabel;
   JTextField textField;
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
      Font f = new Font("Bradley Hand", Font.PLAIN, 70);
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
   
   public MainFrame() throws FileNotFoundException {
      f = new JFrame("Welcome to Fruit");
      int W = 800;
      int H = 900;
      f.setSize(W,H);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      mainPanel = new JPanel();
      mainPanel.setLayout(null);
      
      readFile();
      getImagePaths();
      shuffleFruit();
      startTimer();
      displayMainInterface();
      
      f.add(mainPanel);
      f.setVisible(true);
   }
   
   private void displayMainInterface() {
      
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
   }
      
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
   
   private void getImagePaths() {
      String fname;
      String fpath;
      for (int i = 0; i < fruits.size(); i++ ) {
         fname = fruits.get(i).name;
         fpath = fname.toLowerCase() + ".png";
         fruitImages.put(fname, fpath);
      }
   }
   
   private void startTimer() {
      startTime = System.currentTimeMillis();
   }
   
   private void endTimer() {
      endTime = System.currentTimeMillis();
   }

   private void shuffleFruit() {
      Random rand = new Random();
      randomIndex = rand.nextInt(fruits.size());
      randomFruit = fruits.get(randomIndex).name.toUpperCase();
      System.out.println(randomFruit);
      
      shuffledLetters = Arrays.asList(randomFruit.split(""));
      Collections.shuffle(shuffledLetters);
      System.out.println(shuffledLetters);
      
      for (int i =0; i < shuffledLetters.size(); i++) {
         shuffledName = String.join("",shuffledLetters);
      }
      
      System.out.println(shuffledName);
   }
   
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
       correctFruitLabel.setBounds(172, 437, 456, 88);
       correctFruitLabel.setFont(large());
       
       timeLabel = new JLabel("TIME: " +totalTime()/1000.0 + " s", SwingConstants.CENTER);
       timeLabel.setBounds(211, 522, 377, 50);
       timeLabel.setFont(small());
       
       vitaminsLabel = new JLabel("VITAMINS: " + fruits.get(randomIndex).vitamins, SwingConstants.CENTER);
       vitaminsLabel.setBounds(188, 562, 424, 50);
       vitaminsLabel.setFont(small());
       
       mineralsLabel = new JLabel("MINERALS: " + fruits.get(randomIndex).minerals, SwingConstants.CENTER);
       mineralsLabel.setBounds(188, 604, 424, 50);
       mineralsLabel.setFont(small());
       
       caloriesLabel = new JLabel("CALORIES: " + fruits.get(randomIndex).calories, SwingConstants.CENTER);
       caloriesLabel.setBounds(188, 647, 424, 50);
       caloriesLabel.setFont(small());
       
       continueButton = new JButton("CONTINUE");
       continueButton.setBounds(188, 728, 424, 80);
       continueButton.setFont(medium());
       continueButton.setOpaque(false);
       continueButton.setBorder(null);
       
       cListener = new ContinueListener();
       continueButton.addActionListener(cListener);
       
       sPanel.add(timeLabel);
       sPanel.add(continueButton);
       sPanel.add(caloriesLabel);
       sPanel.add(mineralsLabel);
       sPanel.add(vitaminsLabel);
       sPanel.add(correctFruitLabel);
       sPanel.add(correctFruitImage);
       sPanel.add(background);
       f.add(sPanel);
   }
   
   private void incorrect() {
      incorrectLabel.setVisible(true);
   }
   
   private void clear() {
      textField.setText("");
      incorrectLabel.setVisible(false);
   }
   
   private void check() {
      String text = textField.getText();
      if (text.toUpperCase().equals(randomFruit)) {
         correct();
      } else {
         incorrect();
      }
   }
   
   private void continueButtonAction() {
      startTimer();
      mainPanel.setVisible(true);
      sPanel.setVisible(false);
      shuffleFruit();
      shuffledWordLabel.setText(shuffledName);
      textField.setText("");
   }
   
   class ButtonListener implements ActionListener{  
      public void actionPerformed(ActionEvent action) {
         if (action.getSource() == checkButton) {
            check();
         } else if (action.getSource() == clearButton) {
            clear();
         } 
      }
   }
   
   class ContinueListener implements ActionListener {
      public void actionPerformed(ActionEvent action) {
         continueButtonAction();
      }
   }  
}

public class FruitAssignment {
   public static void main(String[] args) throws FileNotFoundException {
      new MainFrame();
   }
}