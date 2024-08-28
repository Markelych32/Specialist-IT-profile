# Step 1: Build the application
FROM node:18 AS build

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем все остальные файлы
COPY . .

# Собираем приложение
RUN npm run build

# Step 2: Setup Nginx to serve the built application
FROM nginx:alpine

# Копируем сгенерированные файлы из предыдущего контейнера в Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Копируем файл конфигурации Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Экспонируем порт 80 для доступа к приложению
EXPOSE 80

# Запускаем Nginx
CMD ["nginx", "-g", "daemon off;"]
