<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            color: #333;
        }
        .add-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .add-btn:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .action-links a {
            margin-right: 10px;
            text-decoration: none;
        }
        .edit-link {
            color: #2196F3;
        }
        .delete-link {
            color: #f44336;
        }
        img {
            max-width: 100px;
            height: auto;
        }
    </style>
</head>
<body>
    <h2>Quản lý sản phẩm</h2>
    
    <a href="${pageContext.request.contextPath}/admin/product/add" class="add-btn">Thêm sản phẩm mới</a>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Danh mục</th>
                <th>Hình ảnh</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listproduct}" var="product">
                <tr>
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.price} đ</td>
                    <td>${product.category.categoryname}</td>
                    <td>
                        <c:if test="${product.image != null}">
                            <img src="${pageContext.request.contextPath}/image?fname=${product.image}" alt="${product.productName}">
                        </c:if>
                    </td>
                    <td>${product.status == 1 ? 'Hoạt động' : 'Không hoạt động'}</td>
                    <td class="action-links">
                        <a href="${pageContext.request.contextPath}/admin/product/edit?id=${product.productId}" class="edit-link">Sửa</a>
                        <a href="${pageContext.request.contextPath}/admin/product/delete?id=${product.productId}" 
                           class="delete-link" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
