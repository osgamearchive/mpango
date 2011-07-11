window.Mpango = (function( window, document, jQuery ) {

	var $ = jQuery;
	var config;

	function constructor( container, options ) {
		config = $.extend( true, {
			"contextPath": null
		}, options );

		// Expose mpango parent node.
		Mpango.$mpango =  $( container, window.document );

		// cometd: initialize and connect
		Mpango.CometD.initialize( options.cometd );
		Mpango.CometD.connect();

		//Mpango.Map.initialize( Mpango.$mpango );
	}

	return {
		run: function( container, opts ){
			constructor( container, opts );
		}
	};

})( this, this.document, jQuery );
