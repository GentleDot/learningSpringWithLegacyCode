<%--
  Project : spring-code-project
  User: gentledot
  Date: 2020-05-24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>upload with Ajax example</title>
</head>
<body>
<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
    <button type="button" id="uploadBtn">업로드!</button>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        document.querySelector('#uploadBtn').addEventListener('click', event => {
            let formData = new FormData;
            let inputFiles = document.querySelector('input[name=uploadFile]');
            let files = inputFiles.files;

            console.log("input[file] 확인 : " + inputFiles);

            let length = files.length;
            console.log("업로드 파일 개수 확인 : " + length);
            for (let i = 0; i < length; i++) {
                formData.append("uploadFile", files[i]);
            }

            console.log("formdata : " + formData);

            $.ajax({
                url: '/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                success: result => {
                    console.log(result);
                    alert("Uploaded!");
                }
            });

            /*const request = new XMLHttpRequest();
            request.open('POST', '/uploadAjaxAction', true);
            request.onreadystatechange = function () {
                if (request.readyState === 4) {
                    if (request.status === 200){
                        success();
                    } else {

                    }
                }
            };
            request.send(null);*/
        });
    })
</script>
</html>