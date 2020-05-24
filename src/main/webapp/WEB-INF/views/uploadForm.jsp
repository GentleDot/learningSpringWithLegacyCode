<%--
  Project : spring-code-project
  User: gentledot
  Date: 2020-05-24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Form upload example</title>
</head>
<body>
    <div>
        <form action="uploadFormAction" method="post" enctype="multipart/form-data">

            <input type='file' name='uploadFile' multiple>

            <button>Submit</button>

        </form>
    </div>
</body>
</html>