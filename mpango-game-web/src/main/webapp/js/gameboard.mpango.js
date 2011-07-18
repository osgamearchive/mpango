Mpango.GameBoard = (function( window, document, jQuery ) {

	var $container;

	var _instance;

	function GameBoard() {
		var that = this;

		// Adding DOM-Container
		$container = $( "<div class='gameboard' />" );
		Mpango.$mpango.append( $container );


		// Adding Observer.
		this.observer = Mpango.Observer.getInstance( "/service/gameBoard", Mpango.config.cometd );
		this.observer.subscribe(function( message ) {
			that.render( message );
		});

		// Get map data.
		this.observer.publish( { event: 'JOIN', username: 'eduardo' } );
	}

	GameBoard.prototype.render = function( message ) {
		log( "Gameboard: Rendering board with new data.", message.data );

		var data = message.data;
		var offset = {
			left: 0,
			top: 0
		};

		for( var row = 0; row < data.rowSize; row += 1 ) {
			for( var col = 0; col < data.colSize; col += 1 ) {
				var $cell = $( "<div class='cell' />" );
				var info = [];
				var cell = data.cells[row][col];
				log( row, col, data.cells[row][col] );
				for( var key in cell ) {
					info.push( key + ": " + cell[key]+ "<br />" );
				}
				info = info.join("");
				$cell
					.css({
						"left": offset.left,
						"top": offset.top
					})
					.html( info );

				$container.append( $cell );
				offset.left += 128;
			}
			offset.left = 0;
			offset.top += 128;
		}
	};

	return {
		getInstance: function() {
			if( _instance === undefined ) {
				_instance = new GameBoard();
			}
			return _instance;
		}
	};


})( this, this.document, jQuery );