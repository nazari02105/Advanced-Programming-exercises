import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //------------------------------------------------------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        startTheProgram(scanner);
    }
    //------------------------------------------------------------------
    public static void startTheProgram (Scanner scanner){
        while (true){
            String command = scanner.nextLine();
            if (doesCommandMatch(command, "^userconfig --create --username ([^ ]+)? --password ([^ ]+)?$")
            || doesCommandMatch(command, "^userconfig --create --password ([^ ]+)? --username ([^ ]+)?$")
            || doesCommandMatch(command, "^userconfig --username ([^ ]+)? --create --password ([^ ]+)?$")
            || doesCommandMatch(command, "^userconfig --username ([^ ]+)? --password ([^ ]+)? --create$")
            || doesCommandMatch(command, "userconfig --password ([^ ]+)? --create --username ([^ ]+)?")
            || doesCommandMatch(command, "^userconfig --password ([^ ]+)? --username ([^ ]+)? --create$")){
                createUser(command);
            }
            else if (doesCommandMatch(command, "^userconfig --login --username ([^ ]+)? --password ([^ ]+)?$")
                    || doesCommandMatch(command, "^userconfig --login --password ([^ ]+)? --username ([^ ]+)?$")
                    || doesCommandMatch(command, "^userconfig --username ([^ ]+)? --login --password ([^ ]+)?$")
                    || doesCommandMatch(command, "^userconfig --username ([^ ]+)? --password ([^ ]+)? --login$")
                    || doesCommandMatch(command, "^userconfig --password ([^ ]+)? --login --username ([^ ]+)?$")
                    || doesCommandMatch(command, "^userconfig --password ([^ ]+)? --username ([^ ]+)? --login$")){
                loginUser(scanner, command);
            }
            else if (doesCommandMatch(command, "^userconfig --logout$"))
                System.out.println("you must login before logging out");
            else if (doesCommandMatch(command, "^userconfig --logout$")
            || doesCommandMatch(command, "^portconfig --listen --port ([^ ]+?)$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+?) --listen$")
            || doesCommandMatch(command, "^portconfig --listen --port ([^ ]+?) --rebind$")
            || doesCommandMatch(command, "^portconfig --listen --rebind --port ([^ ]+?)$")
            || doesCommandMatch(command, "^portconfig --rebind --listen --port ([^ ]+?)$")
            || doesCommandMatch(command, "^portconfig --rebind --port ([^ ]+?) --listen$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+?) --rebind --listen$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+?) --listen --rebind$")
            || doesCommandMatch(command, "^portconfig --close --port ([^ ]+?)$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+?) --close$")
            || doesCommandMatch(command, "^contactconfig --link --username ([^ ]+?) --host ([^ ]+?) --port ([^ ]+?)$")
            || doesCommandMatch(command, "^send --message ([^ ]+?) --port ([^ ]+?) --host ([^ ]+?)$")
            || doesCommandMatch(command, "^send --message ([^ ]+?) --username ([^ ]+?)$")
            || doesCommandMatch(command, "^focus --start --host ([^ ]+?)$")
            || doesCommandMatch(command, "^send --port ([^ ]+?) --message ([^ ]+?)$")
            || doesCommandMatch(command, "^focus --port ([^ ]+?)$")
            || doesCommandMatch(command, "^focus --start --host ([^ ]+?) --port ([^ ]+?)$")
            || doesCommandMatch(command, "^send --message ([^ ]+?)$")
            || doesCommandMatch(command, "^focus --start --username ([^ ]+?)$")
            || doesCommandMatch(command, "^focus --stop$")
            || doesCommandMatch(command, "^show$")
            || doesCommandMatch(command, "^show --contacts$")
            || doesCommandMatch(command, "^show --contact ([^ ]+?)$")
            || doesCommandMatch(command, "^show --senders$")
            || doesCommandMatch(command, "^show --messages$")
            || doesCommandMatch(command, "^show --count --senders$")
            || doesCommandMatch(command, "^show --count --messages$")
            || doesCommandMatch(command, "^show --messages --from ([^ ]+?)$")
            || doesCommandMatch(command, "^show --count --messages --from ([^ ]+?)$"))
                System.out.println("you must login to access this feature");
            else if (command.equalsIgnoreCase("exit"))
                System.exit(0);
            else
                System.out.println("invalid command");
        }
    }
    //------------------------------------------------------------------
    public static boolean doesCommandMatch (String command, String pattern){
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(command);
        return matcher.find();
    }
    //------------------------------------------------------------------
    public static Matcher returningMatcher (String command, String pattern){
        Pattern pattern1 = Pattern.compile(pattern);
        return pattern1.matcher(command);
    }
    //------------------------------------------------------------------
    public static void createUser (String command){
        Matcher usernameMatcher = returningMatcher(command, "--username ([^ ]+)");
        Matcher passwordMatcher = returningMatcher(command, "--password ([^ ]+)");

        String username = "";
        String password = "";


        if (usernameMatcher.find() && passwordMatcher.find()){
            username = usernameMatcher.group(1);
            password = passwordMatcher.group(1);
            Pattern pattern = Pattern.compile("^([A-Za-z0-9-_]+)$");


            if (User.allUsers.containsKey(username) || !pattern.matcher(username).find()){
                System.out.println("this username is unavailable");
            }

            else {
                new User(username, password);
                System.out.println("success");
            }
        }
        else{
            System.out.println("this username is unavailable");
        }
    }
    //------------------------------------------------------------------
    public static void loginUser (Scanner scanner, String command){
        Matcher usernameMatcher = returningMatcher(command, "--username ([^ ]+)");
        Matcher passwordMatcher = returningMatcher(command, "--password ([^ ]+)");

        String username = "";
        String password = "";
        if (usernameMatcher.find() && passwordMatcher.find()) {
            username = usernameMatcher.group(1);
            password = passwordMatcher.group(1);
        }


        if (!User.allUsers.containsKey(username))
            System.out.println("user not found");
        else if (!User.allUsers.get(username).getPassword().equals(password))
            System.out.println("incorrect password");
        else{
            System.out.println("success");
            mainMenu(scanner, username);
        }
    }
    //------------------------------------------------------------------
    public static void mainMenu (Scanner scanner, String username){
        while (true){
            String command = scanner.nextLine();
            if (doesCommandMatch(command, "^portconfig --listen --port ([^ ]+)?$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+)? --listen$"))
                settingPort(command, username);
            else if (doesCommandMatch(command, "^portconfig --listen --port ([^ ]+)? --rebind$")
            || doesCommandMatch(command, "^portconfig --listen --rebind --port ([^ ]+)?$")
            || doesCommandMatch(command, "^portconfig --rebind --listen --port ([^ ]+)?$")
            || doesCommandMatch(command, "^portconfig --rebind --port ([^ ]+)? --listen$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+)? --rebind --listen$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+)? --listen --rebind$"))
                bindingPort(command, username);
            else if (doesCommandMatch(command, "^portconfig --close --port ([^ ]+)?$")
            || doesCommandMatch(command, "^portconfig --port ([^ ]+)? --close$"))
                closingPort(command, username);
            else if (doesCommandMatch(command, "^contactconfig --link --username ([^ ]+)? --host ([^ ]+)? --port ([^ ]+)?$"))
                addUserNotAutomatic(command, username);
            else if (doesCommandMatch(command, "^send --message (.+?) --port ([^ ]+)? --host ([^ ]+)?$")
            || doesCommandMatch(command, "^send --message (.+?) --host ([^ ]+)? --port ([^ ]+)?$")
            || doesCommandMatch(command, "^send --host ([^ ]+)? --message (.+?) --port ([^ ]+)?$")
            || doesCommandMatch(command, "^send --host ([^ ]+)? --port ([^ ]+)? --message (.+?)$")
            || doesCommandMatch(command, "^send --port ([^ ]+)? --message (.+?) --host ([^ ]+)?$")
            || doesCommandMatch(command, "^send --port ([^ ]+)? --host ([^ ]+)? --message (.+?)$"))
                sendMessageByHostAndPort(command, username);
            else if (doesCommandMatch(command, "^send --message (.+?) --username ([^ ]+)?$")
            || doesCommandMatch(command, "^send --username ([^ ]+)? --message (.+?)$"))
                sendMessageByUserName(command, username);
            else if (doesCommandMatch(command, "^focus --start --host ([^ ]+)?$")
            || doesCommandMatch(command, "^focus --host ([^ ]+)? --start$"))
                focusOnHost(command, username);
            else if (doesCommandMatch(command, "^send --port ([^ ]+)? --message (.+?)$")
            || doesCommandMatch(command, "^send --message (.+?) --port ([^ ]+)?$"))
                sendMessageWithoutHost(command, username);
            else if (doesCommandMatch(command, "^focus --port ([^ ]+)?$"))
                focusOnPort(command, username);
            else if (doesCommandMatch(command, "^send --message (.+?)$"))
                sendMessageWithoutPortAndHost(command, username);
            else if (doesCommandMatch(command, "^focus --start --host ([^ ]+)? --port ([^ ]+)?$")
            || doesCommandMatch(command, "^focus --start --port ([^ ]+)? --host ([^ ]+)?$")
            || doesCommandMatch(command, "^focus --port ([^ ]+)? --start --host ([^ ]+)?$")
            || doesCommandMatch(command, "^focus --port ([^ ]+)? --host ([^ ]+)? --start$")
            || doesCommandMatch(command, "^focus --host ([^ ]+)? --port ([^ ]+)? --start$")
            || doesCommandMatch(command, "^focus --host ([^ ]+)? --start --port ([^ ]+)?$"))
                focusOnPortAndHost(command, username);
            else if (doesCommandMatch(command,"^focus --start --username ([^ ]+)?$")
            || doesCommandMatch(command,"^focus --username ([^ ]+)? --start$"))
                focusOnUsername(command, username);
            else if (doesCommandMatch(command, "^focus --stop$")){
                User.allUsers.get(username).setFocusedPort(null);
                User.allUsers.get(username).setFocusedHost(null);
                System.out.println("success");
            }
            else if (doesCommandMatch(command, "^show --contacts$"))
                showContacts(username);
            else if (doesCommandMatch(command, "^show --contact ([^ ]+)?$"))
                showSpecificUser(command, username);
            else if (doesCommandMatch(command, "^show --senders$"))
                showSenders(username);
            else if (doesCommandMatch(command, "^show --messages$"))
                showMessages(username);
            else if (doesCommandMatch(command, "^show --count --senders$")
            || doesCommandMatch(command, "^show --senders --count$"))
                showSendersNumber(username);
            else if (doesCommandMatch(command, "^show --count --messages")
            || doesCommandMatch(command, "^show --messages --count"))
                showMessagesNumber(username);
            else if (doesCommandMatch(command, "^show --messages --from ([^ ]+)?$")
            || doesCommandMatch(command, "^show --from ([^ ]+)? --messages$"))
                showMessagesOfSpecificUser(command, username);
            else if (doesCommandMatch(command, "^show --count --messages --from ([^ ]+)?$")
            || doesCommandMatch(command, "^show --count --from ([^ ]+)? --messages$")
            || doesCommandMatch(command, "^show --from ([^ ]+)? --count --messages$")
            || doesCommandMatch(command, "^show --from ([^ ]+)? --messages --count$")
            || doesCommandMatch(command, "^show --messages --from ([^ ]+)? --count$")
            || doesCommandMatch(command, "^show --messages --count --from ([^ ]+)?$"))
                showNumberOfMessagesOfSpecificUser(command, username);

            else if (command.equalsIgnoreCase("userconfig --logout"))
                break;
            else
                System.out.println("invalid command");
        }
    }
    //------------------------------------------------------------------
    public static void settingPort (String command, String username){
        Matcher matcher = returningMatcher(command, "--port ([^ ]+)");
        int port = -1;
        if (matcher.find()){
            try {
                port = Integer.parseInt(matcher.group(1));
            }catch (Exception e){
                System.out.println("invalid port type");
            }
        }

        if (User.allUsers.get(username).getPort() != null)
            System.out.println("the port is already set");

        else if (User.allUsers.get(username).getPort() == null && port != -1){
            User.allUsers.get(username).setPort(port);
            System.out.println("success");
        }
    }
    //------------------------------------------------------------------
    public static void bindingPort (String command, String username){
        Matcher matcher = returningMatcher(command, "--port ([^ ]+)");
        int port = -1;
        if (matcher.find()){
            try {
                port = Integer.parseInt(matcher.group(1));
            }catch (Exception e){
                System.out.println("invalid port type");
            }
        }

        if (port != -1){
            User.allUsers.get(username).setPort(port);
            System.out.println("success");
        }
    }
    //------------------------------------------------------------------
    public static void closingPort (String command, String username){
        Matcher matcher = returningMatcher(command, "--port ([^ ]+)");
        int port = -1;
        if (matcher.find()){
            try {
                port = Integer.parseInt(matcher.group(1));
            }catch (Exception e){
                System.out.println("invalid port type");
            }
        }

        if (port != -1 && User.allUsers.get(username).getPort() == port){
            User.allUsers.get(username).setPort(null);
            System.out.println("success");
        }
        else
            System.out.println("the port you specified was not open");
    }
    //------------------------------------------------------------------
    public static void addUserNotAutomatic (String command, String username){
        Matcher matcher = returningMatcher(command, "--username ([^ ]+)");
        Matcher matcher1 = returningMatcher(command, "--host ([^ ]+)");
        Matcher matcher2 = returningMatcher(command, "--port ([^ ]+)");
        int port = -1;
        String usernameToAdd = "";
        String host = "";
        if (matcher.find() && matcher1.find() && matcher2.find()){
            try {
                port = Integer.parseInt(matcher2.group(1));
                usernameToAdd = matcher.group(1);
                host = matcher1.group(1);
            }catch (Exception e){
                System.out.println("invalid port type");
            }
        }

        if (port != -1){
            User.allUsers.get(username).addToUsernamePortArray(usernameToAdd, port);
            User.allUsers.get(username).addToUsernameHostArray(usernameToAdd, host);
            System.out.println("success");
        }
    }
    //------------------------------------------------------------------
    public static void sendMessageByHostAndPort (String command, String username){
        Matcher matcher2 = returningMatcher(command, "--port ([^ ]+)");
        Matcher matcher1 = returningMatcher(command, "--host ([^ ]+)");
        Matcher matcher = returningMatcher(command, "--message \"(.+)\"");
        String message = "";
        String host = "";
        int port = -1;
        if (matcher.find() && matcher1.find() && matcher2.find()){
            try {
                message = matcher.group(1);
                port = Integer.parseInt(matcher2.group(1));
                host = matcher1.group(1);
            }catch (Exception e){
                System.out.println("invalid port type");
            }
        }


        User user = User.getUserByPort(port);
        if (user == null || !host.equals("127.0.0.1"))
            System.out.println("could not send message");
        else{
            P2PThread p2PThread = new P2PThread();
            p2PThread.senderPort = User.allUsers.get(username).getPort();
            p2PThread.senderHost = "127.0.0.1";
            p2PThread.host = host;
            p2PThread.port = port;
            p2PThread.username = username;
            p2PThread.start();


            try {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Socket socket = new Socket(host, port);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();


                String output = dataInputStream.readUTF();

                System.out.println(output);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //------------------------------------------------------------------
    public static void sendMessageByUserName (String command, String username){
        Matcher matcher1 = returningMatcher(command, "--username ([^ ]+)");
        Matcher matcher = returningMatcher(command, "--message \"(.+)\"");
        String message = "";
        String userToSend = "";
        if (matcher.find() && matcher1.find()){
            message = matcher.group(1);
            userToSend = matcher1.group(1);
        }
        message = message.trim();

        if (!User.allUsers.get(username).getUsernameHostArray().containsKey(userToSend))
            System.out.println("no contact with such username was found");
        else{
            sendMessageByHostAndPort("send --message \"" + message + "\" --port " +
                    User.allUsers.get(username).getUsernamePortArray().get(userToSend) + " --host " +
                    User.allUsers.get(username).getUsernameHostArray().get(userToSend), username);
        }
    }
    //------------------------------------------------------------------
    public static void focusOnHost (String command, String username){
        Matcher matcher = returningMatcher(command, "--host ([^ ]+)");
        String host = "";
        if (matcher.find()) {
            host = matcher.group(1);
            User.allUsers.get(username).setFocusedHost(host);
            System.out.println("success");
        }
    }
    //------------------------------------------------------------------
    public static void sendMessageWithoutHost (String command, String username){
        Matcher matcher1 = returningMatcher(command, "--port ([^ ]+)");
        Matcher matcher = returningMatcher(command, "--message \"(.+)\"");
        String message = "";
        int port = -1;
        if (matcher.find() && matcher1.find()){
            try {
                message = matcher.group(1);
                port = Integer.parseInt(matcher1.group(1));
            }catch (Exception e){
                System.out.println("invalid port number");
            }
        }
        message = message.trim();

        if (User.allUsers.get(username).getFocusedHost() == null || port == -1)
            System.out.println("could not send message");
        else{
            sendMessageByHostAndPort("send --message \"" + message + "\" --port " + port + " --host " +
                    User.allUsers.get(username).getFocusedHost(), username);
        }
    }
    //------------------------------------------------------------------
    public static void focusOnPort (String command, String username){
        Matcher matcher = returningMatcher(command, "--port ([^ ]+)");
        int port = -1;
        if (matcher.find()){
            try {
                port = Integer.parseInt(matcher.group(1));
            }catch (Exception e){
                System.out.println("invalid port number");
            }
        }

        if (User.allUsers.get(username).getFocusedHost() == null && port != -1)
            System.out.println("you must focus on a host before using this command");
        else if (port != -1){
            User.allUsers.get(username).setFocusedPort(port);
            System.out.println("success");
        }
    }
    //------------------------------------------------------------------
    public static void sendMessageWithoutPortAndHost (String command, String username){
        Matcher matcher = returningMatcher(command, "--message \"(.+)\"");
        String message = "";
        if (matcher.find()) {
            message = matcher.group(1);
            message = message.trim();

            if (User.allUsers.get(username).getFocusedHost() == null || User.allUsers.get(username).getFocusedPort() == null)
                System.out.println("could not send message");
            else{
                sendMessageByHostAndPort("send --message \"" + message + "\" --port " + User.allUsers.get(username).getFocusedPort() +
                        " --host " + User.allUsers.get(username).getFocusedHost(), username);
            }
        }
    }
    //------------------------------------------------------------------
    public static void focusOnPortAndHost (String command, String username){
        Matcher matcher = returningMatcher(command, "--port ([^ ]+)");
        Matcher matcher1 = returningMatcher(command, "--host ([^ ]+)");
        int port = -1;
        String host = "";
        if (matcher.find() && matcher1.find()){
            try {
                port = Integer.parseInt(matcher.group(1));
                host = matcher1.group(1);
            }catch (Exception e){
                System.out.println("invalid port number");
            }
        }

        if (port != -1){
            User.allUsers.get(username).setFocusedPort(port);
            User.allUsers.get(username).setFocusedHost(host);
            System.out.println("success");
        }
    }
    //------------------------------------------------------------------
    public static void focusOnUsername (String command, String username){
        Matcher matcher = returningMatcher(command, "--username ([^ ]+)");
        String userToFocus = "";
        if (matcher.find()){
            userToFocus = matcher.group(1);

            if (!User.allUsers.get(username).getUsernameHostArray().containsKey(userToFocus))
                System.out.println("no contact with such username was found");
            else{
                User.allUsers.get(username).setFocusedHost(User.allUsers.get(username).getUsernameHostArray().get(userToFocus));
                User.allUsers.get(username).setFocusedPort(User.allUsers.get(username).getUsernamePortArray().get(userToFocus));
                System.out.println("success");
            }
        }
    }
    //------------------------------------------------------------------
    public static void showContacts (String username){
        if (User.allUsers.get(username).getUsernameHostArray().size() == 0)
            System.out.println("no item is available");
        else{
            ArrayList<String> users = new ArrayList<>();
            ArrayList<String> hosts = new ArrayList<>();
            ArrayList<Integer> ports = new ArrayList<>();
            for (Map.Entry<String, String> entry : User.allUsers.get(username).getUsernameHostArray().entrySet()){
                users.add(entry.getKey());
                hosts.add(entry.getValue());
            }
            for (Map.Entry<String, Integer> entry : User.allUsers.get(username).getUsernamePortArray().entrySet()){
                ports.add(entry.getValue());
            }
            for (int i = 0; i<users.size(); ++i){
                System.out.println(users.get(i) + " -> " + hosts.get(i) + ":" + ports.get(i));
            }
        }
    }
    //------------------------------------------------------------------
    public static void showSpecificUser (String command, String username){
        Matcher matcher = returningMatcher(command, "--contact ([^ ]+)?");
        String userToShow = "";
        if (matcher.find()) {
            userToShow = matcher.group(1);

            if (!User.allUsers.get(username).getUsernamePortArray().containsKey(userToShow))
                System.out.println("no contact with such username was found");
            else{
                System.out.println(User.allUsers.get(username).getUsernameHostArray().get(userToShow) + ":" +
                        User.allUsers.get(username).getUsernamePortArray().get(userToShow));
            }
        }
    }
    //------------------------------------------------------------------
    public static void showSenders (String username){
        if (User.allUsers.get(username).getMessagesReceived().size() == 0)
            System.out.println("no item is available");
        else {
            for (Map.Entry<String, ArrayList<String>> entry : User.allUsers.get(username).getMessagesReceived().entrySet()) {
                System.out.println(entry.getKey());
            }
        }
    }
    //------------------------------------------------------------------
    public static void showMessages (String username){
        if (User.allUsers.get(username).getAllMessages().size() == 0)
            System.out.println("no item is available");
        else {
            for (int i = 0; i < User.allUsers.get(username).getAllMessages().size(); ++i) {
                String[] message = User.allUsers.get(username).getAllMessages().get(i).split("&&");
                System.out.println(message[0] + " -> " + message[1]);
            }
        }
    }
    //------------------------------------------------------------------
    public static void showSendersNumber (String username){
        System.out.println(User.allUsers.get(username).getMessagesReceived().size());
    }
    //------------------------------------------------------------------
    public static void showMessagesNumber (String username){
        System.out.println(User.allUsers.get(username).getAllMessages().size());
    }
    //------------------------------------------------------------------
    public static void showMessagesOfSpecificUser (String command, String username){
        Matcher matcher = returningMatcher(command, "--from ([^ ]+)?");
        String userToShow = "";
        if (matcher.find()) {
            userToShow = matcher.group(1);

            if (User.allUsers.get(username).getMessagesReceived().containsKey(userToShow)) {
                ArrayList<String> messages = User.allUsers.get(username).getMessagesReceived().get(userToShow);
                for (String message : messages) System.out.println(message);
            }
        }
    }
    //------------------------------------------------------------------
    public static void showNumberOfMessagesOfSpecificUser (String command, String username){
        Matcher matcher = returningMatcher(command, "--from ([^ ]+)?");
        String userToShow = "";
        if (matcher.find()) {
            userToShow = matcher.group(1);

            if (User.allUsers.get(username).getMessagesReceived().containsKey(userToShow)) {
                System.out.println(User.allUsers.get(username).getMessagesReceived().get(userToShow).size());
            }
        }
    }
    //------------------------------------------------------------------
}