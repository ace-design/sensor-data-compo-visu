map(svg,dataname,chartname,icons) ::= <<
\$(window).ready(function(){load_svg("$svg$","$chartname$_div",$dataname$,put_sensors,{$icons; separator=","$});});
>>