const MQTT = require("async-mqtt");
const client = MQTT.connect("tcp://207.154.218.89:1883");

// When passing async functions as event listeners, make sure to have a try catch block
const sendTest = async() => {
    console.log("Starting");
    try {
        await client.publish("test", "It works!");
        // This line doesn't run until the server responds to the publish
        await client.end();
        // This line doesn't run until the client has disconnected without error
        console.log("Done");
    } catch (e){
        // Do something about it!
        console.log(e.stack);
        process.exit();
    }
}

const publish = async (topic, message) => {
	try {
		await client.publish(topic, message);
	} catch (e){
		console.log(e.stack);
		process.exit();
	}
}

const subscribe = async (topic) => {
	try{
		await client.subscribe(topic);
		console.log("Subscribed to " + topic);
	} catch(e){
		console.log(e.stack);
		process.exit();
	}
}

subscribe("test");

client.on('message', async (topic, message) => {
	console.log(message.toString());
});