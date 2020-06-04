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
    <style>
        .uploadResult {
            width: 100%;
            background-color: #a0a0a0;
        }

        .uploadResult ul {
            line-height: 2em;
            display: flex;
            flex-flow: row;
            justify-content: center;
            align-items: center;
        }

        .uploadResult ul li {
            list-style: none;
            padding: 0 10px;
        }

        .uploadResult ul li img {
            width: 4em;
            height: auto;
            vertical-align: middle;
        }

        .uploadResult ul li img.fileIcon {
            width: 20px;
            margin: 2px 5px;
            vertical-align: text-top;
        }
    </style>
</head>
<body>
<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
    <button type="button" id="uploadBtn">업로드!</button>
</div>

<div class="uploadResult">
    <ul>

    </ul>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>
    const regExp = new RegExp("(.*?)\.(exe|sh|zip|alz|rar|7z)$");
    const maxSize = 5242880; // 5MB

    function checkExtension(fileName, fileSize) {
        if (regExp.test(fileName)) {
            alert("선택한 파일은 업로드할 수 없습니다.");
            return false;
        } else if (fileSize > maxSize) {
            alert("업로드 파일은 5MB 내외만 허용됩니다.");
            return false;
        }

        return true;
    }

    document.addEventListener('DOMContentLoaded', () => {
        var uploadResult = document.querySelector('.uploadResult ul');

        function showUploadedFile(uploadResultArr) {
            $(uploadResultArr).each(function (index, object) {
                let li = document.createElement("li");
                let fileCallPath;
                if (object.image) {
                    fileCallPath = encodeURIComponent(object.uploadPath + "/s_" + object.uuid + "_" + object.fileName);
                    li.innerHTML = "<img src='/display?fileName=" + fileCallPath + "'/>";

                } else {
                    fileCallPath = encodeURIComponent(object.uploadPath + "/" + object.uuid + "_" + object.fileName);
                    li.innerHTML = "<a href='/download?fileName=" + fileCallPath + "'><img class='fileIcon' src='/resources/dist/img/attach.png'/>" + object.fileName + "</a>";
                }

                uploadResult.append(li);
            });
        }

        document.querySelector('#uploadBtn').addEventListener('click', event => {
            let formData = new FormData;
            let inputFiles = document.querySelector('input[name=uploadFile]');
            let files = inputFiles.files;

            let length = files.length;
            console.log("업로드 파일 개수 확인 : " + length);

            if (length <= 0) {
                return false;
            }

            for (let i = 0; i < length; i++) {
                if (!checkExtension(files[i].name, files[i].size)) {
                    return false;
                }
                formData.append("uploadFile", files[i]);
            }

            console.log("formdata : " + formData);

            $.ajax({
                url: '/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                dataType: 'JSON',
                success: result => {
                    console.log(result);

                    showUploadedFile(result);

                    inputFiles.value = '';
                    alert("Uploaded!");
                }
            });
        });
    });

</script>
</html>