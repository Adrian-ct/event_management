FROM ivonet/glassfish:6.2.5-jkd17

WORKDIR $DEPLOY_DIR

COPY target/app.war $DEPLOY_DIR

COPY domain.xml /opt/glassfish6/glassfish/domains/domain1/config/

COPY postgresql-42.7.2.jar /opt/glassfish6/glassfish/domains/domain1/lib/

ENTRYPOINT ["asadmin","start-domain","-v"]

EXPOSE 8080 4848