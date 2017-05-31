<!DOCTYPE HTML>
<html>
	<head>
		<title>PNUIPS</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
	</head>
	<body>

		<!-- Header -->
			<header id="header">
				<h1>PNUIPS For Users</h1><br>

			</header>

		<!-- Signup Form -->
			<form id="signup-form" method="post" action="./user/dologin.jsp">
				<input type="email" name="email" placeholder="Email Address" />
				<input type="password" name="pw" placeholder="Password" />
				<input type="submit" value="login" />
			</form>
			<p>If you are not user, <a href="./user/register.jsp">sign up</a>.</p>
			<p>*caution* data reset using user.csv and item.csv, <a href="./datareset.jsp">click</a>.</p>

		<!-- Footer -->
			<footer id="footer">
				<p>If you are seller, <a href="./seller/sellerStart.jsp">here</a>.</p>
				<p>If you are admin, <a href="./admin/adminStart.jsp">here</a>.</p>
			</footer>

		<!-- Scripts -->
			<script src="assets/js/main.js"></script>

	</body>
</html>