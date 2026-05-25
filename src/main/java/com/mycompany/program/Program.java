/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.program;

import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Student
 */
public class Program {

        public static void main(String[] args) { 
        Scanner input = new Scanner(System.in); 
        
        
        System.out.print("Create new account");
        
        System.out.print("\n");
        
        System.out.print("\nEnter First name: ");
        String firstName = input.nextLine();
        
        System.out.print("Enter Last name: "); 
        String lastName = input.nextLine();
        
        System.out.print("Enter Username: "); 
        String username = input.nextLine();
        
        System.out.print("Enter Password: "); 
        String password = input.nextLine();
        
        System.out.print("Enter a valid South African phone number: "); 
        String phoneNumber = input.nextLine();   
 
        User user = new User(firstName, lastName, username, password, phoneNumber); 
        
        System.out.println("\nAccount status:");
        
        String registrationMessage = Login.registerUser(username, password);
        System.out.println(registrationMessage);
        
        boolean validUsername = Login.checkUserName(user.username); 
        boolean validPassword = Login.checkPasswordComplexity(user.password); 
        boolean validPhone = Login.checkCellPhoneNumber(user.phoneNumber);
        
        System.out.println("------------------------------");
        
        if (!validUsername || !validPassword || !validPhone) {
            System.out.println("Registration was not successful.");
            return;
        } else {
            System.out.println("Registration was successful.");
        }
        
        System.out.println("\nLogin to Account ");
        
        System.out.print("\nEnter Username: "); 
        String loginUsername = input.nextLine(); 
        
        System.out.print("Enter Password: "); 
        String loginPassword = input.nextLine(); 
        
        String accountMessage = Login.returnLoginStatus(loginUsername, loginPassword, user, user.firstName, user.lastName);
        System.out.println("------------------------------");
        System.out.println(accountMessage);
        
        //POE Part 2
        
        if (Login.loginUser(loginUsername, loginPassword, user)) {
            String validChatRunner = Message.createChat(input);
            System.out.println(validChatRunner);
            
        } else {
            System.out.println("Login failed");
            
        }
        
        
        input.close();   
     
    }   
}  

class User { String firstName; String lastName; String username; String password; String phoneNumber;
    public User(String firstName, String lastName, String username, String password, String phoneNumber) { 
        
        this.firstName = firstName; 
        this.lastName = lastName; 
        this.username = username; 
        this.password = password; 
        this.phoneNumber = phoneNumber; 
 
    }   
}  

class Login { 
    public static boolean checkUserName(String username) { 
        if (!username.contains("_")) { 
            return false; 
        } else if (username.length() > 5) { 
            return false; 
        } else { 
            return true; 
    } 
}   
 
public static boolean checkPasswordComplexity(String password) { 
    if (!password.matches(".*[A-Z].*")) { 
        return false; 
    } else if (password.length() < 8) {   
        return false; 
    } else if (!password.matches(".*\\d.*")) {   
        return false; 
    } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {   
        return false; 
    } else {   
        return true; 
    }   
}   
 
public static boolean checkCellPhoneNumber(String phoneNumber) {   
    if (!phoneNumber.matches("^\\+27\\d{9}$")) {   
        System.out.println("Cell phone number incorrectly formatted or does not contain international code"); 
        return false; 
    } else {   
        System.out.println("Cell phone number successfully captured");
        return true; 
    }   
}  
 
public static String registerUser(String username, String password) { 
    if (!checkUserName(username)) {
        return "Username is not correctly formatted; please ensure your username contains an underscore and is no more than five characters in length.";
    } else if (!checkPasswordComplexity(password)) {
        return "Password is not correctly formatted; please ensure your password contains at least eight characters, a capital letter, a number, and a special character.";
    } else {
        return "Username and password successfully captured.";
    }
} 

public static boolean loginUser(String loginUsername, String loginPassword, User user) {
    if (loginUsername.equals(user.username) && loginPassword.equals(user.password)) { 
        return true; 
    } else {  
        return false;  
    }  
} 
 
public static String returnLoginStatus(String loginUsername, String loginPassword, User user, String firstName, String lastName) { 
    if (loginUser(loginUsername, loginPassword, user)) { 
        return "Welcome" + " " + user.firstName + " " + user.lastName + ", it is great to see you."; 
    } else {  
        return "Username or password incorrect, please try again.";
    } 
}

}

class Message {
    
    //method that creates new chat
    
