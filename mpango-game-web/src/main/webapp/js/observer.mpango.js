Mpango.Observer = (function( window, document, jQuery ) {

	/**
	 * private Cometd object.
	 */
	var Cometd = (function( window, document, jQuery ) {
		var $ = jQuery;
		var cometd = $.cometd;

		var config;
		var url;
		var connected = false;
		var connecting = false;

		var queue = [];

		(function(){
			var interval = window.setInterval(function() {
				while( queue.length > 0 && connected ) {
					var command = queue.shift();
					command.call();
				}
			}, 1000);
		})();


		/**
	     * handles connect messages form /meta/connect
	     */
	    function connectHandler( message ) {
	    	if( cometd.isDisconnected() ) {
	    		connected = false;
	    		log( "CometD: Connection closed.", message );
	    		return;
	    	}

	    	var wasConnected = connected;
	    	connected = message.successful === true;

	    	if ( !wasConnected && connected ) {
	    		log( "CometD: Connection established.", message );
	    	}
	    	else if( wasConnected && !connected ) {
	    		log( "CometD: Connection broken.", message );
	    	}
	    	connecting = false;
	    };


	    /**
	     * handles disconnect messages form /meta/disconnect
	     */
	    function disconnectHandler( message ) {
	    	if( message.successful ) {
	    		connected = false;
	    		log( "CometD: Connection successfully disconnected.", message );
	    	}
	    	else {
	    		log( "CometD: Disconnection unsuccessful.", message );
	    	}
	    };



	    /**
	     * handles handshake messages form /meta/handshake
	     */
	    function handshakeHandler( handshake ) {
	    	if( handshake.successful === true ) {
	    		log( "CometD: Handshake successfull." );
	    	}
	    	else {
	    		log( "CometD: Handshake attempt unsuccessful.", handshake );
	    	}
	    };


	    /**
	     * handles subscribe messages form /meta/handshake
	     */
	    function subscribeHandler( message ) {
	    	if( message.successful ) {
	    		log( "CometD: Subscription successfull.", message.subscription, message );
	    	}
	    	else {
	    		log( "CometD: Subscription failed.", message.subscription, message );
	    	}
	    };


	    /**
	     * public methods and objects
	     */
		function connect() {
			if( !connecting && !connected ) {
				log("CometD: Connecting.");
				connecting = true;
				cometd.handshake();
			}
		};


		function disconnect( force ) {
			log("CometD: Disconnecting.", "Force disconnection?", force || false);
			cometd.disconnect( force || false );
		};


		function init( config ) {
			if( typeof this.config !== "undefined" ) {
				// CometD has been initialized before. break here.
				return;
			}
			log("CometD: Initalizing.", config);

			// configure
			this.config = config;
			url = location.protocol + "//" + location.host + config.contextPath + "/cometd";
			cometd.configure({
				url: url,
	            logLevel: config.logLevel
			});

			// add meta listeners
	    	cometd.addListener( "/meta/connect", function( message ) {
	    		connectHandler( message );
	    	});
	    	cometd.addListener( "/meta/disconnect", function( message ) {
	    		disconnectHandler( message );
	    	});
	    	cometd.addListener( "/meta/handshake", function( message ) {
	    		handshakeHandler( message );
	    	});
	    	cometd.addListener( "/meta/subscribe", function( message ) {
	    		subscribeHandler( message );
	    	});


			// force disconnect on window unload
			$( window ).unload(function() {
				disconnect( true );
			});
		};


		function publish( channel, data ) {
			queue.push(function() {
				cometd.publish( channel, data );
			});
		}

		function subscribe( channel, callback ) {
			queue.push(function() {
				cometd.subscribe( channel, function( message ) {
					callback.call( this, message );
				});
			});
		}

		function unsubscribe(  ) {

		}

		/**
		 * Expose public functions.
		 */
		return {
			connect: function( callback ) {
				connect();
			},
			disconnect: function( force ) {
				disconnect( force );
			},
			initialize: function( config ) {
				init( config );
			},
			isConnected: function() {
				return connected;
			},
			publish: function( channel, data ) {
				publish( channel, data );
			},
			subscribe: function( channel, callback ) {
				subscribe( channel, callback );
			},
			unsubscribe: function( subscriber ) {
				unsubscribe( subscriber );
			}
		};

	})( window, document, jQuery );



	/**
	 * Observer
	 *
	 * Class which provides an easy way for listening to game events
	 * and taking care of message propagation to all subscribers.
	 */
	var instance = [];

	function Observer( channel, config ) {
		var that = this;
		this.channel = channel;
		this.config = config;
		this.subscriber = [];

		// Initialize and connect to CometD server.
		Cometd.initialize( Mpango.config.cometd );
		Cometd.connect();

		// Subscribe to channel.
		Cometd.subscribe( this.channel, function( message ) {
			for( var sub in that.subscriber ) {
				that.subscriber[sub].call( this, message );
			}
		});
	}


	Observer.prototype.publish = function( data ) {
		Cometd.publish( this.channel, data );
	};


	Observer.prototype.subscribe = function( callback ) {
		this.subscriber.push( callback );
	};


	Observer.prototype.unsubscribe = function( subscription ) {
	};


	return {
		getInstance: function( channel, config ) {
			if( instance[channel] === undefined ) {
				instance[channel] = new Observer( channel, config );
			}
			return instance[channel];
		}
	};

})( this, this.document, jQuery );