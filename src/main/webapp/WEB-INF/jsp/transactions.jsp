<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Future Transaction Processor</title>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
</head>
<body>
	<h1>Upload Input File</h1>
	<form method="POST" action="/upload" enctype="multipart/form-data">
		<input type="file" name="file"><br> <input type="submit"
			value="Submit">
	</form>

	<hr>
	<h1>Download Daily Summary Report</h1>
	<form method="POST" action="/download/Output.csv">
		<label for="datepicker">Select Date:</label> <input type="text"
			name="date" id="datepicker"> <br> <input type="submit"
			value="Submit">
	</form>

</body>
</html>