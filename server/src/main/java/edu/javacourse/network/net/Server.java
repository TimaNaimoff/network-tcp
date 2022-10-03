package edu.javacourse.network.net;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket socket = new ServerSocket(25225, 2000);
        Map<String, Greatable> handlers = loadHandlers();
        System.out.println("Server is started");
        Thread.sleep(2000);
        while (true) {
            Socket client = socket.accept();
            new ServerThread(client, handlers).start();

        }

    }

    private static Map<String, Greatable> loadHandlers() {
        Map<String, Greatable> result = new HashMap<>();
        try (InputStream is = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            for (Object command : properties.keySet()) {
                String className = properties.getProperty(command.toString());
                Class<Greatable> cl = (Class<Greatable>) Class.forName(className);
                Greatable handler = cl.getConstructor().newInstance();
                result.put(command.toString(), handler);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }


    static class ServerThread extends Thread {
        private Socket client;
        private Map<String, Greatable> handlers;

        public ServerThread(Socket client, Map<String, Greatable> handlers) {
            this.client = client;
            this.handlers = handlers;
        }

        public void run() {
            handleRequest();
        }

        public void handleRequest() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                String request = reader.readLine();
                String[] words = request.split("\\s+");
                String userName = words[1];
                String command = words[0];
                System.out.println("Server got string 1: " + command);
                System.out.println("Server got string 2: " + userName);
                //Thread.sleep(2000);
                String response = buildReasponse(command, userName);
                writer.write(response);
                writer.newLine();
                writer.flush();
                reader.close();
                writer.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }

        public String buildReasponse(String command, String userName) {
            Greatable handler = handlers.get(command);
            if(handler!=null){
                return handler.buildResponse(userName);
            }
            return "Go a way, "+userName;
        }
    }
}
