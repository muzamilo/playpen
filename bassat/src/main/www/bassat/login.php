<?PHP

    session_start();
    require('config.php');
    require($include.'lang.inc');

    $connect = mysql_connect($db_host, $db_user, $db_password) or die (mysql_error());
    $db = mysql_select_db($db_name) or die (mysql_error());


    if (isSet($_POST['login'])) {
        $email = trim(strip_tags($_POST[email]));
        $password = strip_tags($_POST[password]);
        $query = "SELECT * FROM user WHERE email = '$email' and password = '$password'";
        $result = mysql_query($query) or die(mysql_error());
        $nResults = mysql_num_rows($result);
        if ($nResults == 0) {
            $error_message = $invalid_credentials;
            require($include.'login.inc');
        } else {
            header("Location: dashboard.php");
        }
    }

?>