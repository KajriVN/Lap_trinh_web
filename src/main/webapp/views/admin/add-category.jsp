<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Category</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input[type=text], input[type=file] { width: 300px; padding: 6px; margin-top: 4px; }
        .radio-group { margin-top: 6px; }
        input[type=submit] { margin-top: 16px; padding: 8px 20px; background: #4a90e2; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        a { display: inline-block; margin-top: 12px; }
    </style>
</head>
<body>
<h2>Thêm danh mục</h2>
<form action="<c:url value='/admin/category/insert'/>" method="post" enctype="multipart/form-data">

    <label for="categoryname">Tên Category:</label>
    <input type="text" id="categoryname" name="categoryname" required/>

    <label for="images">Link ảnh (URL):</label>
    <input type="text" id="images" name="images" placeholder="https://..."/>

    <label for="images1">Hoặc upload ảnh:</label>
    <input type="file" id="images1" name="images1" accept="image/*"/>

    <label>Trạng thái:</label>
    <div class="radio-group">
        <input type="radio" id="ston"  name="status" value="1" checked>
        <label for="ston" style="display:inline">Hoạt động</label>
        <input type="radio" id="stoff" name="status" value="0">
        <label for="stoff" style="display:inline">Khóa</label>
    </div>

    <input type="submit" value="Thêm"/>
</form>
<a href="<c:url value='/admin/categories'/>">← Quay lại danh sách</a>
</body>
</html>
