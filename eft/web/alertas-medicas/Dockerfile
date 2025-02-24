# Etapa de compilación
FROM node:20 as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --configuration production

# Etapa de ejecución con NGINX
FROM nginx:alpine
COPY --from=build /app/dist/alertas-medicas /usr/share/nginx/html
EXPOSE 80
