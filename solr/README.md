#itk_reservation_solr - Solr Index Configuration For ITK
======

## Contents
* solr.xml - Top level solr config, specifies the indexes to load.
* README.md - Documentation.
* logrotate.txt - Specifies Tomcat server log rotation.
* activities/ - Config directory for the activities index. This powers 'search', 'morelikethis', 'autocomplete', and 'spellcheck' functionality.

## Production Usage - SOLR CLOUD SETUP ONLY

### Assumptions
* The Solr 4.* must be deployed along with its Zookeeper management cluster. 
* There must be 3 Zookeeper machines with the hostnames and ports: zk[1-3].production.itivitykids.com:9100 (replace 'production' with the correct environment)
* There must be one or more running instances of solr behind a load balancer


### First-time Setup Instructions

* Locally, download and unpack Solr 4.x from http://lucene.apache.org/solr and set the environment variable ```$APACHE_SOLR_HOME``` to this location.
* 
```
cd $APACHE_SOLR_HOME/example/solr-webapp; jar -xvf ../webapps/solr.war; cd -
```

* Clone itk_reservation_solr project
```
git clone git@github.com:Itivity/itk_reservation_solr.git
```

* Upload the activities index configuration
```
java -cp $(echo $APACHE_SOLR_HOME/example/solr-webapp/WEB-INF/lib/*.jar | tr ' ' ':') org.apache.solr.cloud.ZkCLI \
  -cmd bootstrap \
  -zkhost zk1-public.production.itivitykids.com:9100,zk2-public.production.itivitykids.com:9100,zk3-public.production.itivitykids.com:9100 \
  -solrhome itk_reservation_solr/
```

* Create the new collections
```
curl 'http://solr-public.staging.itivitykids.com:9000/solr/admin/cores?action=CREATE&name=activities_core&collection=activities&collection.configName=activities'
curl 'http://solr-public.staging.itivitykids.com:9000/solr/admin/cores?action=CREATE&name=activities&collection=activities&collection.configName=activities'
```

* Restart the Tomcat application server to clear the bootstrap errors

* Check the status of the new collections
```
curl http://solr-public.staging.itivitykids.com:9000/solr/admin/cores?action=STATUS&core=activities
curl http://solr-public.staging.itivitykids.com:9000/solr/admin/cores?action=STATUS&core=activities
curl http://solr-public.staging.itivitykids.com:9000/solr
```


### Update Configuration
* Upload the activities index configuration
```
java -cp $(echo $APACHE_SOLR_HOME/example/solr-webapp/WEB-INF/lib/*.jar | tr ' ' ':') org.apache.solr.cloud.ZkCLI \
  -cmd upconfig \
  -zkhost zk1-public.production.itivitykids.com:9100,zk2-public.production.itivitykids.com:9100,zk3-public.production.itivitykids.com:9100 \
  -confdir itk_reservation_solr/activities/conf \
  -confname activities \
  -solrhome itk_reservation_solr/
```

