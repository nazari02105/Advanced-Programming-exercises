import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class P2PThread extends Thread{
    String username = null;
    Integer senderPort = null;
    String senderHost = null;
    Integer port = null;
    String host = null;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());


            String input = dataInputStream.readUTF();

            User user = User.getUserByPort(port);

            if (user.getMessagesReceived().containsKey(username)){
                user.getUsernamePortArray().put(username, senderPort);
                user.getUsernameHostArray().put(username, senderHost);

                user.getMessagesReceived().get(username).add(input);
                user.getAllMessages().add(username + "&&" + input);
            }
            else{
                user.getUsernamePortArray().put(username, senderPort);
                user.getUsernameHostArray().put(username, senderHost);

                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(input);
                user.getMessagesReceived().put(username, arrayList);
                user.getAllMessages().add(username + "&&" + input);
            }



            dataOutputStream.writeUTF("success");
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
