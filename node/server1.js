var WebSocketServer = require('ws').Server
  , wss = new WebSocketServer({port: 8080});
wss.on('connection', function(ws) {
    ws.on('message', function(message) {
		ws.send('ok');
        console.log('received: %s', message);
    });
    ws.send('something');
});