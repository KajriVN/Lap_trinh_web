<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa sản phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            max-width: 600px;
        }
        h2 {
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: bold;
        }
        input[type="text"], input[type="number"], textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 100px;
            resize: vertical;
        }
        input[type="file"] {
            margin-top: 5px;
        }
        button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #45a049;
        }
        .back-link {
            display: inline-block;
            margin-left: 10px;
            padding: 10px 20px;
            background-color: #999;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .current-image {
            margin-top: 10px;
        }
        .current-image img {
            max-width: 200px;
            height: auto;
        }
    </style>
</head>
<body>
    <h2>Sửa sản phẩm</h2>
    
    <form action="${pageContext.request.contextPath}/admin/product/edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="productid" value="${product.productId}">
        
        <div class="form-group">
            <label for="productname">Tên sản phẩm:</label>
            <input type="text" id="productname" name="productname" value="${product.productName}" required>
        </div>
        
        <div class="form-group">
            <label for="description">Mô tả:</label>
            <textarea id="description" name="description">${product.description}</textarea>
        </div>
        
        <div class="form-group">
            <label for="price">Giá:</label>
            <input type="number" id="price" name="price" step="0.01" value="${product.price}" required>
        </div>
        
        <div class="form-group">
            <label for="categoryid">Danh mục:</label>
            <select id="categoryid" name="categoryid" required>
                <c:forEach items="${listcate}" var="cate">
                    <option value="${cate.categoryId}" ${cate.categoryId == product.category.categoryId ? 'selected' : ''}>
                        ${cate.categoryname}
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <div class="form-group">
            <label for="image">Hình ảnh mới:</label>
            <input type="file" id="image" name="image" accept="image/*">
            <c:if test="${product.image != null}">
                <div class="current-image">
                    <p>Hình ảnh hiện tại:</p>
                    <img src="${pageContext.request.contextPath}/image?fname=${product.image}" alt="${product.productName}">
                </div>
            </c:if>
        </div>
        
        <div class="form-group">
            <label for="status">Trạng thái:</label>
            <select id="status" name="status">
                <option value="1" ${product.status == 1 ? 'selected' : ''}>Hoạt động</option>
                <option value="0" ${product.status == 0 ? 'selected' : ''}>Không hoạt động</option>
            </select>
        </div>
        
        <button type="submit">Cập nhật</button>
        <a href="${pageContext.request.contextPath}/admin/products" class="back-link">Quay lại</a>
    </form>
</body>
</html>
