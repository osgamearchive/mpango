// usage: log('inside coolFunc', this, arguments);
// paulirish.com/2009/log-a-lightweight-wrapper-for-consolelog/
window.log = function(){
	log.history = log.history || [];   // store logs to an array for reference
	log.history.push(arguments);
	arguments.callee = arguments.callee.caller;
	if(this.console) console.log( Array.prototype.slice.call(arguments) );
};
// make it safe to use console.log always
(function(b){function c(){}for(var d="assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,markTimeline,profile,profileEnd,time,timeEnd,trace,warn".split(","),a;a=d.pop();)b[a]=b[a]||c;})(window.console=window.console||{});

/*
 * Author: Bastian Gutschke
 */


(function($){

	// get user layout or set default layout
	var defaultLayout = {
			"controls": {
				"id": "#controls",
				"anchorX": "left",
				"anchorY": "bottom",
				"x": 100,
				"y": 0
			},
			"info": {
				"id": "#info",
				"anchorX": "left",
				"anchorY": "top",
				"x": 20,
				"y": 30
			},
			"map": {
				"id": "#map",
				"anchorX": "right",
				"anchorY": "top",
				"x": 20,
				"y": 30
			}
	};

	//localStorage.removeItem("mpango.layout");

	// global object with settings
	var mpango = {
		"layout": JSON.parse(localStorage.getItem("mpango.layout")) || defaultLayout,
		// get url params as object
		"params": (function(){
			var params = {};
			var rawParams = window.location.search;
			if(rawParams === ""){
				return params;
			}
			else{
				rawParams = rawParams.substring(rawParams.indexOf("?")+1).split("&");
				for(var idx in rawParams){
					var param = rawParams[idx].split("=");
					param[1] === "true" ? param[1] = true : param[1] = false;
					params[param[0]] = param[1];
				}
				return params;
			}
		})()
	};



	/*
	 * Takes care of basic element positioning.
	 */
	$.fn.mpLayout = function(){

		return this.each(function(){
			log("$.fn.mpLayout", this);
			var that = this;

			/* position header and add resize event handler */
			var $header = $("#header", this);
			var $main = $("#main", this);
			var $controls = $("#controls", this);

			var headerHeight = $header.height();

			$(window).resize(function(){
				var windowSize = {
					"height": $(window).height(),
					"width": $(window).width()
				};

				$header.width(windowSize.width);
				$main.height(windowSize.height-headerHeight);
			});

			// initial trigger to layout the page
			$(window).trigger("resize");

			/* position gui elements */
			var layout = mpango.layout;
			for(var e in layout){
				$(layout[e].id, that)
					.css(layout[e].anchorX, layout[e].x)
					.css(layout[e].anchorY, layout[e].y);

			}
		});
	};


	/*
	 * Add drag funtionality
	 */
	$.fn.mpDragable = function(){
		return this.each(function(){
			log("$.fn.mpDragable", this);

			var $handle = $(this);
			var $element = $(this);
			var elementId = $element.attr("id");
			var layout = mpango.layout[elementId];

			var offsetX, offsetY;
			var grabbed = false;

			var windowHeight = $(window).height();
			var windowWidth = $(window).width();

			$(window).resize(function(){
				windowHeight = $(window).height();
				windowWidth = $(window).width();
			});

			$handle
				.mousedown(function(e){
					grabbed = true;
					offsetX = e.pageX;
					offsetY = e.pageY;
					$element.css("cursor", "move");
				})
				.mouseleave(function(e){
					grabbed = false;
				})
				.mousemove(function(e){
					if(grabbed === false) { return; }

					var anchorX, anchorY;

					// set anchors
					e.pageX <= windowWidth / 2 ? anchorX = "left" : anchorX = "right";
					e.pageY <= windowHeight / 2 ? anchorY = "top" : anchorY = "bottom";

					if(layout.anchorX !== anchorX){
						layout.x = windowWidth-layout.x-$element.width();
						layout.anchorX = anchorX;
					}
					if(layout.anchorY !== anchorY){
						layout.y = windowHeight-layout.y-$element.height();
						layout.anchorY = anchorY;
					}


					// set X and Y anchor and position according to the closest border
					if(layout.anchorX === "left"){
						$element.css({
							"left": layout.x-(offsetX-e.pageX),
							"right": "auto"
							});
					}
					else{
						$element.css({
							"left": "auto",
							"right": layout.x-(offsetX-e.pageX)*-1
							});
					}
					if(layout.anchorY === "top"){
						$element.css({
							"top": layout.y-(offsetY-e.pageY),
							"bottom": "auto"
							});
					}
					else{
						$element.css({
							"top": "auto",
							"bottom": layout.y-(offsetY-e.pageY)*-1
							});
					}
				})
				.mouseup(function(e){
					layout.anchorX === "left" ? layout.x = parseInt($element.css("left")) : layout.x = parseInt($element.css("right"));
					layout.anchorY === "top" ? layout.y = parseInt($element.css("top")) : layout.y = parseInt($element.css("bottom"));

					grabbed = false;

					// save to local storage
					mpango.layout[elementId] = layout;
					localStorage.setItem("mpango.layout", JSON.stringify(mpango.layout));

					$element.css("cursor", "default");
				});

		});
	};


	// fire functions
	$("#container").mpLayout();
	$(".dragable").mpDragable();

})(jQuery);
