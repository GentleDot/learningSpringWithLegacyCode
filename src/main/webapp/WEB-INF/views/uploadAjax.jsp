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

        .bigPictureWrapper {
            display: none;
            position: absolute;
            top: 0;
            width: 100%;
            height: 100%;
            justify-content: center;
            align-items: center;
            background: rgba(220, 220, 220, 0.5);
            z-index: 100;
        }

        .bigPicture {
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .bigPicture img {
            width: 600px;
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

<div class="bigPictureWrapper">
    <div class="bigPicture"></div>
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

    function showImage(filePath) {
        console.log("이미지 경로 : " + filePath);
        let bigWrapper = document.querySelector('.bigPictureWrapper');
        let bigPicture = document.querySelector('.bigPictureWrapper .bigPicture');

        // bigWrapper.css({display: 'flex'}).show();
        bigWrapper.style.display = "flex";

        // bigPicture.html("<img src='/display?filename=" + encodeURI(filePath) + "'/>")
        let displayImage = document.createElement("img");
        displayImage.setAttribute("src", "/display?fileName=" + filePath);
        bigPicture.appendChild(displayImage);
        // bigPicture.animate({width: '100%', height: '100%'}, 1000);
        bigPicture.animate([{width: '0', height: '0'}, {width: '100%', height: '100%'}], {duration: 1000});
    }

    function setDeleteFunction() {
        let uploadResultList = document.querySelector('.uploadResult ul').querySelectorAll('span');
        uploadResultList.forEach(function (item) {
            item.addEventListener('click', function (e) {
                console.log("클릭했는데 반응이 없어?");
                console.log("this는 뭐냐? " + this);
                console.log("e.currentTarget은? " + e.currentTarget);
                console.log("this와 e.currentTarget은 같다? " + e.currentTarget === this);

                /*let targetFile = this.data("file");
                let type = this.data("type");*/
                let targetFile = this.dataset.file;
                let type = this.dataset.type;

                console.log("targetFile : " + targetFile + " / type : " + type);
                let requestData = {
                    fileName: targetFile,
                    type: type
                };

                let responsePromise = fetch('/deleteFile', {
                    body: JSON.stringify(requestData),
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                responsePromise
                    .then((data) => {
                        console.log(data);
                        console.log(data.text());
                    })
                    .catch(error => console.error(error));
            });
        });
    }

    document.addEventListener('DOMContentLoaded', () => {
        var uploadResult = document.querySelector('.uploadResult ul');

        function showUploadedFile(uploadResultArr) {
            $(uploadResultArr).each(function (index, object) {
                let li = document.createElement("li");
                let fileCallPath;
                if (object.image) {
                    fileCallPath = encodeURIComponent(object.uploadPath + "/s_" + object.uuid + "_" + object.fileName);
                    // let uploadImagePath = object.uploadPath + "\\" + object.uuid + "_" + object.filename;
                    let uploadImagePath = object.uploadPath + "/" + object.uuid + "_" + object.fileName;
                    li.innerHTML = "<a href='javascript:showImage(\"" + uploadImagePath + "\")'><img src='/display?fileName=" + fileCallPath + "'/></a>"
                        + "<span data-file=\'" + fileCallPath + "' data-type='image'> x </span>";

                } else {
                    fileCallPath = encodeURIComponent(object.uploadPath + "/" + object.uuid + "_" + object.fileName);
                    li.innerHTML = "<a href='/download?fileName=" + fileCallPath + "'><img class='fileIcon' src='/resources/dist/img/attach.png'/>" + object.fileName + "</a>"
                        + "<span data-file=\'" + fileCallPath + "' data-type='file'> x </span>";
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
                    setDeleteFunction();

                    alert("Uploaded!");
                }
            });
        });

        document.querySelector('.bigPictureWrapper').addEventListener("click", (event) => {
            let bigPicture = document.querySelector('.bigPictureWrapper .bigPicture');
            bigPicture.animate([{width: '100%', height: '100%'}, {width: '0', height: '0'}], {duration: 1000});
            this.setTimeout(() => {
                document.querySelector('.bigPictureWrapper').style.display = "none";
                bigPicture.innerHTML = ""; // init
            }, 500);
        });
    });

</script>
</html>