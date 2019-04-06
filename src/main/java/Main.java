
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.Scanner;  // Import the Scanner class
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

public class Main {

    private static String publisherId = UUID.randomUUID().toString();
    private static IMqttClient client;
    private static Scanner scanner;

    public static void main(String[] args) throws MqttException {
        client = new MqttClient("tcp://207.154.218.89:1883", publisherId);

        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable cause) {
            }

            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("Server: " + message.toString());
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });

        clientConnect();

        scanner = new Scanner(System.in);

        System.out.println("Enter Topic");
        String topic = scanner.nextLine();  // Read user input
        System.out.println("Topic is: " + topic);  // Output user input 

        client.subscribe(topic);
        
        new PublishThread(topic).start();
    }

    private static void clientConnect() {
        System.out.println("Connecting...");

        while (!client.isConnected()) {
            try {
                client.connect();
                System.out.print(".");
            } catch (MqttException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("\nConnected");
    }

    public static class PublishThread extends Thread {

        public PublishThread(String topic) {
            super(topic);
        }

        public void run() {
            while (true) {

                MqttMessage message = new MqttMessage(scanner.nextLine().getBytes());

                message.setQos(0);
                message.setRetained(true);

                try {
                    client.publish(getName(), message);
                } catch (MqttException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
