# What you needs

| Keyword | Description |
| --- | --- | 
| ... | ... |


# Runtime Environment 

environment

```bash
git clone https://github.com/u2waremanager/io.u2ware.common.usage.git
```

```bash
cd io.u2ware.common.usage
```

```bash
docker-compose up -d 
```

```bash
http://localhost:8080
```


# Develop Envirement 

### 1. Frontend

```bash
cd io.u2ware.common.usage/src/test/resources/frontend
```
```bash
npm install
```
```bash
npm run dev
```

### 2. Backend

```bash
cd io.u2ware.common.usage
```

```bash
./mvnw spring-boot:run
```

# Packaging Envirement


### 1. Frontend
```bash
cd io.u2ware.common.usage/src/test/resources/frontend
```
```bash
npm run build
```

## 2. Backend

```bash
cd io.u2ware.common.usage
```

```bash
./mvnw clean install
```


# Deploy 

```bash
docker-compose up --remove-orphans 
```


docker push ghcr.io/u2ware-company/io.u2ware.product.files:${project.version}





