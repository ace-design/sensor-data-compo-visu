html(data,chartsscript,chartsbody) ::= <<
<!DOCTYPE HTML>
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html"; charset="utf-8"/>
			<title>Test</title>
			<link rel="stylesheet" href="style.css" type="text/css">
			<script src="http://www.amcharts.com/lib/amcharts.js" type="text/javascript"></script>
			<script type="text/javascript">

				$data$
	
				$chartsscript$
				
			</script>
		</head>
		<body>
			$chartsbody$
		</body>
	</html>
>>