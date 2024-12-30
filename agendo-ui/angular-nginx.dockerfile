### Stage 1 - build the Angular application
FROM node:18 AS angular-app

WORKDIR /app

# Copy all files from the current directory to the container
COPY . . 

# Install the dependencies
RUN npm install

# Build the project
RUN npm run build

### Stage 2 - bundle the Angular application with Nginx
FROM nginx:alpine

# Copy the build output to replace the default nginx contents
COPY --from=angular-app /app/dist/agendo-ui/browser /usr/share/nginx/html

# Expose port 80
EXPOSE 80