# vro-helloworld-plugin
A hello world project for vRO plugin

Has a greeter service and an inventory of employees.

# To build run the following command
mvn clean install -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Drepo.host=<VRO_HOST_IP> -Dbuild.number=1

#To import workflows into project ensure they are mapped to package: com.demo.helloworld then run
mvn o11n-package:import-package -DserverUrl=vsphere.local\\administrator:<password>@<VRO_HOST_IP>:8281 -Dmaven.wagon.http.ssl.insecure=false -Dmaven.wagon.http.ssl.allowall=true