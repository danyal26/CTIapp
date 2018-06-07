<?php

require "connection.php";

$qry = "select Departments.department_name, News.message, News.date_posted, News.time_posted from News left join Departments on Departments.department_id = News.department_id";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("department_name"=>$row[0], "message"=>$row[1], "date_posted"=>$row[2], "time_posted"=>$row[3]));
}

echo json_encode(array("news"=>$response));

mysqli_close($connect);

?>
