/*
 * 
 * @author : Malhaar Patel
 *
 * */



package com.humber.java; // Package name



//All the imported class used in this game
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


	

//Creating the child class GuessTheWord which is extending from the parent class called JFrame
public class GuessTheWord extends JFrame {

	//Global Variables and instances
	
	ArrayList stringArrayList = new ArrayList<>();
	ArrayList toCheckWordPresence = new ArrayList<>();
	
	
	StringBuilder resultString = new StringBuilder();
	List<Integer> resultIndexList = new ArrayList<>();
	String randomword;
	
	int errorCount = 0;
	
	String[] randomWordArray;
	
//	GUI Code 
	
	//Labels
	JLabel greetingJLabel;
	JLabel enterLabel;
	JLabel addNewLetterLabel;
	JLabel resultLabel;
	JLabel errorLabel;

	//textfields
	JTextField guessWordField;
	JTextField addWordField;

	//Buttons
	JButton bStart;
	JButton bAdd;
	JButton bAnswer;
	JButton bExit;
	
	//Panels
	JPanel mainframePanel = new JPanel();
	JPanel greetingMessagePanel;
	JPanel textfieldPanel;
	JPanel buttonPanel1;
	JPanel buttonPanel2;
	JPanel buttonPanel3;
	JPanel addWordPanel;
	JPanel resultPanel;
	JPanel errorPanel;
	
	

	
//Creating the constructor of class GuessTheGame
	public GuessTheWord() {
		
		// Making instance of ActionListener class
		 ActionListener forbutton = new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					//When user start button is clicked
					if(e.getSource() == bStart) {
						//start the game
						try {
							bStart.setEnabled(false); //Start button will be disabled when clicked for the first time 
							greetingJLabel.setText("Game Started!! All the Best");
							greetingJLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
							greetingJLabel.setForeground(Color.GREEN);
							
							resultString.delete(0, resultString.length());//delete the resultString if user click start button to play game second time
							resultIndexList.clear();//clear the list of previous word index
							errorLabel.setText("");
							
							File readfile = new File("E:\\PROJECTS\\JAVA PROJECT\\GUESS THE WORD\\HangmanGame_Assignment_1\\Hangman_Assignment_1\\src\\\\com\\humber\\java\\Hangman.txt"); // read the file
							Scanner getWord = new Scanner(readfile);// Scan the whole file from the file instance called readfile
							
							//It will add every word form the file and save it to arraylist named as stringArrayList
							while(getWord.hasNext()) {
								stringArrayList.add(getWord.next());
							}
							
							//From that stringArrayList it will select on word and assign that word to the global string variable called randomword
						    randomword = (String) stringArrayList.get((int)(Math.random() * stringArrayList.size()));
							System.out.println("Word is: "+randomword);

							
							
						    
						    //Now selected random word is split into character and assign that characters to an array called randomWordArray from index 0 till the last character of an word
							randomWordArray =  randomword.split("");
							
							// Here using for loop from 0th index till length of randomWordArray, we are append or adding '*' symbol on instance of StringBuilder class named resultString
							for(int i =0; i<randomWordArray.length; i++) {
								resultString.append("*");
								
							}
							//Displaying instance of StringBuilder class on the frame
							resultLabel.setText(resultString.toString());
							
						} catch (Exception e2) { //if try block is not executed this catch block gives an exception message 
							// TODO: handle exception
							e2.printStackTrace();
						}
						
					}else if(e.getSource() == bAnswer) {
						//checking the answer
						//Fetching character that user enter into a local string variable
						String textFieldData = guessWordField.getText().toLowerCase();
						if(bStart.isEnabled()) {
							greetingJLabel.setText("Please click the start button first");
							greetingJLabel.setForeground(Color.RED);
						}
						else if(textFieldData.length() <1) {
							greetingJLabel.setText("Please enter the character");
							greetingJLabel.setForeground(Color.RED);
								
						}
						else {
						
						guessWordField.setText(""); //this will clear the field every user check for the character
						
						
						// Looping from 0th index till length of randomWordArray, this loop compare a character from the text-field of the GUI with the array of character
						//and if that character exist in the array it will return the index of the character in the list called resultIndexList
						for(int i =0; i<randomWordArray.length; i++) {
							if(textFieldData.compareTo(randomWordArray[i])==0) {
								resultIndexList.add(i);
							}
						}
						
						//feedback for user guess i.e right guess and wrong guess and increase error counter if error found 
						if(randomword.toString().contains(textFieldData)) {
							greetingJLabel.setText("Right Guess");
							greetingJLabel.setForeground(Color.GREEN);
						}
						else {
							greetingJLabel.setText("Wrong Guess");
							greetingJLabel.setForeground(Color.RED);
							errorCount++;
//							if(errorCount == 3) {
//								greetingJLabel.setText("You Lost the game");
//								bAnswer.setEnabled(false);
//								errorCount = 0;
//							}
						}
							
						// 	Looping from 0th index till length of randomWordArray, it will replace '*' with the character from the text-field that user enter
						//if and only if that character is present in the random string array 
						for(int i =0; i<randomWordArray.length; i++) {
						
							if(resultIndexList.contains(i)) {
								resultString.setCharAt(i,randomWordArray[i].toCharArray()[0]);
							}
						}
						//Every time user guesses the right character result label will change from * to that character 
						resultLabel.setText(resultString.toString());
						
						//this condition will executed if there is no more '*' character in the resultString and display the winning message
						if(!resultString.toString().contains("*")) {
							greetingJLabel.setText("Congrats!!! You Won. Do you want to play again? Press Start Button...");
							greetingJLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
							
							errorLabel.setText("Total Wrong Guess is: "+errorCount+" ");
							errorLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
							enterLabel.setForeground(Color.RED);
							errorCount = 0;
							
							bStart.setEnabled(true);
							resultString.delete(0,resultString.length()); // As soon as game ends it will delete the word from the instance of StringBuilder, so that if user want to play again it will not append that string 
							resultLabel.setText(""); // Clear the label in the GUI
							}
						}
						
					}else if(e.getSource() == bExit) {
						//exit from the game
						//if user press exit button without pressing start button below if statement is executed
						if(bStart.getModel().isEnabled()) {
							greetingJLabel.setText("Please start the game");
							greetingJLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
							enterLabel.setForeground(Color.ORANGE);
						}
						else {
							greetingJLabel.setText("Game Over!! Do you want to play again? Press Start Button...");
							greetingJLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
							
							errorLabel.setText("Total Wrong Guess is: "+errorCount +" ");
							errorLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
							enterLabel.setForeground(Color.RED);
							errorCount = 0;
							
							
							bStart.setEnabled(true);
							resultString.delete(0,resultString.length());
							resultLabel.setText("");

							
						}
					}else {
						//for add word
						
						//fetch the work that user enter to add and save the word in wordString
						String wordString = addWordField.getText().toLowerCase();
						
						
						try {
							//Checking for the word present in text file or not
							File readfile2 = new File("E:\\PROJECTS\\JAVA PROJECT\\GUESS THE WORD\\HangmanGame_Assignment_1\\Hangman_Assignment_1\\src\\com\\humber\\java\\Hangman.txt"); // read the file
							Scanner getWord = new Scanner(readfile2);
							
							while(getWord.hasNext()) {
								toCheckWordPresence.add(getWord.next());
							}
							getWord.close();
							if(toCheckWordPresence.contains(wordString)) {
								greetingJLabel.setText(wordString.toUpperCase() + " already in the Hangman file");
								greetingJLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
								greetingJLabel.setForeground(Color.green);
							}else {
								FileWriter writer = new FileWriter("E:\\PROJECTS\\JAVA PROJECT\\GUESS THE WORD\\HangmanGame_Assignment_1\\Hangman_Assignment_1\\src\\com\\humber\\\\java\\Hangman.txt",true);
								writer.write(System.lineSeparator());
								writer.write(wordString);
								writer.close();
								greetingJLabel.setText(wordString.toUpperCase() + " is added to the Hangman file successfully");
								greetingJLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
								greetingJLabel.setForeground(Color.green);
							}
							//Code to write in the file
							
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
					}
				}
			};
			
