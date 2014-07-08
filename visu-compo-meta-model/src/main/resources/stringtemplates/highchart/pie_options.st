pie_options() ::= <<
tooltip: {
    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
},
plotOptions: {
    pie: {
        allowPointSelect: true,
        cursor: 'pointer',
        dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
            style: {
                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
            },
            connectorColor: 'silver'
        }
    }
},
>>