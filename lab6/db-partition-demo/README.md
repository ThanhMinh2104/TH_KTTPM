# DB Partition Demo — Spring Boot

Demo 3 loai Database Partitioning voi Spring Boot + SQL Server (H2 cho test local).

---

## Cau truc project

```
src/main/java/com/example/dbpartition/
├── DbPartitionDemoApplication.java      ← Main class
├── config/
│   ├── DataSourceConfig.java            ← Cau hinh 2 datasource
│   ├── UserRoutingDataSource.java       ← Logic chon datasource (CORE)
│   └── DataLoader.java                  ← Tao data mau khi start
├── entity/
│   └── User.java                        ← Entity
├── repository/
│   └── UserRepository.java
├── service/
│   └── UserService.java                 ← Logic phan vung
└── controller/
    └── UserController.java              ← REST API
```

---

## Chay app (local — dung H2)

```bash
# 1. Mo terminal trong VSCode (Ctrl + `)
mvn spring-boot:run

# Hoac click nut "Run" tren DbPartitionDemoApplication.java
```

App chay tai: http://localhost:8080

---

## Test API

### Tao user moi (tu dong chon partition theo gender)
```bash
# Nam -> luu vao DB_MALE
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Nguyen Van A","email":"a@mail.com","gender":"MALE","age":25}'

# Nu -> luu vao DB_FEMALE
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Nguyen Thi B","email":"b@mail.com","gender":"FEMALE","age":22}'
```

### Lay users theo partition
```bash
# Chi lay tu DB_MALE (table_user_01)
curl http://localhost:8080/api/users?gender=MALE

# Chi lay tu DB_FEMALE (table_user_02)
curl http://localhost:8080/api/users?gender=FEMALE

# Lay tat ca (merge tu ca 2 partition)
curl http://localhost:8080/api/users/all
```

### H2 Console (xem du lieu truc tiep)
- URL: http://localhost:8080/h2-console
- JDBC URL male:   `jdbc:h2:mem:male_db`
- JDBC URL female: `jdbc:h2:mem:female_db`
- Username: `sa`, Password: (de trong)

---

## Chuyen sang SQL Server that

1. Mo `src/main/resources/application.properties`
2. Comment phan H2, uncomment phan SQL Server
3. Dien thong tin ket noi
4. Trong `DataSourceConfig.java`, doi dialect:
   ```
   props.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
   ```

---

## Giai thich 3 loai Partition

### 1. Horizontal Partition (da implement)
- Chia theo **hang (rows)**
- Nam -> `table_user_01` (DB_MALE)
- Nu  -> `table_user_02` (DB_FEMALE)
- Core class: `UserRoutingDataSource` + `AbstractRoutingDataSource`

### 2. Vertical Partition
- Chia theo **cot (columns)**
- `user_main`: id, name, email (hot data — query nhieu)
- `user_detail`: id, bio, avatar (cold data — query it)
- Dung 2 Entity rieng, 2 Table rieng

### 3. Functional Partition (Heidi)
- Chia theo **chuc nang / service**
- User DB rieng, Order DB rieng, Product DB rieng
- Moi service co `@Qualifier("xxxDataSource")` rieng
- Huong toi Microservices architecture

---

## VSCode Extensions nen cai

- Extension Pack for Java (Microsoft)
- Spring Boot Extension Pack (VMware)
- SQL Server (mssql) — de ket noi SQL Server
- Thunder Client — test API khong can Postman
