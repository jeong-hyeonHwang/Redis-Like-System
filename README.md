# Redis-Like-System

## Settings
### docker base sql & redis

**BASH**
```bash
docker run --name like-demo-mysql \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_DATABASE=likedemo \
  -e MYSQL_USER=user \
  -e MYSQL_PASSWORD=password \
  -p 3306:3306 \
  -d mysql:8.0.41
```

```bash
docker run --name like-demo-redis -p 6379:6379 -d redis
```

**POWERSHELL**
```powershell
docker run --name like-demo-mysql `
  -e MYSQL_ROOT_PASSWORD=password `
  -e MYSQL_DATABASE=likedemo `
  -e MYSQL_USER=user `
  -e MYSQL_PASSWORD=password `
  -p 3306:3306 `
  -d mysql:8.0.41
```

```powershell
docker run --name like-demo-redis -p 6379:6379 -d redis
```

---