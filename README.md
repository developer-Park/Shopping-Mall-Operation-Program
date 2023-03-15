
# 777 Team Lucky Seven 
>**“It is an online shopping mall management program focused on back-end development. Implemented the basic functions of the shopping mall.”**



<div>
 <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white">
 <img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white">
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
 <img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
 <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white">
</div>
<div>
 <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white">
 <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white"><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
 </div>

## Index

<!-- TOC -->
* [Project environment](#project-environment)
* [Team Members](#team-members)
* [Demonstration video](#demonstration-video)
* [Project requirement](#project-requirement)
* [Sequence Diagram](#sequence-diagram)
* [Table ERD](#table-erd)
* [Wireframe](#wireframe)
* [API documents](#api-documents)
<!-- TOC -->


<br>


## Project environment
<details><summary> &nbsp Project development environment</summary>

- spring 2.7.6
- h2
- JDK 17
- build.gradle
    ```
   dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
        implementation 'org.springframework.boot:spring-boot-starter-security'
        implementation 'org.springframework.boot:spring-boot-starter-web'
        implementation 'org.springframework.boot:spring-boot-starter-validation'
    
        compileOnly 'org.projectlombok:lombok'
        runtimeOnly 'com.h2database:h2'
        annotationProcessor 'org.projectlombok:lombok'
    
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        testImplementation 'org.springframework.security:spring-security-test'
    
        testCompileOnly 'org.projectlombok:lombok'
        testAnnotationProcessor 'org.projectlombok:lombok'
    
    
        compileOnly group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.2'
        runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.2'
        runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.2'
    
        implementation 'org.springframework.boot:spring-boot-starter-data-redis'
        implementation group: 'it.ozimov', name: 'embedded-redis', version: '0.7.1'
    
        implementation 'org.springframework.boot:spring-boot-starter-websocket'
    }
    ```

- application.properties

```
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:db;MODE=MYSQL;
spring.datasource.username=
spring.datasource.password=
spring.thymeleaf.cache=false
spring.jpa.properties.hibernate.show_sql=true
logging.level.org.hibernate.type.descriptor.sql=trace

jwt.secret.key=

##Redis
spring.redis.host=localhost
spring.redis.port=6379

##Swagger
spring.mvc.pathmatch.matching-strategy=ant_path_matcher

```
</details>
<br>


## Team Members
<div>
 Team notion : https://lively-beluga-779.notion.site/0c49ffa349e34b138606d192a438f9f9
 
 </div>
JeongHun Park(Parker), Mun ji Young, Ji seop Lee, Hyun jae Jang


### Role

| Member | Role                                                                          |
|:---:|:----------------------------------------------------------------------------|
| Parker | - Spring Security,JWT Token,Redis<br/>- Signup,Signin,Logout<br/>- Category function,User function,Point function<br/>- Exception function, HTML|
| Mun ji Young | -  Admin function<br/> - Search function|
| Ji seop Lee | - Paging function<br/> - Swagger function|
| Hyun jae Jang | - Seller function|
<br>

## Demonstration video
https://www.youtube.com/watch?v=LYVYWcRVgPg

## Project requirement
<details><summary> details
</summary>- Creating our own matching service project
[ Customer-seller matching service (free matching subject)]
- Member signup/login/logout/token function
- User permission function
    - Users are divided into three rights.
        - Customer: The user who first registered as a member
        - Seller: Customers who have been approved as a seller
        - Operator: User who approves the seller
- Functions by user authority
    - customer
        - Lookup
            - My profile setting and inquiry: You can set and view profiles (nickname, image) for each user
            - List of all sales products: Paging through the list of sales products
            - List of all sellers: search through the list of sellers by paging
            - Seller information: Select a seller to view profile information (nickname, image, introduction + matching topic information)
        - write
            - Request Form to Seller: Send the request details (matching topic information) to the seller
        - Permission request
            - Seller registration request: Fill out the seller profile request information and request seller registration to the operator
            
    - seller
        - Lookup
            - Set and view my seller profile: set and search profile for each seller (nickname, image, introduction + matching topic information)
            - Search my sales products: Paging through the list of products I am selling
            - Search customer request list: Paging and search the customer request list of all products
        - Enrollment
            - Register my sales product: Fill out the sales product information and register it on the list
        - Modify
            - Modify/Delete My Selling Products: Write the selling product information and edit it in the list
        - delete
            - Delete my sales product: Write the sales product information and delete it from the list
        - Customer request processing: Accept customer request and complete processing
    - Operator
        - Lookup
            - Customer List: Paging through the list of customers
            - Seller List: Paging and search the list of sellers
            - Seller registration request form list: Search the seller registration request list
        - Permission registration
            - Seller permission approval: Approve the seller registration request
        - delete
            - Seller authority: Delete user's seller authority
            
- Search function
    - Keyword search: Add a search function by entering a search keyword when searching for paging lists.
    - Seller Search: Add a function to search by seller name when searching the paging list.


- Customer-seller conversation function
    - Chat room creation: A chat room is created when sales start.
    - Conversation message transmission function: Customer and seller have a conversation about the sale.
    - Chat room message list search: You can search the chat list between the customer and the seller.
    - Chat room termination: When the sale is completed, the chat room is stopped and no more messages can be sent.
    
</details>

<br>

## Sequence Diagram
![sequence](https://user-images.githubusercontent.com/83831110/225190929-a9499ea2-52c4-4e8a-a7c7-c4e4343fa617.png)

<br>

## Table ERD
![erd](https://user-images.githubusercontent.com/83831110/225190883-a18cb469-7147-4312-b9d3-bff25864dd89.png)


<br>

## Wireframe
![wireframe](https://user-images.githubusercontent.com/83831110/225191022-b601c267-681e-4d7d-b4c6-276259c1f05f.png)

## API documents
![general](https://user-images.githubusercontent.com/83831110/225192197-e99ed9b9-2aa9-43b3-9637-b5344e040ff1.png)

![user](https://user-images.githubusercontent.com/83831110/225192758-ff558489-835d-4e22-8ff1-69141b6eefb9.png)

![seller](https://user-images.githubusercontent.com/83831110/225193457-a84729b5-68cb-43a3-9134-a1a24979b18d.png)

![admin](https://user-images.githubusercontent.com/83831110/225194515-3e77f425-8a86-4e71-b11e-91c8e013543b.png)

![category](https://user-images.githubusercontent.com/83831110/225194792-9a062bcd-d08f-481b-970d-7d37b7712084.png)

![point](https://user-images.githubusercontent.com/83831110/225196066-5c174a38-5b35-4d2f-9a68-c23c70afbf4a.png)

![chat](https://user-images.githubusercontent.com/83831110/225196166-8d703435-c299-4699-b8e1-328f1dc215c9.png)










