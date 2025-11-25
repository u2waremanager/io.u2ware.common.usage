# What you needs

| Keyword | Description |
| --- | --- | 
| ... | ... |


# Runtime Environment 

environment

```bash
git clone https://github.com/u2waremanager/io.u2ware.common.examples.git
```

```bash
cd io.u2ware.common.examples
```

```bash
docker-compose up -d 
```

```bash
http://localhost:8080
```


# Development Environment

### 1. Frontend

```bash
cd io.u2ware.common.examples/src/test/resources/frontend
```
```bash
npm install
```
```bash
npm run dev
```

### 2. Backend

```bash
cd io.u2ware.common.examples
```

```bash
./mvnw spring-boot:run
```


# Distribution Environment


### 1. Frontend
```bash
cd io.u2ware.common.examples/src/test/resources/frontend
```
```bash
npm run build
```

## 2. Backend

```bash
cd io.u2ware.common.examples
```

```bash
./mvnw clean install
```

## 3. Deploy

```bash
docker build \
--platform linux/arm64,linux/amd64 \
-t ghcr.io/u2waremanager/io.u2ware.examples.rest:0.0.1-SNAPSHOT \
.
```

```bash
docker push ghcr.io/u2waremanager/io.u2ware.examples.rest:0.0.1-SNAPSHOT
```