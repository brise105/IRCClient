/**
*  IRC Bot Logic
*  reads in commands. some commands require !command+space+number to work >"!fib 23"
*
*  @author: Brandon Briseno & Alfonso Castanos
*  Email:  brise105@mail.chapman.edu & casta145@mail.chapman.edu
*  Date:  5/6/2018
*  @version: 6.0
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.StringIndexOutOfBoundsException;
import java.util.Random;

//import org.jibble.pircbot.DccChat;
//import org.jibble.pircbot.DccFileTransfer;
//import org.jibble.pircbot.DccManager;
import org.jibble.pircbot.IrcException;
//import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.OutputThread;
import org.jibble.pircbot.PircBot;
//import org.jibble.pircbot.ReplyConstants;
//import org.jibble.pircbot.User;

public class Bot extends PircBot {
  public Bot() {
    this.setName("Gamabot");
  }
  /**
  *  PircBot
  *  IRC Bot API: http://www.jibble.org/javadocs/pircbot/index.html. 
  *
  *  @author: Paul Mutton
  *  @version: 2.0
  */

  public void onMessage(String channel, String sender,
                        String login, String hostname, String message) {
    String metaMsg = message.toLowerCase();

    try {
      if (metaMsg.substring(0,1).equals("!")) {
        if (metaMsg.substring(0,4).equals("!cat")) {
          Random rand = new Random();
          int lines = 0;
          String line = "";
          try {
            BufferedReader reader = new BufferedReader(new FileReader("cats.txt"));
            while (reader.readLine() != null) {
              lines++;
              //reader.close();
            }
          } catch (IOException ex) {
            System.out.println("ERROR: cat.txt was not found!");
          } 
          int r = rand.nextInt(lines) + 1;
          try (BufferedReader br = new BufferedReader(new FileReader("cats.txt"))) {
            for (int i = 0; i < r; i++) {
              br.readLine();
            }
            line = br.readLine();
          } catch (IOException ex) {
            System.out.println("ERROR: cat.txt was not found!");
          } 
          sendMessage(channel, sender + ": " + line);
        } else if (metaMsg.substring(0,5).equals("!time")) {
          String time = new java.util.Date().toString();
          sendMessage(channel, sender + ": The time is now " + time);
        } else if (metaMsg.substring(0,5).equals("!fib ")) {
          int n1 = 0;
          int n2 = 1;
          int n3;
          int i;
          String sequence = "";
          try {
            int count = Integer.parseInt(metaMsg.substring(5,metaMsg.length()));
            if (count >= 48) {
              String error = "Error: enter a number less than 48.";
              sendMessage(channel,sender + ": " + error);
            } else {
              sendMessage(channel, sender + ": the first " +  count + " Fibonacci numbers are: ");
              for (i = 2; i < count; i++) {
                n3 = n1 + n2;
                sequence = sequence + Integer.toString(n3) + " ";
                n1 = n2;
                n2 = n3;
              }
            }
            sendMessage(channel, sender + ": " + sequence);
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!"); 
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!fib error!");
          }
        } else if (metaMsg.substring(0,5).equals("!rev ")) {
          try {
            String orig = metaMsg.substring(5,metaMsg.length());
            String rev = "";
            int len = orig.length();
            for (int i = len - 1; i >= 0; i--) {
              rev = rev + orig.charAt(i);
            }
            sendMessage(channel, sender + ": " + rev);
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!rev error!");
          }
        } else if (metaMsg.substring(0,5).equals("!cal ")) {
          try {
            String equation = metaMsg.substring(5,metaMsg.length());
            if (equation.contains("+")) {
              String[] parts = equation.split("\\+");
              Float num1 = Float.parseFloat(parts[0].toString());
              Float num2 = Float.parseFloat(parts[1].toString());
              String ans = Float.toString(num1 + num2);
              sendMessage(channel, sender + ": " + ans);
            } else if (equation.contains("-")) {
              String[] parts = equation.split("\\-");
              Float num1 = Float.parseFloat(parts[0].toString());
              Float num2 = Float.parseFloat(parts[1].toString());
              String ans = Float.toString(num1 - num2);
              sendMessage(channel, sender + ": " + ans);
            } else if (equation.contains("/")) {
              String[] parts = equation.split("\\/");
              Float num1 = Float.parseFloat(parts[0].toString());
              Float num2 = Float.parseFloat(parts[1].toString());
              String ans = Float.toString(num1 / num2);
              sendMessage(channel, sender + ": " + ans);
            } else if (equation.contains("%")) {
              String[] parts = equation.split("\\%");
              Float num1 = Float.parseFloat(parts[0].toString());
              Float num2 = Float.parseFloat(parts[1].toString());
              String ans = Float.toString(num1 % num2);
              sendMessage(channel, sender + ": " + ans);
            } else if (equation.contains("*")) {
              String[] parts = equation.split("\\*");
              Float num1 = Float.parseFloat(parts[0].toString());
              Float num2 = Float.parseFloat(parts[1].toString());
              String ans = Float.toString(num1 * num2);
              sendMessage(channel, sender + ": " + ans);
            } else {
              String error = "Error: Enter correct parameters";
              sendMessage(channel, sender + ": " + error);
            }
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NumberFormatException ex) {
            System.out.println("Error: number format!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (ArithmeticException ex) {
            System.out.println("Error: can not divide by 0!");
          } catch (Exception ex) {
            System.out.println("!cal error!");
          }
        } else if (metaMsg.substring(0,5).equals("!bin ")) {
          try {
            String num = metaMsg.substring(5,metaMsg.length());
            int dec = Integer.parseInt(num);
            String bin = "";
            while (dec > 0) {
              bin = dec % 2 + bin;
              dec = dec / 2;
            }
            String ans = "Converstion of decimal to binary is: " + bin;
            sendMessage(channel, sender + ": " + ans);
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NumberFormatException ex) {
            System.out.println("Error: number format!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!bin error!");
          }
        } else if (metaMsg.substring(0,5).equals("!dec ")) {
          try {
            int num = Integer.parseInt(metaMsg.substring(5,metaMsg.length()));
            int dec = 0;
            int x = 0;

            while (num != 0) {
              dec += ((num % 10) * Math.pow(2,x));
              num = num / 10;
              x++;
            }
            String ans = "Conversion of binary to decimal is: " + dec;
            sendMessage(channel, sender + ": " + ans);
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NumberFormatException ex) {
            System.out.println("Error: number format!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!dec error!");
          }
        } else if (metaMsg.substring(0,5).equals("!cel ")) {
          try {
            float temp = Float.parseFloat(metaMsg.substring(6,metaMsg.length()));
            temp = ((temp - 32) * 5) / 9;
            float tempOg = Float.parseFloat(metaMsg.substring(6,metaMsg.length()));
            String cel = tempOg + " degrees Fahrenheit equals " + temp + " degrees Celsius";
            sendMessage(channel, sender + ": " + cel);
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!"); 
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!cel error!");
          }
        } else if (metaMsg.substring(0,5).equals("!help")) {
          sendMessage(channel, sender + ": !time");
          sendMessage(channel, sender + ": !cat");
          sendMessage(channel, sender + ": !rev + [space] + s");
          sendMessage(channel, sender + ": !fib + [space] + n");
          sendMessage(channel, sender + ": !facto + [space] + n");
          sendMessage(channel, sender + ": !cel + [space] + n");
          sendMessage(channel, sender + ": !fahr + [space] + n");
          sendMessage(channel, sender + ": !cal + [space] + n + [operator] + m");
          sendMessage(channel, sender + ": !bin + [space] + n");
          sendMessage(channel, sender + ": !dec + [space] + n");
          sendMessage(channel, sender + ": !palin + [space] + s");
          sendMessage(channel, sender + ": !ctdown + [space] + n");
          sendMessage(channel, sender + ": !help");
          sendMessage(channel, sender + ": !cmdinfo");
        } else if (metaMsg.substring(0,6).equals("!fahr ")) {
          try {
            float temp = Float.parseFloat(metaMsg.substring(6,metaMsg.length()));
            temp = 9 * (temp / 5) + 32;
            float tempOg = Float.parseFloat(metaMsg.substring(6,metaMsg.length()));
            String fahr = tempOg + " degrees Celsius equals " + temp + " degrees Fahrenheit";
            sendMessage(channel, sender + ": " + fahr);
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!fahr error!");
          }
        } else if (metaMsg.substring(0,7).equals("!facto ")) {
          int n = 1;
          int m = 1;
          int facto = 1;
          try {
            n = Integer.parseInt(metaMsg.substring(7,metaMsg.length())); 
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!facto error!");
          }
          if (n < 0) {
            sendMessage(channel, sender + ": the number should not be negative!");
          } else {
            for (m = 1; m <= n; m++) {
              facto = facto * m;
            }
            sendMessage(channel, sender + ": the factorial of " + n + " is " + facto);
          }             
        } else if (metaMsg.substring(0,7).equals("!palin ")) {
          try {
            String orig = metaMsg.substring(7,metaMsg.length());
            int len = orig.length();
            String rev = "";
            String senOut;

            for (int i = len - 1; i >= 0; i--) {
              rev = rev + orig.charAt(i);
            }
            if (orig.equals(rev)) {
              senOut = orig + " is a palindrome.";
              sendMessage(channel, sender + ": " + senOut);
            } else {
              senOut = orig + " is not a palindrome.";
              sendMessage(channel, sender + ": " + senOut); 
            }
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!palin error!");
          } 
        } else if (metaMsg.substring(0,8).equals("!ctdown ")) {
          try {
            int tim = Integer.parseInt(metaMsg.substring(8,metaMsg.length()));
            if (tim <= 60) {
              do {
                sendMessage(channel, sender + ": " + (tim / 1));
                tim = tim - 1;
              } while (tim != 0);
              sendMessage(channel, sender + ": Time's Up!");
            } else {
              sendMessage(channel, sender + ": Error: Countdown Limit 60 seconds");
            } 
          } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Error: index out of bounds!");
          } catch (NullPointerException ex) {
            System.out.println("Error: null pointer exception!");
          } catch (Exception ex) {
            System.out.println("!ctdown error!");
          } 
        } else if (metaMsg.substring(0,8).equals("!cmdinfo")) {
          sendMessage(channel, sender + ": !time- tells time for user"); 
          sendMessage(channel, sender + ": !cat- returns a random image of a cat");
          sendMessage(channel, sender + ": !rev- s: reverses string s");
          sendMessage(channel, sender + ": !fib- n: returns fibonnacci sequence up to n");
          sendMessage(channel, sender + ": !facto- n: factorial of n");
          sendMessage(channel, sender + ": !cel- n: converts n degrees fahrenheit to celsius");
          sendMessage(channel, sender + ": !fahr- n: converts n degrees celsius to fahrenheit");
          sendMessage(channel, sender + ": !cal- performs basic arthmetic upon two values: n & m");
          sendMessage(channel, sender + ": (Single Operations Only!)");
          sendMessage(channel, sender + ": !bin- n: converts decimal number n to binary");
          sendMessage(channel, sender + ": !dec- n: converts binary number n to decimal");
          sendMessage(channel, sender + ": !palin- s: checks if string s is a palindrome");
          sendMessage(channel, sender + ": !ctdown- n: countdown starting from n (LIMIT: 60sec)");
          sendMessage(channel, sender + ": !help- list of the commands with required input");
          sendMessage(channel, sender + ": !cmdinfo- list of the commands with descriptions");   
        } else {
          String errormsg = "Please enter correct command input. Type '!help' for list of commands";
          sendMessage(channel, sender + ": " + errormsg);
        }
      } else {
        System.out.println();
      }
    } catch (StringIndexOutOfBoundsException ex) {
      System.out.println("Error: index out of bounds!");
    } catch (NullPointerException ex) {
      System.out.println("Error: null pointer exception!");
    } catch (NumberFormatException ex) {
      System.out.println("Error: number format!");
    } catch (Exception ex) {
      System.out.println("Error!");
    } 
  }
}
