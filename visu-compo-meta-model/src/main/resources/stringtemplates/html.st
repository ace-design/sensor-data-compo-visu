html(imports,data,chartsscript,chartsbody) ::= <<
<!DOCTYPE HTML>
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
			<title>Test</title>
			<link rel="stylesheet" href="style.css" type="text/css">
			$imports; separator="\n"$
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