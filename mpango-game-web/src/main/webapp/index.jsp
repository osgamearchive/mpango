<!doctype html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>mpango-game-web</title>

	    <style>
	    	html,
	    	body { height: 100%; margin: 0; width: 100%; }
	    	.gameboard { height: 100%; position: relative; width: 100%; }
	    	.cell {
	    		background-color: #ffffff;
	    		border: 1px solid #000;
	    		font: 11px/15px Arial, sans-serif;
	    		height: 126px;
	    		position: absolute;
	    		width: 126px;
	    	}
	    	.cell:hover {
	    		transform: scale(1.1);
			    -moz-transform: scale(1.1);
			    -ie-transform: scale(1.1);
			    -webkit-transform: scale(1.1);
	    	}
	    </style>

	</head>
	<body>
		<div class="page">
			<div id="mpango" class="mpango">

			</div>
		</div>

		<script src="${pageContext.request.contextPath}/jquery/jquery-1.6.2.js"></script>
		<script src="${pageContext.request.contextPath}/jquery/json2.js"></script>
		<script src="${pageContext.request.contextPath}/org/cometd.js"></script>
		<script src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>

		<script src="js/helper.js"></script>
		<script src="js/mpango.js"></script>
		<script src="js/observer.mpango.js"></script>
		<script src="js/gameboard.mpango.js"></script>

		<script>
			Mpango.run("#mpango", {
				"cometd": {
					"contextPath": "${pageContext.request.contextPath}",
					"logLevel": "info" // warn, info, debug
				}
			});
		</script>
	</body>
</html>
