# Base Project Spring Boot
Đây là một project mẫu cho ứng dụng Java Spring Boot

## Cấu Trúc Project
```
baseproject.io/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── daohaidang/
│   │   │           └── baseproject/
│   │   │               ├── common/      # Các thành phần dùng chung
│   │   │               ├── config/      # Các lớp cấu hình Spring
│   │   │               ├── controller/  # Lớp API Layer, tiếp nhận HTTP request
│   │   │               ├── dto/         # Data Transfer Objects
│   │   │               ├── entity/      # Các lớp ánh xạ với bảng trong CSDL
│   │   │               ├── exception/   # Xử lý exception tập trung
│   │   │               ├── repository/  # Lớp Data Access Layer, chỉ làm việc với DB
│   │   │               ├── service/     # Lớp Business Logic Layer, chứa nghiệp vụ cốt lõi
│   │   │               └── BaseProjectApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── .env.example
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md