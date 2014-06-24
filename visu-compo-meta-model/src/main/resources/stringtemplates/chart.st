chart(chartname,dataname,categoryname,graphs) ::= <<
var $chartname$;
AmCharts.ready(function () {
	
// SERIAL CHART
$chartname$ = new AmCharts.AmSerialChart();
$chartname$.dataProvider = $dataname$;
$chartname$.categoryField = "$categoryname$";
$chartname$.startDuration = 0.15;
$chartname$.zoomOutButton = {
	backgroundColor: '#0000FF',
	backgroundAlpha: 0.15
};
$chartname$.categoryAxis.labelRotation = 45;
	
// AXES 
$chartname$.addValueAxis(new AmCharts.ValueAxis());

// GRAPHS

$graphs; separator="\n"$

// CURSOR 
var chartCursor = new AmCharts.ChartCursor(); 
chartCursor.cursorPosition = "mouse"; 
$chartname$.addChartCursor(chartCursor);

// SCROLLBAR 
var chartScrollbar = new AmCharts.ChartScrollbar(); 
$chartname$.addChartScrollbar(chartScrollbar);

// LEGEND 
var legend = new AmCharts.AmLegend(); 
legend.marginLeft = 110; 
$chartname$.addLegend(legend);

$chartname$.write("$chartname$_div");
});
>>