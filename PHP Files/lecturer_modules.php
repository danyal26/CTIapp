<?php

require "connection.php";

$lecturer_id = $_POST['lecturer_id'];
$day = $_POST['day'];

// $lecturer_id = 'LEC2000_001';
// $day = 'Tuesday';

$qry = "select distinct Modules.* from Modules
left join Class_Timetable on Modules.module_id = Class_Timetable.module_id
left join Class_Sessions on Class_Timetable.session_id = Class_Sessions.session_id
where Modules.lecturer_id = '$lecturer_id' and Class_Sessions.day = '$day'";

$qryresult = mysqli_query($connect, $qry);

$response = array();

while ($row = mysqli_fetch_array($qryresult)) {

      array_push($response,array("module_id"=>$row[0], "module_name"=>$row[1], "module_length"=>$row[2], "program_id"=>$row[3], "lecturer_id"=>$row[4], "nqf_level"=>$row[5], "cost"=>$row[6], "program_level"=>$row[7]));
}

echo json_encode(array("lecturer_modules"=>$response));

mysqli_close($connect);

?>
