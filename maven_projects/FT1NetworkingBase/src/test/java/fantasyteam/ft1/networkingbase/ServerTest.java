package fantasyteam.ft1.networkingbase;

import fantasyteam.ft1.Game;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for base server class
 *
 * @author javu
 */
public class ServerTest {

    private Server server1;
    private Server server2;
    int port = 22222;
    boolean exception;
    /**
     * This int is the parameter used when running the waitTime function in
     * these tests. Change this value to increase or decrease the
     * time waited when waitTime is called.
     */
    int wait = 10;

    /**
     * Logger for logging important actions and exceptions.
     */
    protected static final Logger LOGGER = Logger.getLogger(ServerTest.class.getName());

    /**
     * waitTime tells the test to wait for a specified amount of
     * time, which is useful when dealing with sockets and connections as they
     * need to be given a small amount of time before being able to perform
     * certain tasks on them. Without this short wait period a lot of these
     * tests would fail. This also serves as a benchmark to see how quickly
     * networking tasks can be performed after trying to connect sockets and
     * other networking tasks.
     */
    private void waitTime() {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @BeforeMethod
    private void setupServer() throws IOException {
        exception = false;
        Game game = EasyMock.createMock(Game.class);

//        game.runVoidMethod();
//        EasyMock.expect(game.parseAction()).andReturn("go fuck yoruself").anyTimes();
//        EasyMock.replay();
        server1 = new Server(game, port, true);
        server2 = new Server(game, port, false);

//        EasyMock.verify();
    }

    @AfterMethod
    private void deleteServer() throws IOException {
        LOGGER.log(Level.INFO, "+++++ CLOSING server2 (CLIENT SERVER) +++++");
        server2.close();
        waitTime();
        LOGGER.log(Level.INFO, "+++++ CLOSING server1 (LISTEN SERVER) +++++");
        server1.close();
        waitTime();
    }

    /**
     * Test of a constructor for the Server class. Tests the Server(game,
     * listen) constructor to ensure the attributes port and state are set
     * correctly.
     */
    @Test
    public void testServerConstructor() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerConstructor -----");
        try {
            server2.close();
        } catch (IOException ex) {
            exception = true;
        }
        Game game = EasyMock.createMock(Game.class);
        server2 = new Server(game, true);
        Assert.assertFalse(exception, "Exception found");
        Assert.assertEquals(server2.getPort(), 0, "Server not constructed");
        Assert.assertEquals(server2.getState(), 0, "Server not set as listen server");
        LOGGER.log(Level.INFO, "----- TEST testServerConstructor COMPLETED -----");
    }

