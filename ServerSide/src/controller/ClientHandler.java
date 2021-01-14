package controller;

import javafx.scene.control.TextArea;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private ArrayList<ClientHandler> clients;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    InputStream inputStream;


    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
//            this.ois = new ObjectInputStream(socket.getInputStream());
//            this.oos = new ObjectOutputStream(socket.getOutputStream());
//
//            this.inputStream = socket.getInputStream();
//
//            System.out.println("Reading: " + System.currentTimeMillis());
//
//            byte[] sizeAr = new byte[4];
//            inputStream.read(sizeAr);
//            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//
//            byte[] imageAr = new byte[size];
//            inputStream.read(imageAr);
//
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//
//            System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
//            ImageIO.write(image, "jpg", new File(""));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                    /*if (msg.equalsIgnoreCase("exit")) {
                        break;
                    }*/
                for (ClientHandler c1 : clients) {
                    c1.writer.println(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
                socket.close();
//                ois.close();
//                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*    public String readInput() {
        StringBuffer buffer = new StringBuffer();
        try {
            int ch;
            while ((ch = reader.read()) > -1) {
                buffer.append((char) ch);
            }
//            reader.close();
            System.out.println(buffer.toString());
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }*/
}
