graph(chartname,graphname,seriename,graphspe) ::= <<
// Graph $graphname$
var $graphname$;
$graphname$ = new AmCharts.AmGraph();
$graphname$.valueField = "$seriename$";
$graphname$.connect = true;
$graphname$.title = "$seriename$";
$graphspe$
$chartname$.addGraph($graphname$);


>>