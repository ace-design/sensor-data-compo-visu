highchart(options,chartname,graphs,title) ::= <<
\$(function () {
    // Radialize the colors
    Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
        return {
            radialGradient: {
                cx: 0.5,
                cy: 0.3,
                r: 0.7
            },
            stops: [
                [0, color],
                [1, Highcharts.Color(color).brighten(-0.3).get("rgb")] // darken
                ]
        };
    });

    // Build the chart
    \$("#$chartname$_div").highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: "$title$"
        },
        $options; separator="\n"$
        legend: {
            layout: "vertical",
            align: "right",
            verticalAlign: "middle",
            borderWidth: 0
        },
        series: [{
            $graphs; separator="\n"$
        }]
    });
});
>>