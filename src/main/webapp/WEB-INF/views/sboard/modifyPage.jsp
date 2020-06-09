<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<style>
    .uploadResult {
        width: 100%;
        background-color: #aaa;
    }

    .uploadResult ul {
        display: flex;
        flex-flow: row;
        justify-content: center;
        align-items: center;
    }

    .uploadResult ul li {
        margin: 0 1em;
        list-style: none;
        padding: 1em;
        align-content: center;
        text-align: center;
    }

    .uploadResult ul li img {
        width: 100%;
        height: auto;
    }

    .uploadResult ul li img.fileIcon {
        width: 20px;
        margin: 2px 5px;
        vertical-align: text-top;
    }

    .uploadResult ul li span {
        color: #fff;
    }

    .uploadResult ul li button[type='button'] {
        position: absolute;
    }

    .uploadResult ul li button:hover {
        font-weight: bold;
        color: darkred;
    }

    .bigPictureWrapper {
        position: absolute;
        display: none;
        top: 0;
        z-index: 100;
        width: 100%;
        height: 100%;
        background-color: rgba(200, 200, 200, 0.5);
        justify-content: center;
        align-items: center;
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

<%@include file="../include/header.jsp" %>

<!-- Main content -->
<section class="content">
    <div class="bigPictureWrapper">
        <div class="bigPicture"></div>
    </div>
    <!-- end bigPictureWrapper -->
    <div class="row">
        <!-- left column -->
        <div class="col-md-12">
            <!-- general form elements -->
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">READ BOARD</h3>
                </div>
                <!-- /.box-header -->

                <form role="form" action="modifyPage" method="post">
                    <input type='hidden' name='page' value="${criteria.page}"/>
                    <input type='hidden' name='perPageNum' value="${criteria.perPageNum}"/>
                    <input type='hidden' name='searchType' value="${criteria.searchType}"/>
                    <input type='hidden' name='keyword' value="${criteria.keyword}"/>
                    <input id="requestMethod" type="hidden" name="_method" value="" disabled="disabled"/>
                    <div class="box-body">

                        <div class="form-group">
                            <label for="exampleInputEmail1">BNO</label> <input type="text"
                                                                               name='bno' class="form-control"
                                                                               value="${board.bno}"
                                                                               readonly="readonly">
                        </div>

                        <div class="form-group">
                            <label for="exampleInputEmail1">Title</label> <input type="text"
                                                                                 name='title' class="form-control"
                                                                                 value="${board.title}">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Content</label>
                            <textarea class="form-control" name="content" rows="3">${board.content}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">Writer</label> <input
                                type="text" name="writer" class="form-control"
                                value="${board.writer}">
                        </div>
                    </div>
                    <!-- /.box-body -->
                </form>


                <div class="box-footer">
                    <button type="submit" id="modifyBtn" class="btn btn-primary">SAVE</button>
                    <button type="submit" class="btn btn-warning">CANCEL</button>
                </div>
            </div>
            <!-- /.box -->
        </div>
        <!--/.col (left) -->
    </div>
    <!-- /.row -->
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">files</div>
                <div class="panel-body">
                    <div class="form-group uploadDiv">
                        <input type="file" name="uploadFile" multiple>
                    </div>
                    <div class="uploadResult">
                        <ul>

                        </ul>
                    </div>
                </div>
            </div>
            <!-- end panel -->
        </div>
        <!-- end col -->
    </div>
    <!-- end row -->
</section>
<!-- /.content -->

<!-- /.content-wrapper -->
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

    function showUploadedFile(uploadResultArr) {
        $(uploadResultArr).each(function (index, object) {
            let li = document.createElement("li");
            let typeValue = object.image ? "image" : "file";
            li.setAttribute("data-path", object.uploadPath);
            li.setAttribute("data-uuid", object.uuid);
            li.setAttribute("data-filename", object.fileName);
            li.setAttribute("data-type", typeValue);

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
        });
    }

    function showImage(filePath) {
        console.log("실행 확인 ?");
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

                if (confirm("첨부파일을 삭제하시겠습니까? ")) {
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
                }
            });
        });
    }

    function setAttachFileListInResult() {
        let form = document.querySelector('form[role=form]');

        document.querySelectorAll('.uploadResult ul li').forEach(function (object, index) {
            console.log(index);
            console.log(object);
            let inputFileName = document.createElement("input");
            inputFileName.setAttribute("type", "hidden");
            inputFileName.setAttribute("name", "attachList[" + index + "].fileName");
            inputFileName.setAttribute("value", object.dataset.filename);

            let inputUUID = document.createElement("input");
            inputUUID.setAttribute("type", "hidden");
            inputUUID.setAttribute("name", "attachList[" + index + "].uuid");
            inputUUID.setAttribute("value", object.dataset.uuid);

            let inputUploadPath = document.createElement("input");
            inputUploadPath.setAttribute("type", "hidden");
            inputUploadPath.setAttribute("name", "attachList[" + index + "].uploadPath");
            inputUploadPath.setAttribute("value", object.dataset.path);

            let inputFileType = document.createElement("input");
            inputFileType.setAttribute("type", "hidden");
            inputFileType.setAttribute("name", "attachList[" + index + "].fileType");
            inputFileType.setAttribute("value", object.dataset.type);

            form.append(inputFileName);
            form.append(inputUUID);
            form.append(inputUploadPath);
            form.append(inputFileType);
        });
    }

    $(document).ready(function () {

        const formObj = $("form[role='form']");
        const httpMethod = $("#requestMethod");
        const boardNo = ${board.bno};

        console.log(formObj);

        fetch("/sboard/getAttachList?bno=" + boardNo)
            .then(result => result.json()).then(list => {
            console.log(list);
            list.forEach(file => {
                let li = document.createElement("li");
                li.setAttribute("data-path", file.uploadPath);
                li.setAttribute("data-uuid", file.uuid);
                li.setAttribute("data-filename", file.fileName);
                li.setAttribute("data-type", file.fileType);
                if (file.fileType === "image") {
                    let uploadImagePath = file.uploadPath + "/" + file.uuid + "_" + file.fileName;
                    let fileCallPath = encodeURIComponent(file.uploadPath + "/s_" + file.uuid + "_" + file.fileName);
                    li.innerHTML = "<a href='javascript:showImage(\"" + uploadImagePath + "\")'><img src='/display?fileName=" + fileCallPath + "'/></a>"
                        + "<button type='button' class='btn btn-circle' data-file=\'" + fileCallPath + "' data-type='image'> <i class='fa fa-times'</i> </button>";
                } else {
                    fileCallPath = encodeURIComponent(file.uploadPath + "/" + file.uuid + "_" + file.fileName);
                    li.innerHTML = "<a href='/download?fileName=" + fileCallPath + "'><img class='fileIcon' src='/resources/dist/img/attach.png'/>" + file.fileName + "</a>"
                        + "<button type='button' class='btn btn-circle' data-file=\'" + fileCallPath + "' data-type='file'> <i class='fa fa-times'</i> </button>";
                }
                document.querySelector('.uploadResult ul').append(li);
            });

            setDeleteFunction();
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
            setTimeout(() => {
                document.querySelector('.bigPictureWrapper').style.display = "none";
                bigPicture.innerHTML = ""; // init
            }, 500);
        });

        $(".btn-warning").on("click", function () {
            self.location = "/sboard/list?page=${criteria.page}" +
                "&perPageNum=${criteria.perPageNum} " +
                "&searchType=${criteria.searchType} " +
                "&keyword=${criteria.keyword}";
        });

        $(".btn-primary").on("click", function () {
            setAttachFileListInResult();
            httpMethod.removeAttr("disabled");
            httpMethod.attr("value", "put");
            formObj.submit();
        });

    });
</script>

<%@include file="../include/footer.jsp" %>
