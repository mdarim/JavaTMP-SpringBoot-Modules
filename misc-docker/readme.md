# Spring Boot Docker Module

## Howto
- Make sure to build by `gradle :spring-boot-miscellaneous:spring-boot-docker:build`
- Build Docker image by `docker build -t javatmp-starter-docker .`
- Run your image by `docker run -p 8080:8080 javatmp-starter-docker`
- Build a Docker Image with Gradle by `gradlew bootBuildImage --imageName=dev`
- Pass `docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 -t dev`

## Override gradle `bootBuildImage` to use local Dockerfile

To override the default behavior of the `bootBuildImage` task in Spring Boot 3's Gradle plugin and utilize a custom `Dockerfile` for building your application's Docker image, you can follow these steps:

1. **Create a Custom Dockerfile**:

   Place your custom `Dockerfile` at the root of your project or in a specific directory of your choice. This file will define the instructions for building your Docker image.

2. **Disable the Default `bootBuildImage` Task**:

   The `bootBuildImage` task uses Cloud Native Buildpacks by default. To use your custom `Dockerfile` instead, you can disable this task to prevent conflicts.

   In your `build.gradle` file, add the following configuration:

   ```groovy
   tasks.named('bootBuildImage') {
       enabled = false
   }
   ```

3. **Create a Custom Task to Build the Docker Image**:

   Define a new Gradle task that utilizes your custom `Dockerfile` to build the Docker image.

   ```groovy
   task buildCustomImage(type: Exec) {
       group = 'docker'
       description = 'Builds a Docker image using the custom Dockerfile.'
       commandLine 'docker', 'build', '-t', 'yourappname:latest', '-f', 'path/to/your/Dockerfile', '.'
   }
   ```

   Replace `'yourappname:latest'` with your desired image name and tag, and `'path/to/your/Dockerfile'` with the actual path to your custom `Dockerfile`.

4. **Build the Docker Image**:

   Run the custom task to build your Docker image:

   ```bash
   ./gradlew buildCustomImage
   ```

By following these steps, you can effectively override the default `bootBuildImage` task and use your custom `Dockerfile` to build the Docker image for your Spring Boot 3 application.


---

## References
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker)
