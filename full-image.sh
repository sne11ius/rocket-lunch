rm -rf ./backend/build
rm -rf ./frontend/dist
rm -rf ./frontend/src/api/generated
cd backend && gradle clean build && cd .. || exit 1
cd frontend && npm run build && cd .. || exit 1

cat <<'EOF' > Dockerfile || exit 1
FROM sne11ius/rocket-lunch-backend
COPY ./frontend/dist/* /app/resources/static/
EOF

docker pull sne11ius/rocket-lunch-backend
docker build . -t sne11ius/rocket-lunch || exit 1
docker push sne11ius/rocket-lunch || exit 1
