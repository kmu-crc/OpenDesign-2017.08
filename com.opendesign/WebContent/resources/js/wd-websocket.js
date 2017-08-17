/**
 * WebSocketWrapper
 */ 
function WebSocketWrapper(ops) {
	this.wsUri = "{0}/websocket/connect.do".replace('{0}', wsGlobalHost); //uri
	this.commands = {}; //command map
	this.websocket = null; 
	this.projectSeq = ''; //project에 쓰이느 seq
	this.HEARTBEAT_INTERVAL = 60 * 1000; // 1분: client와 server connection을 살리는 간격   
	// ops
	this.uri = ops.uri || ''; // uri
	this.projectCallback = null; //callback함수
	if(typeof ops.projectCallback != 'undefined') {
		this.projectCallback = ops.projectCallback;
	}
}

WebSocketWrapper.prototype = {
	doSend: function(message) {
    	this._writeToScreen(">>> Message Sent: " + message);
    	this.websocket.send(message);
    }
    ,doSendJson: function(json) {
    	var message = JSON.stringify(json);
    	this.doSend(message);
    }
    ,close: function() {
    	if(this.websocket != null) {
    		this.websocket.close();
    		this.websocket = null;
    	}
    	if(window.heartbeatTimer) {
    		clearInterval(window.heartbeatTimer);
    		window.heartbeatTimer = null; 
    	}
    }
    ,setProjectCallback: function(callback) {
    	if(callback) {
    		this.projectCallback = callback;
    	}
    }
	,init: function() {
		var me = this;
		if(this.websocket != null) {
			console.log('>>> websocket exists.');
			return;
		}
		this.websocket = new WebSocket(this.wsUri);
		this.websocket.onopen = function(evt) {
			me._onOpen(evt)
        };
        this.websocket.onmessage = function(evt) {
        	me._onMessage(evt)
        };
        this.websocket.onerror = function(evt) {
        	me._onError(evt)
        };
        // 
        if(this.uri.indexOf('/project/') != -1 && this.uri.indexOf('projectSeq=') != -1) {
        	var projectSeqMatch = this.uri.match(/projectSeq=([^&]+)/);
        	if(projectSeqMatch != null && projectSeqMatch.length > 1) {
        		var projectSeq = projectSeqMatch[1];
        		this.projectSeq = projectSeq;
        	}
        }
        //
        this._initCommands();
        //
        this._sendHeartbeatMsg();
	}
	,_initCommands : function(){
		//commands: sync with SocketHandler.java: interface Command
        this.commands["OUT_NOTIFY_PROJECT_CHANGED"] = new ProjectCommand(this.projectSeq); //프로젝트 
        this.commands["OUT_NOTIFY_ALARM_CHANGED"] = new AlarmCommand(); //알람
        this.commands["OUT_NOTIFY_MSG_CHANGED"] = new MsgCommand(); //메시지
	}
	,_onOpen: function(evt) {
		this._writeToScreen(">>> Connected to Endpoint!");
        //초기화 handshake: uri = "/project/openProjectDetail.do?projectSeq=3";
        var param = {cmd:"IN_HANDSHAKE", uri:this.uri};
        this.doSendJson(param);
        console.log('>>> onOpen done');
	}
	,_onMessage: function(evt) {
		this._writeToScreen(">>> Message Received: " + evt.data);
        var msg = $.parseJSON(evt.data);
        // message 처리:
		var cmd = this.commands[msg.cmd]; 
		cmd.execute( msg, this );
    }
    ,_onError: function(evt) {
        this._writeToScreen('>>> ERROR: ' + evt.data);
    }
    ,_writeToScreen: function(message) {
        console.log(message);
    }
    /** client와 server connection을 살리는 작용 */
    ,_sendHeartbeatMsg: function() {
    	var me = this;
    	// 
    	window.heartbeatTimer =  setInterval(function(){
    		var param = {cmd:"IN_HEARTBEAT"};
    		me.doSendJson(param);
        	console.log('>>> send Heartbeat msg.');
    	}, me.HEARTBEAT_INTERVAL);
    }
}

/**
 * project command 
 */
function ProjectCommand(ops) {
	this.projectSeq = ops.projectSeq;
	console.log('>>> ProjectCommand init. projectSeq=' + this.projectSeq);
}
ProjectCommand.prototype = {
	execute: function(msg, wsObj) {
		console.log('>>> ProjectCommand: execute');
		console.log(msg);
		console.log(wsObj);
		console.log('(wsObj.projectSeq == msg.projectSeq) = ' + (wsObj.projectSeq == msg.projectSeq) );
		//
		var projectSeq = wsObj.projectSeq;
		if(projectSeq != '' && projectSeq == msg.projectSeq) {
			console.log('>>> ProjectCommand: projectCallback'); 
			if(wsObj.projectCallback != null) {
				wsObj.projectCallback();
			}
		}
	}
}


/**
 * 알람 command 
 */
function AlarmCommand() {
	console.log('>>> AlarmCommand init. ');
}
AlarmCommand.prototype = {
	execute: function(msg, wsObj) {
		console.log('>>> AlarmCommand: execute');
		console.log(msg);
		console.log(wsObj);
		//
		onNotifyAlarmChanged();
	}
}

/**
 * 메시지 command 
 */
function MsgCommand() {
	console.log('>>> MsgCommand init. ');
}
MsgCommand.prototype = {
	execute: function(msg, wsObj) {
		console.log('>>> MsgCommand: execute');
		console.log(msg);
		console.log(wsObj);
		//
		onNotifyMsgChanged();
	}
}