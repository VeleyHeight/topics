FROM quay.io/keycloak/keycloak:latest AS builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

# Configure a database vendor
ENV KC_DB=postgres

WORKDIR /opt/keycloak
# for demonstration purposes only, please make sure to use proper certificates in production instead
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest
COPY --from=builder /opt/keycloak/ /opt/keycloak/
COPY ./opt/keycloak/data/import/topics-realm.json /opt/keycloak/data/import/topics-realm.json
# change these values to point to a running postgres instance
#ENV KC_DB=postgres
#ENV KC_DB_URL=jdbc:postgresql://keycloak_db:5432/keycloak
#ENV KC_DB_USERNAME=postgres
#ENV KC_DB_PASSWORD=1234
#ENV KC_HOSTNAME=localhost
#ENV KC_HTTP_PORT=8080
#ENV KC_BOOTSTRAP_ADMIN_USERNAME=admin
#ENV KC_BOOTSTRAP_ADMIN_PASSWORD=admin

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--optimized", "--import-realm"]