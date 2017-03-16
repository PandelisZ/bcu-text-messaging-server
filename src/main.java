import msgServer.*;

import java.io.IOException;

class messageServer {
    public static void main(String args[]) throws IOException{

    MessageServer server = new MessageServer(1337);

    server.startService();


    }
}
