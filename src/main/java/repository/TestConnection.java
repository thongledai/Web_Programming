package repository;

import jakarta.persistence.EntityManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // Gọi thử hàm getEntityManager mà bạn vừa viết
            EntityManager em = JPAConfig.getEntityManager();
            
            if (em != null && em.isOpen()) {
                System.out.println("Kết nối Database thành công qua JPA!");
                em.close();
            } else {
                System.out.println("Kết nối thất bại!");
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi khi kết nối:");
            e.printStackTrace();
        }
    }
}