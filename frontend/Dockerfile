# Etapa build
FROM node:20-alpine as build
WORKDIR /app
COPY package.json package-lock.json* ./
# Si hay lock, usa ci; si no, usa install
RUN if [ -f package-lock.json ]; then npm ci; else npm install; fi
COPY . .
# Asegúrate de tener angular.json y @angular/cli en devDependencies
RUN npx ng build --configuration production

# Etapa runtime
FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
# Copia el CONTENIDO de dist/.../browser a la raíz que sirve Nginx
COPY --from=build /app/dist/clase3-frontend/browser/ /usr/share/nginx/html/
EXPOSE 80
