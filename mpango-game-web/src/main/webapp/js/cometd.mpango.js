Mpango.CometD = (function( window, document, jQuery ) {
	var $ = jQuery;
	var cometd = $.cometd;

	var url;

	function _connectionEstablished() {
		log( "CometD Connection Established" );
    };

    function _connectionBroken() {
    	log( "CometD Connection Broken" );
    };

    function _connectionClosed() {
    	log( "CometD Connection Closed" );
    };


    // Function that manages the connection status with the Bayeux server
    var _connected = false;
    function _metaConnect( message ) {
    	if( cometd.isDisconnected() ) {
    		_connected = false;
    		_connectionClosed();
    		return;
    	}

    	var wasConnected = _connected;
    	_connected = message.successful === true;

    	if ( !wasConnected && _connected ) {
    		_connectionEstablished();
    	}
    	else if( wasConnected && !_connected ) {
    		_connectionBroken();
    	}
    };

    function _metaDisconnect( message ) {
    	if( message.successful ) {
    		_connected = false;
    	}
    };



    // Function invoked when first contacting the server and
    // when the server has lost the state of this client
    function _metaHandshake( handshake ) {
    	if( handshake.successful === true ) {
    		cometd.batch( function() {
    			cometd.subscribe( "/game", function( message ) {
    				log( "Server Says: ", message.data.greeting );
    			});

    			// Publish on a service channel since the message is for the server only
    			cometd.publish( "/service/game", { "name": "Hell" } );

    			cometd.subscribe('/gameBoard', function( message ) {
                    log( "Server Says: ", message.data.greeting );
                    log( "Event: ", message.data.event.date, message.data.event.name );
                });

                // Publish on a service channel since the message is for the server only
                cometd.publish( "/service/gameBoard", { event: 'JOIN', username: 'eduardo' } );
    		});
    	}
    };


	function connect() {
		log("Mapango.CometD.connect()");
		cometd.handshake();
	};

	function disconnect( force ) {
		log("Mapango.CometD.disconnect( force )", force || false);
		cometd.disconnect( force || false );
	};


	function initialize( config ) {
		log("Mapango.CometD.initialize( config )", config);

		url = location.protocol + "//" + location.host + config.contextPath + "/cometd";

		cometd.configure({
			url: url,
            logLevel: config.logLevel
		});

		// add meta listeners
		cometd.addListener( "/meta/handshake", function( message ) {
			_metaHandshake( message );
		});
        cometd.addListener( "/meta/connect", function( message ) {
        	_metaConnect( message );
        });
        cometd.addListener( "/meta/disconnect", function( message ) {
        	_metaDisconnect( message );
        });

		// force disconnect on window unload
		$( window ).unload(function() {
			disconnect( true );
		});
	};

	// exposed functions/objects
	return {
		connect: function() {
			connect();
		},
		disconnect: function( force ) {
			disconnect( force );
		},
		initialize: function( config ){
			initialize( config );
		}
	};

})( this, this.document, jQuery );
