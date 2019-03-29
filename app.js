const app = require("express")();
const server = require("http").Server(app);
const io = require("socket.io")(server);

const MQTT = require("async-mqtt");
const client = MQTT.connect("tcp://207.154.218.89:1883");

const publish = async (message) => {
	try {
		await client.publish("chat", message);
	} catch (e) {
		console.log(e.stack);
		process.exit();
	}
}

client.on("connect", () => {
	try {
		client.subscribe("chat");
		console.log("Subscribed to chat");
	} catch (e) {
		console.log(e.stack);
		process.exit();
	}
});
app.get("/", function (req, res) {
	res.sendFile(__dirname + "/public/index.html");
});

io.origins('*:*') // for latest version
io.on("connection", function (socket) {

	client.on("message", async (topic, message) => {
		socket.emit("log", message.toString());
	});

	socket.on("send", (data) => {
		/* would run some regex or safety checking on this in production */
		publish(data);
	});
});



server.listen(80, () => console.log("Listening on port 80"));
