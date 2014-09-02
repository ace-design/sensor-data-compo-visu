map(svg,dataname,chartname,icons) ::= <<
load_svg("$svg$","$chartname$_div",$dataname$,put_sensors,{$icons; separator=","$});
>>