    public static String createChat(Scanner input) {
        
        int numberOfMessages;
        String message = "";
        String recipientCell;
        String result = "";
        
        //User must select an option to start chatting
        System.out.println("Welcome to QuickChat");
        System.out.println("\nSelect one of the following options: ");
        System.out.println("1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        
        System.out.print("\nEnter option: ");
        int option = input.nextInt();
        
        input.nextLine();
        
        int numberMessagesSent = 0;
    
        if (option == 1) {
            System.out.print("Enter the amount of messages you would like to send: ");
            numberOfMessages = input.nextInt();
            input.nextLine();
            
            System.out.print("Enter recipient cell number: ");
            recipientCell = input.nextLine();
                
            String validRecipientCell = checkRecipientCell(recipientCell);
            
            if (validRecipientCell.equals("Recipient cell number incorrectly formatted or does not contain international code")) {
                
                System.out.println(validRecipientCell);
                
                return "\nPlease run program again";
                
            } else {
                System.out.println(validRecipientCell);
            }
            
            for (int i = 1; i <= numberOfMessages; i++) {
                
                long messageID = generateMessageID();
                
                System.out.print("\nEnter message: ");
                message = input.nextLine();
                
                if (message.isEmpty()) {
                    System.out.println("Message cannot be empty");
                    i--;
                    continue;
                }
                
                //Length of message
                if (message.length() > 250) {
                    System.out.println("Please enter a message of less than 250 characters in length");
                    i--;
                    continue;
                }
                
                // Message hash
                String newMessageHash = createMessageHash(messageID, message);
                
                //Storage
                String sentOption = sentMessage(input);
                System.out.println(sentOption);
                
                //Display message and message contents
                if (sentOption.equals("\nMessage successfully sent")) {
                    numberMessagesSent++;
                    storeMessage(messageID, newMessageHash, recipientCell, message);
                    System.out.println(printMessages(messageID, newMessageHash, recipientCell, message));
                    
                    result = "Message created";
                }
                
                if (sentOption.equals("Message deleted")) {
                    
                    result = "Message deleted"; 
                }
                
            }
            
            System.out.println("\nTotal messages sent: " + returnTotalMessages(numberMessagesSent));
            
        } else if (option == 2) {
            result = "\nFeature is coming soon";
            
        } else if (option == 3) {
            result = "\nProgram has ended"; 
            
        } else {
            result = "\nInvalid option, please try again";
        }
        
        return result;
        
    }
    
    //method that generates unique MessageID
    
    public static long generateMessageID() {
        
        Random random = new Random();
        
        long id = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        return id;
    }
    
    //method that checks recipient cell number
    
    public static String checkRecipientCell(String recipientCell) {
        
        if (!recipientCell.matches("^\\+27\\d{9}$")) {
            return "Recipient cell number incorrectly formatted or does not contain international code";
        } else {
            return "Recipient cell number successfully captured";
        }
    }
    
    //method that checks message Hash
    
    public static String createMessageHash(long uniqueMessageID,  String message) {
        
        String idString = String.valueOf(uniqueMessageID);
        String twoNumbers = idString.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        
        if (words.length == 0) {
            return "Invalid";
        }
        
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        String hash = twoNumbers + ":" + firstWord + lastWord;
        
        return hash.toUpperCase();
    }
    
    //method that sends message
    
    public static String sentMessage(Scanner input) {
        
        String store;
        
        //User must select an option
        System.out.println("\nSelect one of the following options: ");
        System.out.println("1) Send Message");
        System.out.println("2) Discard message");
        System.out.println("3) Store message to send later");
        
        System.out.print("\nEnter option: ");
        int createdMessage = input.nextInt();
        input.nextLine();
        
        if (createdMessage == 1) {
            store = "\nMessage successfully sent";
            
        } else if (createdMessage == 2) {
           System.out.print("\nEnter 0 to delete message: ");
           
           int deleteMessage = input.nextInt();
           input.nextLine();
           
           if (deleteMessage == 0) {
               store = "\nMessage deleted";
           } else {
               store = "\nInvalid value";
           }
            
        } else if (createdMessage == 3) {
            store = "\nMessage successfully stored"; 
            
        } else {
            store = "\nInvalid option, please try again";
        }
        
        return store;
    }
    
    //method that prints message details
    
    public static String printMessages(long messageID, String newMessageHash, String recipientCell, String message) {
        
        return "\nMessage ID: " + messageID 
                + "\nMessage Hash: " + newMessageHash 
                + "\nRecipient cellphone: " + recipientCell
                + "\nMessage: " + message;
        
    }
    
    public static int returnTotalMessages(int numberMessagesSent) {
        
        return numberMessagesSent;
    }
    
    public static void storeMessage(long messageID, String messageHash, String recipient, String message) {
        
        try {
            FileWriter writer = new FileWriter("storedMessages.json",true);
            
            writer.write("{\n" +
                "\"MessageID\":\"" + messageID + "\",\n" +
                "\"MessageHash\":\"" + messageHash + "\",\n" +
                "\"Recipient\":\"" + recipient + "\",\n" +
                "\"Message\":\"" + message + "\"\n" +
                "}\n\n"
                );
                writer.close();
                
                System.out.println("Message stored successfully.");
                
        } catch (IOException e) {
            System.out.println( "Error storing message.");
            e.printStackTrace();
            
        }
        
    }
}
    
