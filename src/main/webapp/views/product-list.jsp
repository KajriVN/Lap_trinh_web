<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sản phẩm</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #4CAF50;
            color: white;
            padding: 20px;
            text-align: center;
        }
        .nav {
            background-color: #333;
            overflow: hidden;
        }
        .nav a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
        }
        .nav a:hover {
            background-color: #ddd;
            color: black;
        }
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 20px;
        }
        h2 {
            color: #333;
            margin: 20px 0;
        }
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .product-card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.3s;
            cursor: pointer;
        }
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .product-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            background-color: #f0f0f0;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .product-image img {
            max-width: 100%;
            max-height: 100%;
        }
        .product-info {
            padding: 15px;
        }
        .product-name {
            font-size: 16px;
            font-weight: bold;
            color: #333;
            margin-bottom: 8px;
            min-height: 40px;
        }
        .product-price {
            color: #4CAF50;
            font-size: 18px;
            font-weight: bold;
        }
        .no-image {
            width: 100%;
            height: 200px;
            background-color: #e0e0e0;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #999;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin: 30px 0;
            gap: 10px;
        }
        .pagination a {
            padding: 8px 16px;
            background-color: white;
            color: #333;
            text-decoration: none;
            border-radius: 4px;
            border: 1px solid #ddd;
        }
        .pagination a:hover {
            background-color: #4CAF50;
            color: white;
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Danh sách sản phẩm</h1>
    </div>
    
    <div class="nav">
        <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
        <a href="${pageContext.request.contextPath}/product">Sản phẩm</a>
        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
    </div>
    
    <div class="container">
        <h2>Tất cả sản phẩm</h2>
        
        <div class="product-grid">
            <c:forEach items="${listproduct}" var="product">
                <div class="product-card" onclick="location.href='${pageContext.request.contextPath}/product/detail?id=${product.productId}'">
                    <div class="product-image">
                        <c:choose>
                            <c:when test="${product.image != null}">
                                <img src="${pageContext.request.contextPath}/image?fname=${product.image}" alt="${product.productName}">
                            </c:when>
                            <c:otherwise>
                                <div class="no-image">Không có hình ảnh</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="product-info">
                        <div class="product-name">${product.productName}</div>
                        <div class="product-price">${product.price} đ</div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <div class="pagination">
            <c:if test="${currentPage > 0}">
                <a href="${pageContext.request.contextPath}/product?page=${currentPage - 1}">« Trước</a>
            </c:if>
            
            <c:forEach begin="0" end="${totalPages - 1}" var="i">
                <a href="${pageContext.request.contextPath}/product?page=${i}" 
                   class="${i == currentPage ? 'active' : ''}">${i + 1}</a>
            </c:forEach>
            
            <c:if test="${currentPage < totalPages - 1}">
                <a href="${pageContext.request.contextPath}/product?page=${currentPage + 1}">Sau »</a>
            </c:if>
        </div>
    </div>
</body>
</html>
