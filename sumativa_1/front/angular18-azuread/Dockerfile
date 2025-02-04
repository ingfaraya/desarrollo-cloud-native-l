# Etapa de construcción
FROM node:20.17-alpine AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de dependencias
COPY package*.json ./

# Instalar dependencias sin ejecutar scripts de postinstall
RUN npm install --ignore-scripts

# Copiar los scripts necesarios si son requeridos
COPY /front/sample-scripts /app/sample-scripts

# Copiar el resto del proyecto al contenedor
COPY . .

# Construir la aplicación Angular
RUN npm run build

# Etapa de producción
FROM nginx:alpine

# Copiar los archivos estáticos construidos desde la etapa de construcción
COPY --from=build /app/dist /usr/share/nginx/html

# Copiar archivo de configuración de Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Exponer el puerto 80
EXPOSE 80

# Iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]
