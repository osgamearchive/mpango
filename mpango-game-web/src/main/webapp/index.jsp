<!doctype html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>mpango-game-web</title>
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
		<script src="js/cometd.mpango.js"></script>

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
