# Stage 1: Build Angular
FROM oven/bun:1 AS builder
WORKDIR /app
COPY package.json bun.lockb* ./
RUN bun install --frozen-lockfile
COPY . .
RUN bunx ng build --configuration=production

# Stage 2: Nginx
FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
