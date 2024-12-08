# Используем базовый образ Java 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем скомпилированное приложение в контейнер
COPY target/MyTeacherBot-1.0-SNAPSHOT.jar bot.jar

# Запускаем приложение
CMD ["java", "-jar", "bot.jar"]