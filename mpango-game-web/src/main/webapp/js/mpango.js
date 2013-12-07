window.Mpango = (function( window, document, jQuery ) {

	var $ = jQuery;
	var config;

	var gameObserver;
	var gameBoardObserver;


	function _constructor( container, options ) {
		config = $.extend( true, {
			"cometd": {
				"contextPath": null,
				"logLevel": null
			}
		}, options );

		// Expose mpango global objects.
		Mpango.$mpango =  $( container, window.document );
		Mpango.config = config;

		// Create gameboard
		var gameboard = Mpango.GameBoard.getInstance();
	}

	return {
		run: function( container, opts ){
			_constructor( container, opts );
		}
	};

})( this, this.document, jQuery );