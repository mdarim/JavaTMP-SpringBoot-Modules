Here’s a **concise list of the most important Spring Boot Actuator links (endpoints)** that you’ll typically use to monitor and interact with your application. Spring Boot Actuator exposes these under a base path like `/actuator` by default — e.g., `http://localhost:8080/actuator/health`. ([Spring Enterprise Docs][1])

---

## 🚀 Common Spring Boot Actuator Endpoints

### 📌 Core Operational Endpoints

| Endpoint               | Purpose                                                                                                                  |
|------------------------|--------------------------------------------------------------------------------------------------------------------------|
| `/actuator/health`     | Shows application health status (UP, DOWN, etc.). Essential for load-balancers/monitoring. ([Spring Enterprise Docs][1]) |
| `/actuator/info`       | Displays custom application info (e.g., version/build details). ([Spring Enterprise Docs][1])                            |
| `/actuator/metrics`    | Returns application metrics (memory, CPU, JVM, custom metrics). ([Spring Enterprise Docs][1])                            |
| `/actuator/loggers`    | View and change the log levels at runtime. ([Spring Enterprise Docs][1])                                                 |
| `/actuator/mappings`   | Lists all HTTP request mappings in the app (useful for debugging routes). ([Spring Enterprise Docs][1])                  |
| `/actuator/beans`      | Shows all Spring beans in the context. ([GeeksforGeeks][2])                                                              |
| `/actuator/conditions` | Lists auto-configuration conditions and evaluation results. ([GeeksforGeeks][2])                                         |
| `/actuator/httptrace`* | Shows last HTTP requests (trace info). Requires enabling. ([GeeksforGeeks][2])                                           |
| `/actuator/sessions`*  | Manage HTTP sessions (delete/list). Requires Spring Session. ([Spring Enterprise Docs][1])                               |
| `/actuator/threaddump` | Returns a thread dump for diagnosing thread issues. ([Spring Enterprise Docs][1])                                        |

> Endpoints marked with `*` may require extra configuration or dependencies. ([Spring Enterprise Docs][1])

---

## 📁 Discovery & Advanced Links

| Endpoint                | Purpose                                                                                               |
|-------------------------|-------------------------------------------------------------------------------------------------------|
| `/actuator`             | **Discovery root** — lists all available actuator endpoints (HATEOAS JSON). ([Baeldung on Kotlin][3]) |
| `/actuator/heapdump`    | Provides a JVM heap dump (useful for memory analysis). ([Spring Enterprise Docs][1])                  |
| `/actuator/env`         | Shows configurable environment properties. ([Spring Enterprise Docs][1])                              |
| `/actuator/shutdown`*   | Gracefully shuts down the application (disabled by default). ([Spring Enterprise Docs][1])            |
| `/actuator/prometheus`* | Metrics formatted for Prometheus scraping. ([Spring Enterprise Docs][1])                              |

---

## ⚙️ Notes on Access & Exposure

✔ By default **only `/actuator/health` and `/actuator/info` are exposed over HTTP**; other endpoints must be explicitly enabled. ([Baeldung on Kotlin][3])
✔ You can expose additional ones via properties:

```yaml
management.endpoints.web.exposure.include=health,info,metrics,loggers,mappings
```

or include all:

```yaml
management.endpoints.web.exposure.include=*
```

✔ To secure these in production, restrict access (e.g., with Spring Security). ([Home][4])

---

## 👀 Example Access

* Health status: `GET /actuator/health`
* Info details: `GET /actuator/info`
* JVM/thread/metrics: `GET /actuator/metrics`
* View mappings: `GET /actuator/mappings`
* Log level control: `GET/POST /actuator/loggers`

These URLs assume your Actuator base path is `/actuator` (default). You can customize it via `management.endpoints.web.base-path`. ([Spring Enterprise Docs][1])

---

If you want, I can also generate **example Spring Boot configuration snippets** to expose and secure these endpoints!

[1]: https://docs.enterprise.spring.io/spring-boot/reference/actuator/endpoints.html?utm_source=chatgpt.com "Endpoints :: Spring Boot"
[2]: https://www.geeksforgeeks.org/spring-boot-actuator/?utm_source=chatgpt.com "Spring Boot Actuator - GeeksforGeeks"
[3]: https://www.baeldung.com/spring-boot-actuator-enable-endpoints?utm_source=chatgpt.com "How to Enable All Endpoints in Spring Boot Actuator | Baeldung"
[4]: https://docs.spring.io/spring-boot/reference/actuator/endpoints.html?utm_source=chatgpt.com "Endpoints :: Spring Boot"
