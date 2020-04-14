# Load test example
Load test example with used [Gatling](https://gatling.io)

Requirements
------------
Must be installed in your system: Docker, Java, Maven

Start test
------------
1. Pull and run docker image
    ```bash 
    docker pull zentreid/zoo:v1.01
    docker run -d -p 8080:8080 --name crud zentreid/zoo:v1.01
    ```
2. Clone project
    ```
    git clone git@github.com:Deniszen/gatling-example
    ```
3. Run test
    ```
    mvn clean gatling:test
    ```
4. Open the following file for view report
    ```
    path\to\project\target\gatling\loadzooservice-*\index.html
    ```