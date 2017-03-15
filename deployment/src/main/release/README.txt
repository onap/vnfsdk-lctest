Introduction:
=============
    This document provides the required steps for installation
    and configuration this service.

Installation steps:
====================
    Install following software:
        - Java 1.8 or lattest
        - Apache Tomcat Server 8.0 or lattest

    Set the following environment variables:
        - JAVA_HOME: Set to JAVA JDK installed location
        - CATALINIA_HOME: Set to Tomcat installed location
        - CATALINIA_BASE: Set to the location, where this
          service installer is unzipped, its optional

Configuration steps:
====================
    - Update the MSB address in $PATH/etc/conf/restclient.json
    - Update the lifecycle-test service ip address in $PATH/etc/adapterInfo/lifecycletestinfo.json

How to run?
===========
    - In command console, cd to 'bin' directory under the location,
      where this service installer is unzipped and
      run ./start.sh
          NOTE: It starts the tomcat at predefined http port. To change
          default port, update the port in tomcat configuration file
          'conf/server.xml'
          - Verify that 'Tomcat started.' is reported on the console.
    - Once service is started, please verify below details:
        - from MSB service, verify that "lifecycle-test"  is reported from GET request on "/openoapi/microservices/v1/services"
        - from this service, run one of the supported REST API mentioned in open-o NFVO wiki and verify that the
          expected response is returned.

How to stop?
=============
    - In command console, cd to 'bin' directory under the location,
      where this service installer is unzipped and
      run ./stop.sh
