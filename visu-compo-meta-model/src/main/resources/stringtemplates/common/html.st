html(imports,data,chartsscript,chartsbody) ::= <<
<!DOCTYPE HTML>
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
			<title>Test</title>
			$imports; separator="\n"$
			<script type="text/javascript">

				$data; separator="\n"$
	
				$chartsscript; separator="\n"$
				
			</script>
		</head>
		<body>
			$chartsbody$
		</body>
	</html>
>>