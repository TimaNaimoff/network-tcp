package edu.javacourse.network.net;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 5; i++) {
            SimpleClient spm=new SimpleClient(i);
            spm.start();

        }
    }

}
class SimpleClient extends Thread{
    private int cmdNumber;
    private static final String[]COMMAND={
        "HELLO","MORNING","DAY","EVENING"
    };

    public SimpleClient(int cmdNumber){
        this.cmdNumber=cmdNumber;
    }

    @Override
    public void run() {
        System.out.println("Started "+ LocalDateTime.now());
        try(  Socket client = new Socket("127.0.0.1", 25225);
              BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
              BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
        ){

//        System.out.println("Server is started");

        //String str = "Timur";

        //
        String command=COMMAND[cmdNumber%COMMAND.length];
        String str=command+" Tim";
        writer.write(str);
        writer.newLine();
        writer.flush();
        String answer = reader.readLine();
        System.out.println("Client got string " + answer);

    }
        catch(IOException e){
            e.printStackTrace(System.out);
        }
//        System.out.println("Client finished work "+LocalDateTime.now());
    }
}