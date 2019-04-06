
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bagge
 */
public class MqttClient {

    String publisherId = UUID.randomUUID().toString();

    private MqttClient(String URl, String publisherId)
    {
        
    }
    MqttConnectOptions options = new MqttConnectOptions();

    public void Options()
    {
    options.setAutomaticReconnect (true);
    options.setCleanSession (true);
    options.setConnectionTimeout (10);
    publisher.connect (options);

    }

    private class publisher {

        public publisher()
        {
            IMqttClient publisher = (IMqttClient) new MqttClient("tcp://iot.eclipse.org:1883", publisherId);
        }
    }
    
}
