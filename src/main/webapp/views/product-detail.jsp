<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.productName}</title>
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
            max-width: 1000px;
            margin: 30px auto;
            padding: 0 20px;
        }
        .product-detail {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 30px;
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 40px;
        }
        .product-image-large {
            width: 100%;
            text-align: center;
        }
        .product-image-large img {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .no-image-large {
            width: 100%;
            height: 400px;
            background-color: #e0e0e0;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #999;
            border-radius: 8px;
        }
        .product-details {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .product-title {
            font-size: 28px;
            font-weight: bold;
            color: #333;
        }
        .product-price {
            font-size: 32px;
            color: #4CAF50;
            font-weight: bold;
        }
        .product-category {
            color: #666;
            font-size: 16px;
        }
        .product-description {
            color: #555;
            line-height: 1.6;
            margin-top: 10px;
        }
        .product-info-item {
            display: flex;
            gap: 10px;
        }
        .product-info-label {
            font-weight: bold;
            color: #333;
        }
        .back-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .back-button:hover {
            background-color: #45a049;
        }
        @media (max-width: 768px) {
            .product-detail {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Chi tiết sản phẩm</h1>
    </div>
    
    <div class="nav">
        <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
        <a href="${pageContext.request.contextPath}/product">Sản phẩm</a>
        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
    </div>
    
    <div class="container">
        <div class="product-detail">
            <div class="product-image-large">
                <c:choose>
                    <c:when test="${product.image != null}">
                        <img src="${pageContext.request.contextPath}/image?fname=${product.image}" alt="${product.productName}">
                    </c:when>
                    <c:otherwise>
                        <div class="no-image-large">Không có hình ảnh</div>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div class="product-details">
                <div class="product-title">${product.productName}</div>
                <div class="product-price">${product.price} đ</div>
                
                <div class="product-info-item">
                    <span class="product-info-label">Danh mục:</span>
                    <span class="product-category">${product.category.categoryname}</span>
                </div>
                
                <div class="product-info-item">
                    <span class="product-info-label">Trạng thái:</span>
                    <span>${product.status == 1 ? 'Còn hàng' : 'Hết hàng'}</span>
                </div>
                
                <div>
                    <div class="product-info-label">Mô tả:</div>
                    <div class="product-description">
                        <c:choose>
                            <c:when test="${product.description != null && !product.description.isEmpty()}">
                                ${product.description}
                            </c:when>
                            <c:otherwise>
                                Chưa có mô tả cho sản phẩm này.
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                
                <a href="${pageContext.request.contextPath}/product" class="back-button">← Quay lại danh sách</a>
            </div>
        </div>
    </div>
</body>
</html>
