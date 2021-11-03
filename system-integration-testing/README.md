## Tutorial for system-integration-testing in CoatRack

### Requirements

* Linux Commandline, for Windows use WSL
* Docker installation
* GitHub account



### Scripts

```run.sh```: Executes the tests. The scripts take care of all details in the background like pulling/building images etc.

```stop.sh```: If run.sh is interrupted (e.g. by CTRL + 'c') before terminating, the containers could still run in the detached-mode in the background. Execute stop.sh to terminate them.

```cleanup.sh```: Deletes all possibly running containers and images related to this feature.



### Run the tests

* ```config.properties```: 
  * Insert the login credentials of the GitHub account in the fields ```username``` and ```password```.
  * ```host``` should have the value of a CoatRack web address like ```coatrack.eu``` or ```dev.coatrack.eu```.
* Execute ```run.sh```.



### Setup a local test build

* ```config.properties```: Change the value of ```host``` to ```host.docker.internal```.
* Ensure the OAuth2 Application settings ```Homepage URL``` and ```Authorization callback URL``` on GitHub to have the value ```http://host.docker.internal:8080```. Otherwise you will receive a URL mismatch error during the redirection from GitHub login to the CoatRack Web Application.
*  ```application.yml```:
  * Ensure the presence of the correct credentials for the just mentioned OAuth2 application, namely ```spring.security.oauth2.client.registration.github.client-id``` and ```client-secret```.
  * The parameter ```ygg.admin.api-base-url-for-gateway``` should have the value ```http://host.docker.internal:8080/api/```.
  * If a config server is used which overrides the local ```application.yml```, then ensure that it contains the proper parameter values just mentioned.
* Rebuild the whole CoatRack project after all required configurations are done.
* Finally, run the CoatRack Web Application locally and execute ```run.sh```.



### Testing with Firefox Instance as GUI

* This feature does only work for remote hosts like ````coatrack.eu```` or ```dev.coatrack.eu```.
* To see what actually happens behind the scenes, you can execute the tests via your IDE on a Windows machine only. You also require following installations: 
  * wget and curl (probably already installed by default, check via CLI)
  * [geckodriver](https://github.com/mozilla/geckodriver/releases) (at least version 0.30)
  * firefox
  * java (openjdk-11)
* You also have to make them accessible for the CLI by setting the proper Environment Variables (PATH).
