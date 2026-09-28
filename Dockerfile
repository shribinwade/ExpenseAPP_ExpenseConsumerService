FROM amazoncorretto:21

LABEL maintainer="shrihari"

WORKDIR /app

COPY build/libs/expenseservice.jar /app/expenseservice.jar

ENTRYPOINT ["java", "-jar", "/app/expenseservice.jar"]