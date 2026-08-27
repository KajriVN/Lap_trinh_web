<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Category</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: center; }
        th { background: #4a90e2; color: #fff; }
        tr:nth-child(even) { background: #f2f2f2; }
        a.btn { padding: 4px 10px; border-radius: 4px; text-decoration: none; color: #fff; }
        a.edit { background: #27ae60; }
        a.del  { background: #e74c3c; }
        .active { color: green; font-weight: bold; }
        .locked { color: red; }
    </style>
</head>
<body>
<h2>Danh sách Category</h2>
<a href="<c:url value='/admin/category/add'/>">+ Thêm danh mục</a>
<br><br>
<table>
    <tr>
        <th>STT</th>
        <th>Ảnh</th>
        <th>Tên Category</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
    </tr>
    <c:forEach items="${listcate}" var="cate" varStatus="STT">
    <tr>
        <td>${STT.index + 1}</td>
        <td>
            <c:choose>
                <c:when test="${not empty cate.images and cate.images.substring(0,5) == 'https'}">
                    <c:url value="${cate.images}" var="imgUrl"/>
                </c:when>
                <c:otherwise>
                    <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
                </c:otherwise>
            </c:choose>
            <img height="80" width="100" src="${imgUrl}" alt="${cate.categoryname}" onerror="this.style.display='none'"/>
        </td>
        <td>${cate.categoryname}</td>
        <td>
            <c:if test="${cate.status == 1}"><span class="active">Hoạt động</span></c:if>
            <c:if test="${cate.status != 1}"><span class="locked">Khóa</span></c:if>
        </td>
        <td>
            <a class="btn edit" href="<c:url value='/admin/category/edit?id=${cate.categoryId}'/>">Sửa</a>
            &nbsp;
            <a class="btn del"
               href="<c:url value='/admin/category/delete?id=${cate.categoryId}'/>"
               onclick="return confirm('Xóa danh mục này?')">Xóa</a>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
