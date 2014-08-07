/*
@author Ricardo Vega - "ricardoe" in the google mail system
Flot plugin for trendlines. Controlled through the option
"trendline" in the global series options

  series: {
    trendline: {
        [show:boolean],
        [lineWidth:integer],
        [fill:boolean],
        [fillColor:color],
        [steps:boolean]
    }
  }

This plugin needs to add a new hook called here "processParsedData"
that is fired at the bottom of the for-loop in parseData function
 */

(function ($) {
	function init(plot) {
		var opts, enabled = false,
		defaultLine = {},
		defaultOther = { show: false };

		function checkEnabled(plot, options) {
			if ('trendline' in options && options.series.trendline && options.series.trendline.show) {
				enabled = true;
				opts = options;
				defaultLine = $.extend(defaultLine, options.series.trendline);
				plot.hooks.processParsedData.push(bestfit);
			}
		}

		// 추세선은 기본적으로 x축이 기간, Y축이 데이터.. 
		function bestfit(plot, series, data) {
			
			var x, ns;
			x = data.length;

			// Not enough data or disabled
			if(x < 2 || !enabled) return;

			var aa = 0;
			var bb = 0;
			var cc = 0;
			var dd = 0;
			var xpt = new Array();
			var ypt = new Array();
			var yintr = new Array();
			var ff1 = 0;
			var gg1 = 0;
			for(var y=0; y<x; y++){
				var a = parseFloat(data[y][0]);
				var b = parseFloat(data[y][1]);
				
				xpt[y] = a;  
				ypt[y] = b;

				aa = aa + a;
				bb = bb + b;
				cc = cc + (a*b);
				dd = dd + (a*a); 
				
				var f = ((x*cc) - (aa*bb));
				var g = ((x*dd) - (aa*aa));	
				
				if(g==0){
					if(console){
						console.log("Invalid data points > "+g);
					}
				}
				else{
					var ff = f/g;
					var gg = (bb-(ff*aa))/x;
					ff1 = Math.round(ff*100000)/100000;
					gg1 = Math.round(gg*100000)/100000;
				}
			}

			for(var y=0; y<x; y++){
				yintr[y] = Math.round((parseFloat(ff1 * xpt[y]) + parseFloat(gg1))*1000) / 1000;
			}
			
			var trendLineData = [];
			for(var i=0; i<xpt.length; i++){
				trendLineData.push([xpt[i], yintr[i]]);
			}
			
//			// We extend add the new serie to the series array
//			ns = $.extend(true, {}, opts.series, { data:trendLineData, label: 'trend', bars: defaultOther, lines: defaultLine, points:defaultOther, color: series[series.length-1].color } );
			ns = $.extend(true, {}, opts.series, { data:trendLineData, bars: defaultOther, lines: defaultLine, points:defaultOther, color: series[series.length-1].color, isTrendLine:true } );
			series.push(ns);
		}

		plot.hooks.processOptions.push(checkEnabled);
	}

	var options = { trendline: { show:false, lineWidth:2, fill:false, fillColor:null, steps:false } };

	$.plot.plugins.push({
		init: init,
		options: options,
		name: "trendline",
		version: "0.1"
	});
})(jQuery);