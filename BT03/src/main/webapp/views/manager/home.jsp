<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="manager-container">

    <aside class="manager-left">
        <h3>Manager Menu</h3>

        <ul>
            <li>Quản lý độc giả</li>
            <li>Quản lý sách</li>
            <li>Nhận trả sách</li>
            <li>Thay đổi quy định</li>
        </ul>
    </aside>

    <main class="manager-content">

        <h2>Manager Home</h2>

        <p>Xin chào Manager</p>

        <h3>Chức năng quản lý</h3>

    </main>

</div>

<style>
.manager-container {
    display: flex;
    width: 100%;
}

.manager-left {
    width: 250px;
    flex-shrink: 0;
    padding: 20px;
    background: #f5f5f5;
}

.manager-content {
    flex: 1;
    padding: 30px;
}
</style>