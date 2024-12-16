
# PG3402-Mikrotjenester-dragepust



Running Dragepust character sheet and campaign sheet


Build and run
```
docker compose -f .\docker-compose.yml up --build
```

Launching without building
```
docker compose -f .\docker-compose.yml up
```

Removes ALL docker images and volumes
```
docker system prune -a --volumes
```
