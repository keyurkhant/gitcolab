./mvnw package -Dmaven.test.skip=true
docker buildx build --platform=linux/amd64 -t git-colab .
heroku container:push web -a git-colab
heroku container:release web -a git-colab