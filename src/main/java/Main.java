
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.Scanner;  // Import the Scanner class
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttClient;

public class Main {

    private static String publisherId = UUID.randomUUID().toString();
    private static IMqttClient client;

    public static void main(String[] args) throws MqttException {
        client = new MqttClient("tcp://207.154.218.89:1883", publisherId);

        clientConnect();

        Scanner scanner = new Scanner(System.in);  // Create a Scanner object<
        System.out.println("Enter Topic");

        String topic = scanner.nextLine();  // Read user input
        System.out.println("Topic is: " + topic);  // Output user input 

        while (true) {
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            publisher(topic, message);
        }
    }
    
    private static void clientConnect(){
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

    private static void publisher(String topic, String msg) throws MqttException {
        MqttMessage message = new MqttMessage(msg.getBytes());

        message.setQos(0);
        message.setRetained(true);

        client.publish(topic, message);


    }

}