		//textfield
		guessWordField = new JTextField(20);
		
		//
		guessWordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (guessWordField.getText().length() >= 1) {
                    e.consume(); // Consume the key event if there is already a character in the JTextField
                 }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                }

            @Override
            public void keyReleased(KeyEvent e) {
               }
        });
		
		addWordField = new JTextField(20);
		
		
		//Labels 
		greetingJLabel = new JLabel("Welcome to the Game!!");
		greetingJLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		greetingJLabel.setForeground(Color.green);
		
		
		enterLabel = new JLabel("Enter a letter");
		addNewLetterLabel = new JLabel("Add this letter");
		resultLabel=new JLabel("");
		errorLabel = new JLabel("");
		
		//Buttons
		bAdd = new JButton("Add Word");		
		bAnswer = new JButton("Check letter or Word");	
		bStart = new JButton("Start the Game");
		bExit = new JButton("Exit From the Game");
		
		
		//adding instance of action listener to the button
		bStart.addActionListener(forbutton);
		bAnswer.addActionListener(forbutton);
		bExit.addActionListener(forbutton);
		bAdd.addActionListener(forbutton);
		
		//Panels
		greetingMessagePanel = new JPanel();
		textfieldPanel = new JPanel();
		buttonPanel1 = new JPanel();
		buttonPanel2 = new JPanel();
		buttonPanel3 = new JPanel();
		addWordPanel= new JPanel();	
		resultPanel = new JPanel();
		errorPanel = new JPanel();
		
		//frame Panel
		greetingMessagePanel.add(greetingJLabel);
		
		textfieldPanel.add(enterLabel);
		textfieldPanel.add(guessWordField);
		
		buttonPanel1.add(bStart);
		buttonPanel2.add(bAnswer);
		buttonPanel3.add(bExit);
		
		
		addWordPanel.add(addNewLetterLabel);
		addWordPanel.add(addWordField);
		addWordPanel.add(bAdd);
		
		resultPanel.add(resultLabel);
		
		errorPanel.add(errorLabel);
		
		//Main frame
		mainframePanel.add(greetingMessagePanel);
		mainframePanel.add(textfieldPanel);
		mainframePanel.add(buttonPanel1);
		mainframePanel.add(buttonPanel2);
		mainframePanel.add(buttonPanel3);
		mainframePanel.add(addWordPanel);
		mainframePanel.add(resultPanel);
		mainframePanel.add(errorPanel);
		
				
		
		//Basic Frame 
		setTitle("Guess the Word");//Set the title of the frame
		setVisible(true);//Make frame visible
		setSize(450,350);//set height and width of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close the JFrame on exit and override the default operation ie running in the background
		setResizable(false);
		add(mainframePanel);
	
	}
	
	//Main Method from where code start to execute
	public static void main(String[] args){
		// TODO Auto-generated method stub
		new GuessTheWord(); //Making the instance of this class 
		
	}
}
