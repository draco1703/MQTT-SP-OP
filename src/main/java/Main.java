
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.util.Scanner;  // Import the Scanner class

public class Main {

    public static void main(String[] args) {
        MqttClient client = null;
        try {
            client = new MqttClient(
                    "tcp://207.154.218.89", //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence());
        } catch (MqttException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

//                MqttConnectOptions options = new MqttConnectOptions();
//                options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
////                options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        try {
            client.connect();
            System.out.println("connected");
        } catch (MqttException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        client.isConnected();
        System.out.println("isConnected");
        
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Topic");

        String topic = myObj.nextLine();  // Read user input
        System.out.println("Topic is: " + topic);  // Output user input 

        while(true){
            System.out.println("Enter message");
            String message = myObj.nextLine();
        MqttMessage msg = new MqttMessage();
        msg.setPayload(message.getBytes());
        msg.setQos(0);
        msg.setRetained(true);
        try {
            client.publish(topic, msg);
        } catch (MqttException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

}
