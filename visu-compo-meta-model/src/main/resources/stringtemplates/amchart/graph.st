graph(chartname,graphname,seriename,graphspe) ::= <<
// Graph $graphname$
var $graphname$;
$graphname$ = new AmCharts.AmGraph();
$graphname$.valueField = "$seriename$";
$graphname$.connect = false;
$graphname$.title = "$seriename$";
$graphspe$
$chartname$.addGraph($graphname$);


>>