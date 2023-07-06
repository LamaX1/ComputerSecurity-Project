import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
 

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class App {
    public static int Choice ;
    public static int Choice2;
    public static String key = "aaaaaaaaaaaaaaaaaaaaaaaa";

    public static void main(String[] args) throws Exception {

        //the main for taking the input from user
        Scanner input = new Scanner(System.in);

        System.out.println("Main Menu \n 1-Encrypt \n 2-Decrypt \n 3-Exit");
        System.out.println("Enter your choice: ");
        Choice = input.nextInt();

        //Switch states depend on the user's selection from the menu
        switch(Choice){

            case 1 :  System.out.println("1-File 2-Folder \n Enter your choice: ");
                      Choice2 = input.nextInt();

                    if(Choice2 == 1){
                    System.out.println("Enter your file name:");
                    String FileName = input.next();
                    System.out.print("Choose the algorithm (AES, DES): ");
                    String alog = input.next();
                    
                    if(alog.equals("AES")){
                       AES_Encrypt_Decrypted(key,FileName);

                    }else if(alog.equals("DES")){
                        DES_Encrypt_Decrypted(key,FileName);

                    }else System.out.print("wrong input!!");

                } else if(Choice2 ==2){
                    System.out.println("Enter your folder path: ");
                    String FolderName = input.next();
                    System.out.print("Choose the algorithm (AES, DES): ");
                    String alg = input.next();

                    File Folder = new File(FolderName);
                    for (File file : Folder.listFiles()) {
                        if (file.getName().endsWith("txt")) {
                            if(alg.equals("AES")){
                                AES_Encrypt_Decrypted(key, file.getAbsolutePath());
                            } else if(alg.equals("DES")){
                                DES_Encrypt_Decrypted(key, file.getAbsolutePath()); }
                        }
                    }} else System.out.println("Worng input!"); break;

            case 2 : System.out.println("1-File 2-Folder \n Enter your choice: ");
                     Choice2 = input.nextInt();

                     if(Choice2 == 1){
                    System.out.println("Enter your file name: ");
                    String FileName = input.next();
                    System.out.println("Choose the algorithm (AES, DES): ");
                    String alg = input.next();
                    
                    if(alg.equals("AES")){
                        AES_Encrypt_Decrypted(key,FileName);

                    }else if(alg.equals("DES")){
                        DES_Encrypt_Decrypted(key,FileName);

                    }else System.out.println("Wrong input !");

                    }else if(Choice2 == 2){
                    System.out.println("Enter your folder name: ");
                    String FolderName = input.next();
                    System.out.println("Choose the algorithm (AES, DES): ");
                    String alg = input.next();
                    
                    File Folder = new File(FolderName);
                    for (File file : Folder.listFiles()) {

                        if (file.getName().endsWith("encrypted")) {
                            if(alg.equals("AES")){
                                AES_Encrypt_Decrypted(key, file.getAbsolutePath());
                            }
                            else if(alg.equals("DES")){
                                DES_Encrypt_Decrypted(key, file.getAbsolutePath());
                            }else System.out.print("wrong input!!");}}
                                   } break;

            case 3 : break;


    }}

//---------------AES ---------------

public static void AES_Encrypt_Decrypted(String key, String input){

        try{
            //this choise for encrypt file of folder
            if(Choice == 1){
                File inputFile = new File(input); //taking the file input
                File outputFile = new File(input.replace(".txt", ".encrypted"));

                Key keyAES = new SecretKeySpec(key.getBytes(), 0, 24, "AES"); //we using 192 and this is key size 

                Cipher cipher = Cipher.getInstance("AES");   //this opreation help to passes the name of the requested transformation 
                                                            //which is contain the alogrithm name AES

                cipher.init(Cipher.ENCRYPT_MODE, keyAES);   //This opreation initializes the object to encryption mode

                FileInputStream inputStream = new FileInputStream(inputFile);//reading the file input 
                                                                             //and take byte to encrypt the file content
                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);
                byte[] outputBytes = cipher.doFinal(inputBytes);
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                outputStream.write(outputBytes);
                outputStream.flush();

                inputStream.close();
                outputStream.close();

                System.out.println("Done! File " + inputFile.getName() + " is encrypted using AES-192");
                System.out.println("Output file is " + outputFile.getName());


             //this choise for decrypt file or folder
            } else if(Choice == 2){

                File inputFile = new File(input);
                File outputFile = new File(inputFile.getAbsolutePath().replace(".encrypted", ".decrypted"));

                Key keyAES = new SecretKeySpec(key.getBytes(), 0, 24, "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, keyAES);

                FileInputStream inputStream = new FileInputStream(inputFile);
                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);
                byte[] outputBytes = cipher.doFinal(inputBytes);
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                outputStream.write(outputBytes);
                outputStream.flush();

                inputStream.close();
                outputStream.close();

                System.out.println("Done! File " + inputFile.getName() + " is decrypted using AES-192");
                System.out.println("Output file is " + outputFile.getName());

            }
         
         
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }
//---------------DES ---------------

    public static void DES_Encrypt_Decrypted(String key, String input){
        try{

            //this choise for Encrypt file or folder 
            if(Choice == 1){
                File inputFile = new File(input);
                File outputFile = new File(input.replace(".txt", ".encrypted"));

                DESKeySpec keyDES = new DESKeySpec(key.getBytes()); //this for create the key usingthe first 8 bytes

                SecretKeyFactory keyFac = SecretKeyFactory.getInstance("DES"); //using the first 8 byte to create an DES object       
                SecretKey desKey = keyFac.generateSecret(keyDES);

                Cipher cipher = Cipher.getInstance("DES");//this opreation help to passes the name of the requested
                                                           //transformation which is contain the alogrithm name DES
                cipher.init(Cipher.ENCRYPT_MODE, desKey);

                FileInputStream inputStream = new FileInputStream(inputFile);
                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);
                byte[] outputBytes = cipher.doFinal(inputBytes);
                FileOutputStream stream = new FileOutputStream(outputFile);
                stream.write(outputBytes);
                stream.flush();

                inputStream.close();
                stream.close();

                System.out.println("Done! File " + inputFile.getName() + " is Encrypted using DES");
                System.out.println("Output file is " + outputFile.getName());


            } else if (Choice == 2){

                File inputFile = new File(input);
                File outputFile = new File(inputFile.getAbsolutePath().replace(".encrypted", ".decrypted"));

                DESKeySpec keyDES = new DESKeySpec(key.getBytes());
                SecretKeyFactory keyFac = SecretKeyFactory.getInstance("DES");
                SecretKey desKey = keyFac.generateSecret(keyDES);
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(Cipher.DECRYPT_MODE, desKey);

                FileInputStream inputStream = new FileInputStream(inputFile);
                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);

                byte[] outputBytes = cipher.doFinal(inputBytes);

                FileOutputStream stream = new FileOutputStream(outputFile);
                stream.write(outputBytes);
                stream.flush();
                inputStream.close();
                stream.close();

                System.out.println("Done! File " + inputFile.getName() + " is Decrypted using DES");
                System.out.println("Output file is " + outputFile.getName());

            }

        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

    }
}