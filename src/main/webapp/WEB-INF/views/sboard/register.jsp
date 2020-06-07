<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="../include/header.jsp" %>
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

    .uploadResult ul li button[type='button'] {
        display: inline-block;
        width: 1px;
        line-height: 1em;
        vertical-align: top;
    }

    .uploadResult ul li button:hover {
        font-weight: bold;
        color: darkred;
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

<!-- Main content -->
<section class="content">
    <div class="row">
        <!-- left column -->
        <form role="form" action="register" method="post">
            <div class="col-md-12">
                <!-- general form elements -->

                <div class="box box-primary">
                    <div class="box-header">
                        <h3 class="box-title">REGISTER BOARD</h3>
                    </div>
                    <!-- /.box-header -->


                    <div class="box-body">
                        <div class="form-group">
                            <label for="exampleInputEmail1">Title</label>
                            <input type="text"
                                   name='title' class="form-control" placeholder="Enter Title">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Content</label>
                            <textarea class="form-control" name="content" rows="3"
                                      placeholder="Enter ..."></textarea>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">Writer</label>
                            <input type="text"
                                   name="writer" class="form-control" placeholder="Enter Writer">
                        </div>
                    </div>
                    <!-- /.box-body -->

                    <div class="box-footer">
                        <button type="submit" id="registNewBoardBtn" class="btn btn-primary">등록</button>
                    </div>
                </div>


                <!-- /.box -->
            </div>
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">첨부파일</div>
                    <div class="panel-body">
                        <div class="form-group uploadDiv">
                            <input type="file" name="uploadFile" multiple>
                        </div>
                        <div class="uploadResult">
                            <ul>

                            </ul>
                        </div>
                        <!-- end uploadResult -->
                        <div class="bigPictureWrapper">
                            <div class="bigPicture"></div>
                        </div>
                        <!-- end bigPictureWrapper -->
                    </div>
                    <!-- end panel-body -->
                </div>
                <!-- end panel -->
            </div>
            <!-- end col-lg-12 -->
        </form>
        <!--/.col (left) -->

    </div>
    <!-- /.row -->
</section>
<!-- /.content -->

<%@include file="../include/footer.jsp" %>

<script>
    const regExp = new RegExp("(.*?)\.(exe|sh|zip|alz|rar|7z)$");
    const maxSize = 5242880; // 5MB

    function addMultiEventListener(element, events, eventFunction) {
        events.split(' ').forEach(event => element.addEventListener(event, eventFunction));
    }

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

    function showUploadedFile(uploadResultArr) {
        let form = document.querySelector('form[role=form]');
        $(uploadResultArr).each(function (index, object) {
            let li = document.createElement("li");
            let typeValue = object.image ? "image" : "file";
            li.setAttribute("data-path", object.uploadPath);
            li.setAttribute("data-uuid", object.uuid);
            li.setAttribute("data-filename", object.fileName);
            li.setAttribute("data-type", typeValue);

            let inputFileName = document.createElement("input");
            inputFileName.setAttribute("type", "hidden");
            inputFileName.setAttribute("name", "attachList[" + index + "].fileName");
            inputFileName.setAttribute("value", object.fileName);

            let inputUUID = document.createElement("input");
            inputUUID.setAttribute("type", "hidden");
            inputUUID.setAttribute("name", "attachList[" + index + "].uuid");
            inputUUID.setAttribute("value", object.uuid);

            let inputUploadPath = document.createElement("input");
            inputUploadPath.setAttribute("type", "hidden");
            inputUploadPath.setAttribute("name", "attachList[" + index + "].uploadPath");
            inputUploadPath.setAttribute("value", object.uploadPath);

            let inputFileType = document.createElement("input");
            inputFileType.setAttribute("type", "hidden");
            inputFileType.setAttribute("name", "attachList[" + index + "].fileType");
            inputFileType.setAttribute("value", typeValue);

            let fileCallPath;
            if (object.image) {
                fileCallPath = encodeURIComponent(object.uploadPath + "/s_" + object.uuid + "_" + object.fileName);
                // let uploadImagePath = object.uploadPath + "\\" + object.uuid + "_" + object.filename;
                let uploadImagePath = object.uploadPath + "/" + object.uuid + "_" + object.fileName;
                li.innerHTML = "<a href='javascript:showImage(\"" + uploadImagePath + "\")'><img src='/display?fileName=" + fileCallPath + "'/></a>"
                    + "<button type='button' class='btn btn-circle' data-file=\'" + fileCallPath + "' data-type='image'> <i class='fa fa-times'</i> </button>";

            } else {
                fileCallPath = encodeURIComponent(object.uploadPath + "/" + object.uuid + "_" + object.fileName);
                li.innerHTML = "<a href='/download?fileName=" + fileCallPath + "'><img class='fileIcon' src='/resources/dist/img/attach.png'/>" + object.fileName + "</a>"
                    + "<button type='button' class='btn btn-circle' data-file=\'" + fileCallPath + "' data-type='file'> <i class='fa fa-times'</i> </button>";
            }

            document.querySelector('.uploadResult ul').append(li);
            form.append(inputFileName);
            form.append(inputUUID);
            form.append(inputUploadPath);
            form.append(inputFileType);
        });
    }

    function showImage(filePath) {
        let bigWrapper = document.querySelector('.bigPictureWrapper');
        let bigPicture = document.querySelector('.bigPictureWrapper .bigPicture');

        if (bigPicture.hasChildNodes()) {
            return;
        }

        console.log("이미지 경로 : " + filePath);
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
        let uploadResultList = document.querySelector('.uploadResult ul').querySelectorAll('button');
        uploadResultList.forEach(function (item) {
            item.addEventListener('click', function (e) {
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
                    .then(data => data.text()).then(result => {
                    if (result === "deleted") {
                        this.parentNode.remove();
                    }
                }).catch(error => console.error(error));
            });
        });
    }

    document.addEventListener('DOMContentLoaded', () => {
        let panelArea = document.querySelector('.panel');

        addMultiEventListener(panelArea, 'dragenter dragover', function (e) {
            e.preventDefault();
        });

        panelArea.addEventListener('drop', function (e) {
            e.preventDefault();

            let files = e.dataTransfer.files;
            console.log("파일 확인 : " + files);
            console.log("파일 확인[0] : " + files[0]);
        });

        document.querySelector('#registNewBoardBtn').addEventListener('click', function (e) {
            e.preventDefault();
            console.log("제출 버튼을 클릭하였음!");

            document.querySelector('form[role=form]').submit();
        });

        document.querySelector('input[name=uploadFile]').addEventListener('change', function (e) {
            let formData = new FormData;
            let files = this.files;
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
                    this.value = '';
                    setDeleteFunction();
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
