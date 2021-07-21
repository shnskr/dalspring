<%--
  Created by IntelliJ IDEA.
  User: DAL
  Date: 2021-07-20
  Time: 오후 6:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <style>
        .uploadResult {
            width: 100%;
            background-color: gray;
        }

        .uploadResult ul {
            display: flex;
            flex-flow: row;
            justify-content: center;
            align-items: center;
        }

        .uploadResult ul li {
            list-style: none;
            padding: 10px;
        }

        .uploadResult ul li img {
            width: 20px;
        }
    </style>
</head>
<body>

<h1>Upload with Ajax</h1>

<div class="uploadDiv">
    <input type="file" name="uploadFile" multiple>
</div>

<button id="uploadBtn">Upload</button>

<div class="uploadResult">
    <ul>

    </ul>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>

<script>
    $(document).ready(function () {
        var cloneObj = $(".uploadDiv").clone();

        $("#uploadBtn").on("click", function (e) {
            var formData = new FormData();
            var inputFile = $("input[name='uploadFile']");
            var files = inputFile[0].files;

            console.log(files);

            // add filedate to formdata
            for (var i = 0; i < files.length; i++) {
                if (!checkExtension(files[i].name, files[i].size)) {
                    return false;
                }
                formData.append("uploadFile", files[i]);
            }

            $.ajax({
                url: '/uploadAjaxAction',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                dataType: 'json',
                success: function (result) {
                    console.log(result);

                    showUploadFile(result);

                    $(".uploadDiv").html(cloneObj.html());
                }
            });
        });

        var regex = RegExp("(.*?)\.(exe|sh|zip|alz)$");
        var maxSize = 5242880; // 5MB

        function checkExtension(fileName, fileSize) {
            if (fileSize >= maxSize) {
                alert("파일 사이즈 초과");
                return false;
            }

            if (regex.test(fileName)) {
                alert("해당 종류의 파일은 업로드 할 수 없습니다.");
                return false;
            }

            return true;
        }

        var uploadResult = $(".uploadResult ul");

        function showUploadFile(uploadResultArr) {
            var str = "";

            $(uploadResultArr).each(function (i, obj) {

                if (!obj.image) {
                    var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);

                    str += "<li><a href='/download?fileName=" + fileCallPath + "'><img src='/resources/img/attach.png'>"
                        + obj.fileName + "</a></li>";
                } else {
                    // str += "<li>" + obj.fileName + "</li>";

                    var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);

                    str += "<li><img src='/display?fileName=" + fileCallPath + "'></li>";
                }

            });

            uploadResult.append(str);
        }
    });
</script>

</body>
</html>
