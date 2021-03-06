/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anthony Vigil
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class PolCompFrame extends javax.swing.JFrame {
                   
    private static javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    private static javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
	public static ArrayList<Question> questions;
	public static ArrayList<Question> unansweredQuestions;
    public static ArrayList<Question> answeredQuestions;
    public static User user;
    
    public PolCompFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().setBackground(Color.WHITE);
        jTextArea1.setMargin(new Insets(5, 10, 30, 5));
        jTextArea1.setBackground(Color.BLACK);
        jTextArea1.setFont(new Font("Verdana", Font.BOLD, 14));
        jTextArea1.setForeground(Color.GRAY);
        jTextArea1.setCaretColor(Color.LIGHT_GRAY);
        
        jTextArea1.setLineWrap(true);
        
        
        jTextArea1.addKeyListener(new java.awt.event.KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateTextArea();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {}
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1600, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
    	runMeth();
    	
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PolCompFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PolCompFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PolCompFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PolCompFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PolCompFrame().setVisible(true);
            }
        });
    }
    
    public static void runMeth() {
    	user = new User();
    	questions = parseQuestions();
    	user.calculateModerateScores(questions);
    	sortQuestionsByTopic();
    	
    	String intro = "Welcome to my political compass!\nThe instructions are simple, assume you are living in the modern United States, and each question is a policy proposal.\nIf the question is something that happened in the past, answer as if it had not happened.\nRespond with a number between 1 and 5, representing how you would respond to such a proposal.\n   5 - Vote for it and publicly advocate for it\n   4 - Vote for it (without becoming vocal)\n   3 - Undecided (take no action)\n   2 - Vote against it (without becoming vocal)\n   1 - Vote against it and publicly advocate against it"; // will have introductory statement
    	jTextArea1.append("*************************\n" + intro + "\n*************************\n");
    	
    	unansweredQuestions = questions;
    	answeredQuestions = new ArrayList();
    	getNextQuestion();
    }
    
    public static void updateTextArea() { // runs every time enter is pressed
    	String[] a = jTextArea1.getText().split("\n");
    	String cur = a[a.length-1];	
    	if((int) cur.charAt(0) >= 49 && (int) cur.charAt(0) <= 53) { // if one character long and equal to a number between one and 5
    		Question temp = unansweredQuestions.get(0);
    		unansweredQuestions.remove(0);
    		temp.wasAnswered((int) cur.charAt(0) - 48);
    		answeredQuestions.add(temp);
    		updateUserData(temp);
    		getNextQuestion();
    	} else if(cur.substring(0, 1).toUpperCase().equals("B")) {
    		if(!answeredQuestions.isEmpty()) {
    			previousQuestion();
    		}
    	}
    	else {
    		jTextArea1.append("Error: Did not recognize answer, please try again (note: answers must be between 1 and 5, press enter to move to next question)\n");
    		String temp = "Question " + (answeredQuestions.size()+1) + " of " + ((unansweredQuestions.size()) + (answeredQuestions.size())) + ": ";
    		jTextArea1.append(temp + unansweredQuestions.get(0).text + "\n");
    	}
    }
    
    public static void updateUserData(Question answered) {
    	user.incrementScore(answered);
    	printUserData();
    }
    
    public static void previousQuestion() {    	
    	unansweredQuestions.add(0, answeredQuestions.get(answeredQuestions.size()-1)); // takes the last question from answered questions and moves it to unanswered
    	answeredQuestions.remove(answeredQuestions.size()-1); // removes the last question from answered questions
    	
    	
    	// resets user score
    	user.decrementScore(unansweredQuestions.get(0));
    	unansweredQuestions.get(0).setAnswered(false);
    	
    	// sets the text area to remove the last two lines
    	String a = jTextArea1.getText();
    	String[] aSplit = a.split("\n");
    	
    	
    	printUserData();
    	getNextQuestion();
    }
    
    public static void printUserData() {
    	System.out.println("*****\nModerate scores\n******");
    	System.out.println("moderate domestic capitalism score : " + user.moderateDCScore);
    	System.out.println("moderate international capitalism score : " + user.moderateICScore);
    	System.out.println("moderate interventionism score : " + user.moderateIntervScore);
    	System.out.println("moderate social score : " + user.moderateSocScore);
    	System.out.println("*****\nUser data\n******");
    	System.out.println("domestic capitalism score : " + user.domesticCapitalismScore);
    	System.out.println("international capitalism score : " + user.internationalCapitalismScore);
    	System.out.println("interventionism score : " + user.interventionismScore);
    	System.out.println("social score : " + user.socialScore);
    	System.out.println("--------------------------------------------------------------------------------------------------------");
    }
    
    
    public static void sortQuestionsByTopic() {
    	Collections.sort(questions, new Comparator<Question>() {
			@Override
			public int compare(Question o1, Question o2) {
				// TODO Auto-generated method stub
				return o1.topic.compareTo(o2.topic);
			}
    	});
    }
    
    public static void getNextQuestion() {
    	if(unansweredQuestions.isEmpty()) {
    		endTest();
    	} else {
    		String temp = "Question " + (answeredQuestions.size()+1) + " of " + ((unansweredQuestions.size()) + (answeredQuestions.size())) + ": ";
    		jTextArea1.append(temp + unansweredQuestions.get(0).text + " (type 'b' to go back to the previous question)\n");
    	}
    }
    
    public static void endTest() {
    	jTextArea1.append("*****\nTest over\n*****\n");
    	user.calculateAdjustedScores();
    	jTextArea1.append("Alignment Scores: \n");
    	DecimalFormat df = new DecimalFormat("#.##");
    	jTextArea1.append("Domestic Capitalism Score: " + df.format(user.adjustedDCScore) + "\n");
    	jTextArea1.append("International Capitalism Score: " + df.format(user.adjustedICScore) + "\n");
    	jTextArea1.append("Interventionism Score: " + df.format(user.adjustedIntervScore) + "\n");
    	jTextArea1.append("Social Progressiveness Score: " + df.format(user.adjustedSocScore) + "\n");
    	interpretScores();
    	printUserData();
    	jTextArea1.append("\n\n**\nTEST HAS ENDED\n**\n\n");
    	runMeth();
    }

    
    public static void interpretScores() { // calculates scores and changes them to strings
    	//domestic capitalism score
    	double a = user.adjustedDCScore;
    	double b = user.adjustedICScore;
    	double c = user.adjustedIntervScore;
    	double d = user.adjustedSocScore;
    	String a1 = "";
    	String b1 = "";
    	String c1 = "";
    	String d1 = "";
    	
    	if(a < -0.75) {
    		a1 = "Very Socialist on Domestic Issues";
    	} else if (a < -0.5) {
    		a1 = "Mostly Socialist on Domestic Issues";
    	} else if (a < -0.25) {
    		a1 = "Somewhat Socialist on Domestic Issues";
    	} else if(a < 0) {
    		a1 = "Slightly Socialist on Domestic Issues";
    	} else if(a == 0) {
    		a1 = "Neutral on Domestic Capitalist Issues";
    	} else if(a > 0) {
    		a1 = "Slightly Capitalist on Domestic Issues";
    	} else if(a > 0.25) {
    		a1 = "Somewhat Capitalist on Domestic Issues";
    	} else if(a > 0.5) {
    		a1 = "Mostly Capitalist on Domestic Issues";
    	} else if(a > 0.75) {
    		a1 = "Very Capitalist on Domestic Issues";
    	}
    	if(b < -0.75) {
    		b1 = "Very Socialist on International Issues";
    	} else if (b < -0.5) {
    		b1 = "Mostly Socialist on International Issues";
    	} else if (b < -0.25) {
    		b1 = "Somewhat Socialist on International Issues";
    	} else if(b < 0) {
    		b1 = "Slightly Socialist on International Issues";
    	} else if(b == 0) {
    		b1 = "Neutral on International Capitalist Issues";
    	} else if(b > 0) {
    		b1 = "Slightly Capitalist on International Issues";
    	} else if(b > 0.25) {
    		b1 = "Somewhat Capitalist on International Issues";
    	} else if(b > 0.5) {
    		b1 = "Mostly Capitalist on International Issues";
    	} else if(b > 0.75) {
    		b1 = "Very Capitalist on International Issues";
    	}
    	if(c < -0.75) {
    		c1 = "Very Isolationist";
    	} else if (c < -0.5) {
    		c1 = "Mostly Isolationist";
    	} else if (c < -0.25) {
    		c1 = "Somewhat Isolationist";
    	} else if(c < 0) {
    		c1 = "Slightly Isolationist";
    	} else if(c == 0) {
    		c1 = "Neutral on Interventionism";
    	} else if(c > 0) {
    		c1 = "Slightly Interventionist";
    	} else if(c > 0.25) {
    		c1 = "Somewhat Interventionist";
    	} else if(c > 0.5) {
    		c1 = "Mostly Interventionist";
    	} else if(c > 0.75) {
    		c1 = "Very Interventionist";
    	}
    	if(d < -0.75) {
    		d1 = "Very Socially Progressive";
    	} else if (d < -0.5) {
    		d1 = "Mostly Socially Progressive";
    	} else if (d < -0.25) {
    		d1 = "Somewhat Socially Progressive";
    	} else if(d < 0) {
    		d1 = "Slightly Socially Progressive";
    	} else if(d == 0) {
    		d1 = "Neutral on Social Issues";
    	} else if(d > 0) {
    		d1 = "Slightly Socially Conservative";
    	} else if(d > 0.25) {
    		d1 = "Somewhat Socially Conservative";
    	} else if(d > 0.5) {
    		d1 = "Mostly Socially Conservative";
    	} else if(d > 0.75) {
    		d1 = "Very Socially Conservative";
    	}    	
    	jTextArea1.append("Alignments: \n" + a1 + "\n" + b1 + "\n" + c1 + "\n" + d1 + "\n");
    }
    
    public static ArrayList<Question> parseQuestions() { // handles question fetching
    	Scanner scn = null;
    	ArrayList<Question> qs = new ArrayList();
    	File file = new File("QuestionList.txt");
    	try {
    		scn = new Scanner(file);
    		jTextArea1.append("Initialized question list document\n");
    	} catch (Exception e) {
    		jTextArea1.append("Error: could not find question list document\n");
    	}
    	
    	int count = 0;
    	while(scn.hasNextLine()) {
    		String str = scn.nextLine();
    		if(!str.substring(0,2).equals("//")) { // skips commented lines in file
    			count++;
    			String[] a = str.split("\t"); // splits by tab and assigns to a new question object
    				try {
    					qs.add(new Question(a[0], a[1], a[2], Integer.valueOf(a[3])));
    				} catch (Exception e) {
    					jTextArea1.append("Error loading question " + count + " from file. Line in file: " + str + "\n");
    				}
    		}
    	}
    	jTextArea1.append("Successfully loaded " + qs.size() + " of " + count + " questions from file\n");
		return qs;
    }
    

                 
}
