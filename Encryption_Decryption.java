import java.util.Scanner;

public class Encryption_Decryption
{
    public static void main(String[] args) 
    {
        C_submenu();   
    }

    /**
     * This method clears the terminal
     * 
     * It uses a sequence of escape codes.<br>"\033" marks the start of the ANSI sequence.<br>"[H" moves the cursor to the top-left.<br>"[2J" clears everything after the cursor.<br>
     */
    private static void Clear_Console()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    /**
     * This method contains all the funcitonality of this class. It displays the main menu for the "Encryption - Decryption" part. <br>
     * The user is given 3 options :<br> 
     * [1]Encryption<br>
     * [2]Decryption <br>
     * [3]Return to the Main Menu<br>
     * Then when the user enters a valid input, it would take them to the menu of their selected option.<br><br>
     * 
     * [1]Encryption:<br>
     * Calls the function "Encrypt()"<br><br>
     * 
     * [2]Decryption:<br>
     * Calls the function "Decrypt()"<br><br>
     * 
     * [3]Return to the Main Menu<br>
     * Returns from the function to the application's main menu <br><br>
     * 
     * Unless the user enters option [3], this option will keep running in a while loop which is stopped using a boolean "exit" which is set to true only when the user pick [3]<br> 
     * 
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public  static void C_submenu()
    {
        Scanner inputC  = new Scanner(System.in);
        boolean exit = false;

        while(!exit)
        {
            System.out.println("Please Select One of The Following Options From The Submenu: ");
            System.out.println("[1] Encryption");
            System.out.println("[2] Decryption");
            System.out.println("[3] Return to the Main Menu\n");
            
            switch (inputC.nextInt()) 
            {

                case 1 -> {
                    Clear_Console();
                    System.out.println("You have selected option 1\n");
                    System.out.println("Please Enter The Encryption Key to Use (Must be between 26 And -26),\nPostive Values Shift Characters to The Right, Negative Values Shift Characters to The Left, 0 Will Not Shift Characters: ");     
                    
                    int key = inputC.nextInt();
                    inputC.nextLine(); 
                    
                    if (key > 26 || key < -26){
                        System.out.println("\nINVALID input!!!, The Encryption Key Must Be Be Between 26 And -26\nReturning To the subMenu...\n ");
                        break;
    
                    }
                    
                    System.out.println("\nPlease Enter the Text You Would Like to Encrypt: ");     
                    String text = inputC.nextLine();
    
                    String Encrypted_Text = Encrypt(text, key);
                    System.out.println("\n"+ text + " Was Successfully Encrypted To " + Encrypted_Text);
                }
               
                case 2 -> {
                    Clear_Console();
                    System.out.println("You have selected option 2\n");
                    System.out.println("Please Enter The Decryption Key to Use (Must be between 26 And -26)\nPostive Values Shift Characters The Left, Negative Values Shift Characters to The Right, 0 Will Not Shift Characters: ");
                    int key = inputC.nextInt();
                    inputC.nextLine();
                    if (key > 26 || key < -26){
                        System.out.println("\nINVALID input!!!, The Decryption Key Must Be Be Between 26 And -26\nReturning To the Submenu...\n ");
                        break;
                    }
                    System.out.println("\nPlease Enter the Text You Would Like to Decrypt: ");
                    String text = inputC.nextLine();
                    String Decrypted_Text = Decrypt(text, key);
                    System.out.println("\n"+ text + " Was Successfully Dencrypted To " + Decrypted_Text);
                }
    
                case 3 -> {
                    Clear_Console();  
                    exit=true;
                }
                 
                default -> System.out.println("INVALID input!!!, No Such Option Exists, Please Select A valid option From The Submenu (1, 2, 3)\n");
            }   
        }

    }

    /**
     * 
     * This function encrypts a text entered by the user.<br><br>
     * 
     * The user is asked to select a number between -26 and +26 as the Encryption Key.<br>
     * Then is asked to input a string "text" to be encrypted.<br>
     * Then the string's characters are shifted upwards (positively) according to the key from before.<br><br>
     * 
     * @param text A string that will be encrypted
     * @param key An int value that determines how many letters each character in "text" is going to be shifted
     * @return EncryptedString
     */
    private static String Encrypt(String text, int key){

        String ans = "";      
        for (int i = 0; i < text.length(); i++) {
            
            if(Character.isAlphabetic(text.charAt(i)))
            {
                char toAdd = 'a';
                
                if(text.charAt(i)<'a')
                {
                    toAdd = 'A';
                }
                
                char c = (char)(text.charAt(i)-toAdd);

                char c_Encrypted = (char)(((c+key)+26)%26);

                c_Encrypted = (char) (c_Encrypted%26 + toAdd);
                
                ans += c_Encrypted;
            }
            else if(!Character.isAlphabetic(text.charAt(i)))
            {
                ans += text.charAt(i);
            }
        }

        return ans;
    }

    /**
     * 
     * This function decrypts a text entered by the user.<br><br>
     * 
     * The user is asked to select a number between -26 and +26 as the Encryption Key.<br>
     * Then is asked to input a string "text" to be encrypted.<br>
     * Then the string's characters are shifted downwards (negatively) according to the key from before.<br>
     * 
     * @param text A string that will be encrypted
     * @param key An int value that determines how many letters each character in "text" is going to be shifted
     * @return DecryptedString
     */
    private static String Decrypt(String text, int key){
        
        String ans = "";     
        for (int i = 0; i < text.length(); i++) {
            
            if(Character.isAlphabetic(text.charAt(i)))
            {
                char toSubtract = 'a';
                
                if(text.charAt(i)<'a')
                {
                    toSubtract = 'A';
                }

                char c = (char)(text.charAt(i)-toSubtract);

                char c_Decrypted = (char)(((c-key)+26)%26);

                c_Decrypted = (char) (c_Decrypted + toSubtract);

                ans += c_Decrypted;
            }
            else if(!Character.isAlphabetic(text.charAt(i)))
            {
                ans += text.charAt(i);
            }
        }

        return ans;
    }
}

