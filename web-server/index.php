<?php 
	include "connectdb.php";
	
	$from = "";
	if(isset($_GET['from'])){
		// mempersempit pencarian
		if(isset($_GET['target'])){
			$target = $_GET['target'];
			$pos = strpos($target, " (");
			$target = substr($target, 0, $pos);
			$sql = "select a.*, b.nama as nama_bagian from puskesmas a left join bagian b on a.`bagian` = b.id where a.nama LIKE '%".$target."%'";
		}else{
			$sql = "select a.*, b.nama as nama_bagian from puskesmas a left join bagian b on a.`bagian` = b.id"; // query semua data
		}
	}else{
		$sql = "select a.*, b.nama as nama_bagian from puskesmas a left join bagian b on a.`bagian` = b.id limit 20";
	}

	$query = mysqli_query($con, $sql);
	
	$json = '{"tempat": [';
	
	// create looping dech array in fetch
	while ($row = mysqli_fetch_array($query)){

	// quotation marks (") are not allowed by the json string, we will replace it with the` character
	// strip_tag serves to remove html tags on strings
		$char ='"';

		$json .= 
		'{
			"id":"'.str_replace($char,'`',strip_tags($row['id'])).'", 
			"nama":"'.str_replace($char,'`',strip_tags($row['nama'])).'",
			"lat":"'.str_replace($char,'`',strip_tags($row['lat'])).'",
			"lng":"'.str_replace($char,'`',strip_tags($row['lng'])).'",
			"bagian":"'.str_replace($char,'`',strip_tags($row['nama_bagian'])).'"
		},';
	}

	// omitted commas at the end of the array
	$json = substr($json,0,strlen($json)-1);

	$json .= ']}';

	// print json
	echo $json;
	
	mysqli_close($con);
	
?>