    /**
     * Test of a constructor for the Server class. Tests the Server(game,
     * listen) constructor to ensure the attribute listen_thread is set
     * correctly.
     */
    @Test
    void testServerConstructorListen() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerConstructorListen -----");
        try {
            server2.close();
        } catch (IOException ex) {
            exception = true;
        }
        Game game = EasyMock.createMock(Game.class);
        server2 = new Server(game, false);
        Assert.assertFalse(exception, "Exception found");
        Assert.assertEquals(server2.getPort(), 0, "Server not constructed");
        Assert.assertEquals(server2.getListenThread(), null, "Server not constructed");
        LOGGER.log(Level.INFO, "----- TEST testServerConstructorListen COMPLETED -----");
    }

    /**
     * Test of a constructor for the Server class. Tests the Server(game, port,
     * listen) constructor to ensure the attribute port is set correctly.
     */
    @Test
    public void testServerConstructorPort() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerConstructorPort -----");
        try {
            server2.close();
        } catch (IOException ex) {
            exception = true;
        }
        Game game = EasyMock.createMock(Game.class);
        server2 = new Server(game, 12457, true);
        Assert.assertFalse(exception, "Exception found");
        Assert.assertEquals(server2.getPort(), 12457, "Server not constructed");
        LOGGER.log(Level.INFO, "----- TEST testServerConstructorPort COMPLETED -----");
    }

    /**
     * Test of a constructor for the Server class. Tests the Server(game, port,
     * listen) constructor to ensure the attribute listen_thread is set
     * correctly.
     */
    @Test
    void testServerConstructorListenPort() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerConstructorListenPort -----");
        try {
            server2.close();
        } catch (IOException ex) {
            exception = true;
        }
        Game game = EasyMock.createMock(Game.class);
        server2 = new Server(game, 12457, false);
        Assert.assertFalse(exception, "Exception found");
        Assert.assertEquals(server2.getPort(), 12457, "Server not constructed");
        Assert.assertEquals(server2.getListenThread(), null, "Server not constructed");
        LOGGER.log(Level.INFO, "----- TEST testServerConstructorListenPort COMPLETED -----");
    }

    /**
     * Test of an attribute setter for the port attribute.
     */
    @Test
    public void testSetPort() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testSetPort -----");
        server1.setPort(22223);
        Assert.assertEquals(server1.getPort(), 22223, "Port not changed");
        LOGGER.log(Level.INFO, "----- TEST testSetPort COMPLETED -----");
    }

    /**
     * Test of an attribute setter for the use_disconnected_sockets attribute.
     */
    @Test
    public void testUseDisconnectSockets() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testUseDisconnectSockets -----");
        server1.setUseDisconnectedSockets(true);
        Assert.assertEquals(server1.getUseDisconnectedSockets(), true, "Use Disonnected Sockets flag not changed");
        LOGGER.log(Level.INFO, "----- TEST testUseDisconnectSockets COMPLETED -----");
    }

    /**
     * Test of an attribute setter for the socket_list attribute.
     */
    @Test
    public void testSetSocketList() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testSetSocketList -----");
        server2.setSocketList(server1.getSocketList());
        Assert.assertEquals(server2.getSocketList(), server1.getSocketList(), "Socket List not changed");
        LOGGER.log(Level.INFO, "----- TEST testSetSocketList COMPLETED -----");
    }

    /**
     * Test of an attribute setter for the disconnected_sockets attribute.
     */
    @Test
    public void testSetDisconnectedSockets() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testSetDisconnectedSockets -----");
        ArrayList<String> string_array = new ArrayList<String>();
        string_array.add("1");
        string_array.add("2");
        server2.setDisconnectedSockets(string_array);
        Assert.assertEquals(server2.getDisconnectedSockets(), string_array, "Disconnected Sockets array not changed");
        LOGGER.log(Level.INFO, "----- TEST testSetDisconnectedSockets COMPLETED -----");
    }

    /**
     * This test ensures that a Server set up as a client can be successfully
     * changed to a listen server and bound correctly to a port to listen.
     */
    @Test
    public void testSetListenThreadClient() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testSetListenThreadClient -----");
        boolean flag1 = false;
        boolean flag2 = false;
        if (server2.getState() == 1) {
            flag1 = true;
        }
        if (flag1) {
            server2.setPort(23231);
            try {
                server2.setListenThread();
            } catch (IOException ex) {
                exception = true;
            }
            if (server2.getState() == 0) {
                flag2 = true;
            }
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertTrue(flag2, "Client server's state has not been set as listen Server. listen_thread may have not been created correctly. Server state is " + Integer.toString(server2.getState()));
        LOGGER.log(Level.INFO, "----- TEST testSetListenThreadClient COMPLETED -----");
    }

    /**
     * This test attempts to unbind a Server listening on a port and binds it to
     * a different port using the setListenThread method.
     */
    @Test
    public void testSetListenThreadServer() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testSetListenThreadServer -----");
        boolean flag1 = false;
        boolean flag2 = false;
        if (server1.getState() == 0) {
            flag1 = true;
        }
        if (flag1) {
            server1.setPort(23231);
            try {
                server1.setListenThread();
            } catch (IOException ex) {
                exception = true;
            }
            if (server1.getState() == 0) {
                flag2 = true;
            }
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertTrue(flag2, "Server's state has not been set as listen Server. listen_thread may have not been created correctly. Server state is " + Integer.toString(server2.getState()));
        LOGGER.log(Level.INFO, "----- TEST testSetListenThreadServer COMPLETED -----");
    }

    /**
     * This test uses the setListenThread method to test whether a Server
     * already listening on a port can be unbound from the port and rebound to
     * the same port using the setListenThread method.
     */
    @Test
    public void testSetListenThreadServerOnSamePort() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testSetListenThreadServerOnSamePort -----");
        boolean flag1 = false;
        boolean flag2 = false;
        if (server1.getState() == 0) {
            flag1 = true;
        }
        if (flag1) {
            try {
                server1.setListenThread();
            } catch (IOException ex) {
                exception = true;
            }
            if (server1.getState() == 0) {
                flag2 = true;
            }
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertTrue(flag2, "Server's state has not been set as listen Server. listen_thread may have not been created correctly. Server state is " + Integer.toString(server2.getState()));
        LOGGER.log(Level.INFO, "----- TEST testSetListenThreadServerOnSamePort COMPLETED -----");
    }

    /**
     * This test changes a Server constructed as a client into a listen server
     * using the setListenThread method. It then ensures that this newly changed
     * listen server is capable of accepting new connections.
     */
    @Test
    public void testSetListenThreadAndListen() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testSetListenThreadAndListen -----");
        boolean flag1 = false;
        boolean flag2 = false;
        int count = 0;
        if (server2.getState() == 1) {
            flag1 = true;
        }
        if (flag1) {
            server2.setPort(23231);
            try {
                server2.setListenThread();
            } catch (IOException ex) {
                exception = true;
            }
            if (server2.getState() == 0) {
                flag2 = true;
                server2.startThread();
            }
        }
        if (flag2) {
            try {
                server1.addSocket("127.0.0.1", 23231);
            } catch (IOException ex) {
                exception = true;
            }
            waitTime();
            if (server2.getSocketList() != null && !server2.getSocketList().isEmpty()) {
                for (SocketThread socket : server2.getSocketList().values()) {
                    count++;
                }
            }
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertTrue(flag2, "server2 not set as a listen server");
        Assert.assertEquals(count, 1, "No connection was established");
        LOGGER.log(Level.INFO, "----- TEST testSetListenThreadAndListen COMPLETED -----");
    }

    /**
     * This test ensures that the removeDisconnectedSocket method removes the
     * specified String from the disconnected_sockets array increments all other
     * values back into order correctly.
     */
    @Test
    public void testRemoveDisconnectedSocket() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testRemoveDisconnectedSocket -----");
        server1.startThread();
        ArrayList<String> string_array = new ArrayList<String>();
        string_array.add("r");
        string_array.add("b");
        server1.setDisconnectedSockets(string_array);
        server1.removeDisconnectedSocket("r");
        Assert.assertEquals(server1.getDisconnectedSockets().get(0), "b", "Hash not removed from disconnected_sockets changed");
        LOGGER.log(Level.INFO, "----- TEST testRemoveDisconnectedSocket COMPLETED -----");
    }

    /**
     * This test ensures the version of addSocket(Socket) correctly takes a
     * pre-constructed Socket class, builds it into a SocketThread and adds it
     * to the socket_list map.
     */
    @Test
    public void testServerClientConnectSocket() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerClientConnectSocket -----");
        String client_hash = "";
        server1.startThread();
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", port);
        } catch (IOException ex) {
            exception = true;
        }
        try {
            client_hash = server2.addSocket(socket);
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        Assert.assertFalse(exception, "Exception found");
        Assert.assertFalse(client_hash.isEmpty(), "Client not connected");
        Assert.assertFalse(server2.getSocketList().isEmpty(), "SocketThread not added to socket_list");
        LOGGER.log(Level.INFO, "----- TEST testServerClientConnectSocket COMPLETED -----");
    }

    /**
     * This test ensures the version of addSocket(Sock) correctly takes a
     * pre-constructed Sock class, builds it into a SocketThread and adds it to
     * the socket_list map.
     */
    @Test
    public void testServerClientConnectSock() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerClientConnectSock -----");
        String client_hash = "";
        server1.startThread();
        Sock sock = null;
        try {
            sock = new Sock("127.0.0.1", port);
        } catch (IOException ex) {
            exception = true;
        }
        try {
            client_hash = server2.addSocket(sock);
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        Assert.assertFalse(exception, "Exception found");
        Assert.assertFalse(client_hash.isEmpty(), "Client not connected");
        Assert.assertFalse(server2.getSocketList().isEmpty(), "SocketThread not added to socket_list");
        LOGGER.log(Level.INFO, "----- TEST testServerClientConnectSock COMPLETED -----");
    }

    /**
     * This test ensures the version of addSocket(String) correctly takes a
     * String representing an IP address, builds it into a SocketThread and adds
     * it to the socket_list map.
     */
    @Test
    public void testServerClientConnectIp() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerClientConnectIp -----");
        String client_hash = "";
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        for (SocketThread sockets : server1.getSocketList().values()) {
            client_hash = sockets.getHash();
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertFalse(client_hash.isEmpty(), "Client not connected");
        Assert.assertFalse(server2.getSocketList().isEmpty(), "SocketThread not added to socket_list");
        LOGGER.log(Level.INFO, "----- TEST testServerClientConnectIp COMPLETED -----");
    }

    /**
     * This test ensures the version of addSocket(String, int) correctly takes
     * a String representing an IP address and an int representing a port
     * number, builds them into a SocketThread and adds it to the socket_list
     * map.
     */
    @Test
    public void testServerClientConnectIpPort() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerClientConnectIpPort -----");
        String client_hash = "";
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1", port);
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        for (SocketThread sockets : server1.getSocketList().values()) {
            client_hash = sockets.getHash();
        }
        try {
            if (server1.containsHash(client_hash) == true) {
                client_hash = "connected";
            }
        } catch (IOException ex) {
            exception = true;
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertEquals(client_hash, "connected", "Client not connected");
        Assert.assertFalse(server2.getSocketList().isEmpty(), "SocketThread not added to socket_list");
        LOGGER.log(Level.INFO, "----- TEST testServerClientConnectIpPort COMPLETED -----");
    }

    /**
     * This test ensures that the disconnect method correctly removes the
     * SocketThread corresponding to the given hash String from the socket_list
     * map. It also ensures the Thread is gracefully ended.
     */
    @Test
    public void testServerClientDisconnect() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerClientDisconnect -----");
        String client_hash = "";
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        for (SocketThread sockets : server1.getSocketList().values()) {
            client_hash = sockets.getHash();
        }
        try {
            server2.close();
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        try {
            if (server1.containsHash(client_hash) != true || !server1.getSocketList().get(client_hash).getRun()) {
                client_hash = "disconnected";
            }
        } catch (IOException ex) {
            exception = true;
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertEquals(client_hash, "disconnected", "Connection not closed successfully");
        LOGGER.log(Level.INFO, "----- TEST testServerClientDisconnect COMPLETED -----");
    }

    /**
     * This test ensures that the disconnect method correctly removes the
     * SocketThread corresponding to the given hash String from the socket_list
     * map and that the hash String is added to the disconnected_sockets array
     * if the use_disconnected_sockets attribute is set as true. It also ensures
     * the Thread is gracefully ended.
     */
    @Test
    public void testServerClientDisconnectWithHash() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerClientDisconnectWithHash -----");
        String client_hash = "";
        server1.setUseDisconnectedSockets(true);
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        for (SocketThread sockets : server1.getSocketList().values()) {
            client_hash = sockets.getHash();
        }
        try {
            server2.close();
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        Assert.assertFalse(exception, "Exception found");
        Assert.assertEquals(server1.getDisconnectedSockets().get(0), client_hash, "Disconnected sockets hash not logged");
        LOGGER.log(Level.INFO, "----- TEST testServerClientDisconnectWithHash COMPLETED -----");
    }

    /**
     * This test ensures that the close() method of Server correctly closes all
     * attributes and threads, including closing the listen_thread attribute
     * only if the Server is set as a listen server. Ensures all Threads are
     * closed gracefully.
     */
    @Test
    public void testServerClose() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testServerClose -----");
        server1.startThread();
        waitTime();
        boolean flag1 = false;
        boolean flag2 = false;
        if (server1.getListenThread().getRun()) {
            flag2 = true;
        }
        if (flag2) {
            try {
                server1.close();
            } catch (IOException ex) {
                exception = true;
            }
            waitTime();
            if (!server1.getListenThread().getRun()) {
                flag1 = true;
            }
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertTrue(flag1, "Server not closed as a listen server");
        LOGGER.log(Level.INFO, "----- TEST testServerClose COMPLETED -----");
    }

    /**
     * This test uses the replaceHash method to ensure that a SocketThread
     * already added to the socket_list array can be moved to correspond to a
     * different key in the array.
     */
    @Test
    public void testReplaceHash() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testReplaceHash -----");
        String client_hash = "";
        Game game = EasyMock.createMock(Game.class);
        Server server3 = new Server(game, port, false);
        server1.setUseDisconnectedSockets(true);
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        ArrayList<String> string_array = new ArrayList<String>();
        for (SocketThread sockets : server1.getSocketList().values()) {
            string_array.add(sockets.getHash());
        }
        server1.setDisconnectedSockets(string_array);
        try {
            server3.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        for (SocketThread sockets : server1.getSocketList().values()) {
            client_hash = sockets.getHash();
        }
        boolean flag = server1.connectDisconnectedSocket(client_hash, string_array.get(0));
        waitTime();
        Assert.assertTrue(flag, "Sockets hash not changed");
        try {
            server3.close();
        } catch (IOException ex) {
            exception = true;
        }
        Assert.assertFalse(exception, "Exception found");
        LOGGER.log(Level.INFO, "----- TEST testReplaceHash COMPLETED -----");
    }

    /**
     * Tests the pingSockets function to send a blank String to two connected
     * sockets and ensures neither of them are detected as disconnected after
     * sending the message.
     */
    @Test
    public void testPingSocket() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testPingSocket -----");
        Game game = EasyMock.createMock(Game.class);
        Server server3 = new Server(game, port, false);
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        try {
            server3.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        try {
            server1.pingSockets();
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        boolean not_disconnected = server1.getDisconnectedSockets().isEmpty();
        Assert.assertTrue(not_disconnected, "Could not ping sockets");
        try {
            server3.close();
        } catch (IOException ex) {
            exception = true;
        }
        Assert.assertFalse(exception, "Exception found");
        LOGGER.log(Level.INFO, "----- TEST testPingSocket COMPLETED -----");
    }

    /**
     * Tests whether the Server.close() method correctly unbinds any bound ports
     * used by the Server class that are not currently being listened on.
     */
    @Test
    public void testReleasePortNumber() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testReleasePortNumber -----");
        Game game = EasyMock.createMock(Game.class);
        try {
            server1.close();
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        Server server3 = new Server(game, port, true);
        Assert.assertEquals(server3.getState(), 0, "Server not set as state 0. Port could not be listened on");
        try {
            server3.close();
        } catch (IOException ex) {
            exception = true;
        }
        Assert.assertFalse(exception, "Exception found");
        LOGGER.log(Level.INFO, "----- TEST testReleasePortNumber COMPLETED -----");
    }

    /**
     * Tests whether the Server.close() method correctly unbinds any bound ports
     * used by the Server class that are currently being listened.
     */
    @Test
    public void testReleasePortNumberRunning() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testReleasePortNumberRunning -----");
        Game game = EasyMock.createMock(Game.class);
        server1.startThread();
        try {
            server1.close();
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        Server server3 = new Server(game, port, true);
        Assert.assertEquals(server3.getState(), 0, "Server not set as state 0. Port could not be listened on");
        try {
            server3.close();
        } catch (IOException ex) {
            exception = true;
        }
        Assert.assertFalse(exception, "Exception found");
        LOGGER.log(Level.INFO, "----- TEST testReleasePortNumberRunning COMPLETED -----");
    }

    /**
     * Calls the startThread method on a Server setup as a client. Ensures that the call doesn't do anything and that listen_thread is not started.
     */
    @Test
    public void testStartThreadClient() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testStartThreadClient -----");
        server2.startThread();
        Assert.assertEquals(server2.getState(), 1, "server2 not set as client server");
        Assert.assertEquals(server2.getListenThread(), null, "ListenThread started on client server");
        LOGGER.log(Level.INFO, "----- TEST testStartThreadClient COMPLETED -----");
    }
    
    @Test
    public void testToStringClient() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testToStringClient -----");
        String to_string = null;
        to_string = server2.toString();
        boolean server_type = false;
        if(to_string != null) {
            String server_string = "";
            char[] ch = to_string.toCharArray();
            for(int i=0;i<6;i++) {
                server_string += ch[i];
            }
            if(server_string.compareTo("Server") == 0) {
                server_type = true;
            }
        }
        Assert.assertTrue(server_type, "Server not detected as a client server when generating String data");
        Assert.assertNotEquals(to_string, null, "Server data not generated into a readable String");
        LOGGER.log(Level.INFO, "Server toString() details: \n{0}", to_string);
        LOGGER.log(Level.INFO, "----- TEST testToStringClient COMPLETED -----");
    }
    
    @Test
    public void testToStringClientCh() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testToStringClientCh -----");
        String to_string = null;
        to_string = server2.toString("\t");
        boolean server_type = false;
        if(to_string != null) {
            String server_string = "";
            char[] ch = to_string.toCharArray();
            for(int i=1;i<7;i++) {
                server_string += ch[i];
            }
            if(server_string.compareTo("Server") == 0) {
                server_type = true;
            }
        }
        Assert.assertTrue(server_type, "Server not detected as a client server when generating String data");
        Assert.assertNotEquals(to_string, null, "Server data not generated into a readable String with added character");
        LOGGER.log(Level.INFO, "Server toString() details: \n{0}", to_string);
        LOGGER.log(Level.INFO, "----- TEST testToStringClientCh COMPLETED -----");
    }
    
    @Test
    public void testToStringServer() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testToStringServer -----");
        String to_string = null;
        Game game = EasyMock.createMock(Game.class);
        server1.setUseDisconnectedSockets(true);
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        Server server3 = new Server(game,port,false);
        try {
            server3.addSocket("127.0.01");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        try {
            server3.close();
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        to_string = server1.toString();
        boolean server_type = false;
        if(to_string != null) {
            String server_string = "";
            char[] ch = to_string.toCharArray();
            for(int i=0;i<6;i++) {
                server_string += ch[i];
            }
            if(server_string.compareTo("Listen") == 0) {
                server_type = true;
            }
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertTrue(server_type, "Server not detected as a listen server when generating String data");
        Assert.assertNotEquals(to_string, null, "Server data not generated into a readable String");
        LOGGER.log(Level.INFO, "Server toString() details: \n{0}", to_string);
        LOGGER.log(Level.INFO, "----- TEST testToStringServer COMPLETED -----");
    }
    
    @Test
    public void testToStringServerCh() {
        LOGGER.log(Level.INFO, "----- STARTING TEST testToStringServerCh -----");
        String to_string = null;
        Game game = EasyMock.createMock(Game.class);
        server1.setUseDisconnectedSockets(true);
        server1.startThread();
        try {
            server2.addSocket("127.0.0.1");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        Server server3 = new Server(game,port,false);
        try {
            server3.addSocket("127.0.01");
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        try {
            server3.close();
        } catch (IOException ex) {
            exception = true;
        }
        waitTime();
        to_string = server1.toString("\t");
        boolean server_type = false;
        if(to_string != null) {
            String server_string = "";
            char[] ch = to_string.toCharArray();
            for(int i=1;i<7;i++) {
                server_string += ch[i];
            }
            if(server_string.compareTo("Listen") == 0) {
                server_type = true;
            }
        }
        Assert.assertFalse(exception, "Exception found");
        Assert.assertTrue(server_type, "Server not detected as a listen server when generating String data");
        Assert.assertNotEquals(to_string, null, "Server data not generated into a readable String with added character");
        LOGGER.log(Level.INFO, "Server toString() details: \n{0}", to_string);
        LOGGER.log(Level.INFO, "----- TEST testToStringServerCh COMPLETED -----");
    }
}