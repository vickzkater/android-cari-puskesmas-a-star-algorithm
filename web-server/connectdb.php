<?php 

$server		= "YOUR_HOST"; // adjust the server name
$user		= "YOUR_DBUSER"; // adjust the username
$password	= "YOUR_DBPASS"; // adjust the password
$database	= "YOUR_DBNAME"; // adjust the target databese

$con = mysqli_connect($server, $user, $password, $database);
if (mysqli_connect_errno()) {
	echo "Failed to connect MySQL: " . mysqli_connect_error();
}

?>