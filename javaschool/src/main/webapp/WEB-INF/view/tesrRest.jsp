<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>REST</title>
    <script src="/resources/js/jquery.min.js"></script>
</head>
<body>

<button type="button" onclick="RestGet()">Метод GET</button>
<button type="button" onclick="RestPost()">Метод POST</button>
<script>
    var RestGet = function() {
        $.ajax({
            type: 'GET',
            url:   + '/' + Date.now(),
            dataType: 'json',
            async: true,
            success: function(result) {
                alert('Время: ' + result.time
                    + ', сообщение: ' + result.message);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    };


    var RestPost = function() {
        $.ajax({
            type: 'POST',
            url:  prefix,
            dataType: 'json',
            async: true,
            success: function(result) {
                alert('Время: ' + result.time
                    + ', сообщение: ' + result.message);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    };
</script>
</body>
</html>
