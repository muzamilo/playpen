<?PHP

    session_start();
    require('config.php');
    require($include.'lang.inc');

    $connect = mysql_connect($db_host, $db_user, $db_password) or die (mysql_error());
    $db = mysql_select_db($db_name) or die (mysql_error());

    require($include.'login.inc');
?>