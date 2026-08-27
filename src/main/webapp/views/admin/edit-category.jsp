<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Category</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input[type=text], input[type=file] { width: 300px; padding: 6px; margin-top: 4px; }
        .radio-group { margin-top: 6px; }
        input[type=submit] { margin-top: 16px; padding: 8px 20px; background: #27ae60; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        .preview { margin-top: 8px; }
        a { display: inline-block; margin-top: 12px; }
    </style>
</head>
<body>
<h2>Sửa danh mục</h2>
<form action="<c:url value='/admin/category/update'/>" method="post" enctype="multipart/form-data">

    <%-- Hidden field carries the PK --%>
    <input type="hidden" name="categoryid" value="${cate.categoryId}"/>

    <label for="categoryname">Tên Category:</label>
    <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}" required/>

    <label for="images">Link ảnh (URL):</label>
    <input type="text" id="images" name="images" value="${cate.images}"/>

    <%-- Preview current image --%>
    <div class="preview">
        <c:choose>
            <c:when test="${not empty cate.images and cate.images.substring(0,5) == 'https'}">
                <c:url value="${cate.images}" var="imgUrl"/>
            </c:when>
            <c:otherwise>
                <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
            </c:otherwise>
        </c:choose>
        <img height="100" width="130" src="${imgUrl}" alt="Ảnh hiện tại"
             onerror="this.style.display='none'"/><br/>
    </div>

    <label for="images1">Đổi ảnh (upload):</label>
    <input type="file" id="images1" name="images1" accept="image/*"/>

    <label>Trạng thái:</label>
    <div class="radio-group">
        <input type="radio" id="ston"  name="status" value="1" ${cate.status == 1 ? 'checked' : ''}>
        <label for="ston"  style="display:inline">Hoạt động</label>
        <input type="radio" id="stoff" name="status" value="0" ${cate.status != 1 ? 'checked' : ''}>
        <label for="stoff" style="display:inline">Khóa</label>
    </div>

    <input type="submit" value="Cập nhật"/>
</form>
<a href="<c:url value='/admin/categories'/>">← Quay lại danh sách</a>
</body>
</html>
